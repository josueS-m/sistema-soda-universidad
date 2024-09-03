package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.TextField;

import java.util.List;

import javax.swing.JOptionPane;

import data.Logic;
import data.ServiceData;
import data.StudentData;
import domain.Service;
import domain.Student;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.layout.Pane;

public class UIServiceController {
	@FXML
	private Button btnAddService;
	@FXML
	private Button btnServiceRequest;
	@FXML
	private Button btnBack;
	@FXML
	private Pane pRequestFoodService;
	@FXML
	private Pane pFoodRegisterForm;
	@FXML
	private Button btnCheck;
	@FXML
	private Label lError;
	@FXML
	private ComboBox<String> cxStudent;
	@FXML
	private ComboBox<String> cxReservationDay;
	@FXML
	private RadioButton rbBreakfastRequest;
	@FXML
	private ToggleGroup reservation;
	@FXML
	private RadioButton rbLunchRequest;
	@FXML
	private TableView<Service> tvDataNewServiceStudent;
	@FXML
    private TableColumn<Service, String> tcServiceName;
    @FXML
    private TableColumn<Service, Double> tcServicePrice;   
	@FXML
	private Button btnBackMenu;
	@FXML
	private Button btnDeleteFoodService;
	@FXML
	private Button btnEditFoodService;
	@FXML
	private Button btnAddFood;	
	@FXML
	private RadioButton rbBreakfast;
	@FXML
	private ToggleGroup serviceFood;
	@FXML
	private RadioButton rbLunch;
	@FXML
	private ComboBox<String> cxServiceDay;
	@FXML
	private TextField tfServiceName;
	@FXML
	private TextField tfPriceService;
	@FXML
	private Button btnRegisterService;
	@FXML
	private Button btnBackToRequest;
	@FXML
	private Button btnConfirmService;
	
	@FXML
	private ObservableList<String> daysList = FXCollections.observableArrayList(
            "Lunes", "Martes", "Miércoles", "Jueves", "Viernes");
		
	//Inicializa los comboBox para gardar al instante
	@FXML
	public void initialize() {
	    cxServiceDay.setItems(daysList);
	    cxReservationDay.setItems(daysList);
	    loadStudents();
	}
	
	//Carga los estudiantes 
	@FXML
	public void loadStudents() {
		List<Student> students = StudentData.getStudentList();
		
	    ObservableList<String> studentList = FXCollections.observableArrayList();
	    for (Student student : students) {
	        studentList.add(student.getIdStudent() + " - " + student.getName());
	    }
	    cxStudent.setItems(studentList);	    
    }
	
