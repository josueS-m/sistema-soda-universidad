package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import java.io.IOException;
import java.time.LocalDate;

import javax.swing.JOptionPane;

import data.RechargeData;
import data.StudentData;
import domain.Recharge;
import domain.Student;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.control.TableColumn;

public class UIRechargeController {
	@FXML
	private Button btnRechargePane;
	@FXML
	private Button btnConsultBalancePane;
	@FXML
	private Pane pAvailableBalance;
	@FXML
	private TextField tfConsultIdStudent;
	@FXML
	private Button btnConsultStudent;
	@FXML
	private TableView<Recharge> tvMoneyAvailable;
	@FXML
	private TableColumn<Recharge, String> tcIdStudent;
	@FXML
	private TableColumn<Recharge, String> tcNameStudent;
	@FXML
	private TableColumn<Recharge, LocalDate> tcDateOfRecharge;
	@FXML
	private TableColumn<Recharge, Double> tcBalance;
	@FXML
	private Button btnBackMenu;
	@FXML
	private Button btnAddNewStudent;
	@FXML
	private Pane pRechargeRegister;
	@FXML
	private TextField tfIdStudentRecharge;
	@FXML
	private TextField tfAmount;
	@FXML
	private DatePicker dpRechargeDate;
	@FXML
	private Button btnBack;
	@FXML
	private Button btnAddRecharge;
	@FXML
	private Label lErrors;

	//Muestra registro de recargas
	@FXML
	public void handleRechargePane(ActionEvent event) {
		hideAllPanes();
		pRechargeRegister.setVisible(true);
	}
	
	//Muestra consulta de saldo
	@FXML
	public void handleConsultPane(ActionEvent event) {
		hideAllPanes();
		pAvailableBalance.setVisible(true);
	}
	
	@FXML
	public void handleBackPane(ActionEvent event) {
		closeWindows();
	}
	
	//salir al menu
	@FXML
	public void handleBackMenu(ActionEvent event) {
		closeWindows();
	}
	
	//regresar a vista consulta de saldo
	@FXML
	public void handleBackToConsult(ActionEvent event) {
		hideAllPanes();
		pAvailableBalance.setVisible(true);
	}
	
	//manejo de consulta de saldo
	@FXML
	public void handleConsultBalance(ActionEvent event) {
	    
	    String studentId = tfConsultIdStudent.getText().trim();

	    if (studentId.isEmpty()) {
	        notifyWithDialog("El campo de carné no puede estar vacío.");
	        return;
	    }

	    // Se busca al estudiante en los datos
	    Student student = StudentData.getStudentById(studentId);
	    if (student == null) {
	        notifyWithDialog("El número de carné '" + studentId + "' no se encuentra registrado.");
	        tfConsultIdStudent.clear();
	        return;
	    }

	    // Obtiene las recargas registradas del estudiante
	    ObservableList<Recharge> recharges = FXCollections.observableArrayList(RechargeData.getRechargesByStudentId(studentId));
	    
	    if (recharges.isEmpty()) {	       
	        notifyWithDialog("El estudiante con el carné " + studentId + " no tiene recargas registradas.");
	        tfConsultIdStudent.clear();
	    } else {
	        
	        tvMoneyAvailable.setItems(recharges);
	        
	        tcIdStudent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdStudent()));
	        tcNameStudent.setCellValueFactory(cellData -> new SimpleStringProperty(StudentData.getStudentById(cellData.getValue().getIdStudent()).getName()));
	        tcDateOfRecharge.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getRechargeDate()));
	        tcBalance.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getAmount()));	       
	    }	    
	}
		
	//método para mostrar "agregar estudiante" desde recarga a estudiante
	@FXML
	public void handleShowToAddStudent(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIStudent.fxml"));
			Parent root = loader.load();
			
			UIStudentController studentController = loader.getController();
			studentController.handleAddPane(event);
			
			 Stage stage = (Stage) btnAddNewStudent.getScene().getWindow();
		        Scene scene = new Scene(root);
		        stage.setScene(scene);
		        stage.show();
		        
		} catch (IOException e) {
	        e.printStackTrace();	        
	    }
	}
		
	//Método para guardar la recarga 
	@FXML
	public void handleAddRecharge(ActionEvent event) {
	    String messageError = formValidationRecharge();

	    if (!messageError.isEmpty()) {
	        lErrors.setText(messageError);
	        return;
	    }

	    String idStudent = tfIdStudentRecharge.getText();
	    double amount = Double.parseDouble(tfAmount.getText());
	    LocalDate rechargeDate = dpRechargeDate.getValue();
	    
	    Recharge newRecharge = new Recharge(idStudent, amount, rechargeDate);

	    // Se guarda la recarga en el Json
	    try {
	        if (RechargeData.saveRecharge(newRecharge)) {
	            notify("¡Recarga registrada exitosamente!");
	            
	            // Actualiza el saldo del estudiante
	            Student student = StudentData.getStudentById(idStudent);
	            if (student != null) {
	                student.setMoneyAvailable(student.getMoneyAvailable() + amount);
	                StudentData.updateStudentBalance(student);
	            }
	            cleanRechargeForm();
	        } else {
	            notify("Error al registrar la recarga.");
	        }
	    } catch (Exception e) {
	        e.printStackTrace();
	        notify("Error al acceder al archivo de recargas.");
	    }
	}

	//Form para las validaciones 
	@FXML
	public String formValidationRecharge() {
		
	    StringBuilder messageError = new StringBuilder();

	    //Valida el carne
	    if (tfIdStudentRecharge.getText().isEmpty()) {
	        messageError.append("*Carné es un campo requerido.\n");
	    } else if (tfIdStudentRecharge.getText().length() > 10) {
	        messageError.append("*Carné no puede exceder de 10 caracteres.\n");
	    }
	    
	    //Valida el saldo
	    if (tfAmount.getText().isEmpty()) {
	        messageError.append("*Monto es un campo requerido.\n");
	    } else {
	        try {
	            double amount = Double.parseDouble(tfAmount.getText());
	            if (amount < 1000 || amount > 10000) {
	                messageError.append("*El monto debe estar entre 1000 y 10000 colones.\n");
	            }
	        } catch (NumberFormatException e) {
	            messageError.append("*Monto debe ser un número válido.\n");
	        }
	    }
	   
	    //Valida la fecha
	    if (dpRechargeDate.getValue() == null) {
	        messageError.append("*Fecha de recarga es un campo requerido.\n");
	    }

	    if (messageError.length() > 0) {
	        notify(messageError.toString());
	    }

	    return messageError.toString();
	}
	
	//notificar en label
	public void notify(String message) {
		lErrors.setText(message);
		Timeline timeline = new Timeline(
				new KeyFrame(
						Duration.seconds(3), e-> lErrors.setText("")
						)
				);
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	//notificar en cuadro de dialogo
	public void notifyWithDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "Notificación", JOptionPane.INFORMATION_MESSAGE);
	}
	
	//cerrar ventana 
	public void closeWindows() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIMenu.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

			Stage temp = (Stage) btnBack.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//limpiar campos 
	public void cleanRechargeForm() {
	    tfIdStudentRecharge.clear();
	    tfAmount.clear();
	    dpRechargeDate.setValue(null);
	}
	
	//visibillidad de los pane 
	@FXML
	public void hideAllPanes() {
	    pAvailableBalance.setVisible(false);
	    pRechargeRegister.setVisible(false);          
	}
}