	//Carga los servicios en la tabla
	@FXML
	public void loadServices(ActionEvent event) {
		String selectedDay = cxReservationDay.getValue();
	    String schedule = rbBreakfastRequest.isSelected() ? "Desayuno" : "Almuerzo";
				
		tcServiceName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
		tcServicePrice.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getPrice()));
		List<Service> services = ServiceData.getServicesByDayAndSchedule(selectedDay, schedule);
		tvDataNewServiceStudent.getItems().setAll(services);  
	}
	
	//método para los agregar los datos al comboBox
	@FXML
	public void fillCXGender(Event event) {
		Logic.fillCBox(cxServiceDay, daysList);
	}
		
	@FXML
	public void handleAddRegisterPane(ActionEvent event) {
		hideAllPanes();
		pFoodRegisterForm.setVisible(true);
	}
	
	@FXML
	public void handleServiceRequest(ActionEvent event) {
		hideAllPanes();
	    pRequestFoodService.setVisible(true);    	   
	}
	
	@FXML
	public void handleBackMenu(ActionEvent event) {
		closeWindows();		
	}
	
	@FXML
	public void handleBackPane(ActionEvent event) {
		closeWindows();		
	}
	
	@FXML
	public void handleAddFoodService(ActionEvent event) {
		hideAllPanes();
	    pFoodRegisterForm.setVisible(true);
	}
	
	@FXML
	public void handleBackRequest(ActionEvent event) {
		hideAllPanes();
		pRequestFoodService.setVisible(true);
	}
	
	@FXML
	public void handleShowDataSelect(ActionEvent event) {		
		loadServices(event);		
	}
	
	@FXML
	public void handleDeleteFoodService(ActionEvent event) {
		
		Service selectedService = tvDataNewServiceStudent.getSelectionModel().getSelectedItem();
	    if (selectedService == null) {
	        notify("Por favor, seleccione un servicio para eliminar.");
	        return;
	    }

	    boolean success = ServiceData.removeService(selectedService);
	    if (success) {
	        tvDataNewServiceStudent.getItems().remove(selectedService);
	        notify("Servicio eliminado exitosamente.");
	    } else {
	        notify("Error al eliminar el servicio.");
	    }
	}
	
	@FXML
	public void handleEditFoodService(ActionEvent event) {
		
		Service selectedService = tvDataNewServiceStudent.getSelectionModel().getSelectedItem();
		
	    if (selectedService == null) {
	        notify("Por favor, seleccione un servicio para editar.");
	        return;
	    }

	    String newName = tfServiceName.getText();
	    double newPrice = Double.parseDouble(tfPriceService.getText());
	    
	    selectedService.setName(newName);
	    selectedService.setPrice(newPrice);

	    boolean success = ServiceData.editService(selectedService);
	    
	    if (success) {
	        tvDataNewServiceStudent.refresh(); 
	        notify("Servicio editado exitosamente.");
	    } else {
	        notify("Error al editar el servicio.");
	    }
	}
		
	//Método que reemplaza a solicitar
	@FXML
	public void handleConfirmService(ActionEvent event) {
		
	    Service selectedService = tvDataNewServiceStudent.getSelectionModel().getSelectedItem();
	    
	    if (selectedService == null) {
	    	notifyWithDialog("Por favor, seleccione un servicio para solicitar.");
	        return;
	    }

	    // Obtiene el estudiante seleccionado
	    String selectedStudentId = cxStudent.getValue().split(" - ")[0];
	    Student student = StudentData.getStudentById(selectedStudentId);
	    
	    if (student == null) {
	    	notifyWithDialog("Estudiante no encontrado.");
	        return;
	    }

	    //  Validacion del saldo del estudiante
	    if (student.getMoneyAvailable() < selectedService.getPrice()) {
	    	notifyWithDialog("Saldo insuficiente para realizar la compra.");
	        return;
	    }

	    // Actualiza el saldo del estudiante
	    double nuevoSaldo = student.getMoneyAvailable() - selectedService.getPrice();
	    student.setMoneyAvailable(nuevoSaldo);
	    boolean updateSuccess = StudentData.updateStudentBalance(student);

	    if (updateSuccess) {
	        notifyWithDialog("Compra realizada exitosamente. \nNuevo saldo: " + nuevoSaldo);
	    } else {
	    	notifyWithDialog("Error al actualizar el saldo del estudiante.");
	    }
	}
	
	//manejo del registro de servicios
	@FXML
	public boolean handleRegisterService(ActionEvent event) {
	    	    
	    String messageError = validationServiceForm();
	    
	    if (!messageError.isEmpty()) {
	        lError.setText(messageError);
	        return false;
	    }

	    // Obtiene los datos del servicio
	    String serviceName = tfServiceName.getText();
	    double price = Double.parseDouble(tfPriceService.getText());
	    String day = cxServiceDay.getValue();
	    String schedule = rbBreakfast.isSelected() ? "Desayuno" : "Almuerzo";
	    
	    Service newService = new Service(serviceName, price, day, schedule);
	    
	    String confirmMessage = String.format(
	            "¿Estás seguro que quieres registrar el servicio '%s' para el día %s al horario %s?",
	            serviceName, day, schedule);
	   
	    Object[] options = {"Continuar", "Cancelar"};
	    
	    int confirmOption = JOptionPane.showOptionDialog(
	            null, confirmMessage, 
	            "Confirmación", JOptionPane.DEFAULT_OPTION, 
	            JOptionPane.PLAIN_MESSAGE, null, 
	            options, options[0]);
	   
	    if (confirmOption == 0) {
	        if (ServiceData.saveService(newService)) {
	            notify("¡Servicio registrado exitosamente!");
	            cleanForm();
	        } else {
	            notify("Error al registrar el servicio.");
	        }
	    } else {
	        notify("Se canceló el proceso.");
	    }
	    return true;
	}
		
	public String validationServiceForm() {
        StringBuilder messageError = new StringBuilder();

        if (tfServiceName.getText().isEmpty()) {
            messageError.append("*El nombre del servicio es un campo requerido.\n");
        }

        if (tfPriceService.getText().isEmpty()) {
            messageError.append("*El precio es un campo requerido.\n");
        } else {
            try {
                double price = Double.parseDouble(tfPriceService.getText());
                if (price <= 0) {
                    messageError.append("*El precio debe ser un número positivo.\n");
                }
            } catch (NumberFormatException e) {
                messageError.append("*El precio debe ser un número válido.\n");
            }
        }

        if (cxServiceDay.getSelectionModel().getSelectedIndex() == -1) {
            messageError.append("*El día es un campo requerido.\n");
        }

        if (serviceFood.getSelectedToggle() == null) {
            messageError.append("*El horario es un campo requerido.\n");
        }

        return messageError.toString();
    }
	
	///Notificaciones en el label
	public void notify(String message) {
		lError.setText(message);
		Timeline timeline = new Timeline(
				new KeyFrame(
						Duration.seconds(3), e-> lError.setText("")
						)
				);
		timeline.setCycleCount(1);
		timeline.play();
	}
	
	//notificaciones por cuadro de dialogo
	public void notifyWithDialog(String message) {
	    JOptionPane.showMessageDialog(null, message, "Notificación", JOptionPane.INFORMATION_MESSAGE);
	}

	
	public void closeWindows() {

		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIMenu.fxml"));
			Parent root = loader.load();
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();

			Stage temp = (Stage) btnBackMenu.getScene().getWindow();
			temp.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@FXML
	public void hideAllPanes() {
	    pFoodRegisterForm.setVisible(false);
	    pRequestFoodService.setVisible(false);		    
	}	
	
	public void cleanForm() {
        tfServiceName.clear();
        tfPriceService.clear();
        cxServiceDay.getSelectionModel().clearSelection();
        serviceFood.selectToggle(null);
    }
}
