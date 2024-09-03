package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

import javax.swing.JOptionPane;

import data.LogicControllerStudent;
import data.StudentData;
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

import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class UIStudentController {
	@FXML
	private Pane pOptionCRUD;
	@FXML
	private Button btnAddStudent;
	@FXML
	private Button btnEditStudent;
	@FXML
	private Button btnDeleteStudent;
	@FXML
	private Button btnConsultStudent;
	@FXML
	private Button btnShowAll;
	@FXML
	private Button btnBack;		
	
	@FXML
	private Pane pAddStudent;
	@FXML
	private TextField tfIdStudent;
	@FXML
	private TextField tfEmail;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfTelephone;
	@FXML
	private TextField tfMoneyAvailable;		
	@FXML
	private ComboBox<String> cxGender;	
	@FXML
	private DatePicker dpDateofEntry;		
	@FXML
	private RadioButton rbActive;
	@FXML
	private RadioButton rbNoActive;
	@FXML
	private ToggleGroup isActive;
	@FXML
	private Label lError;
	@FXML
	private Button btnAdd;
	
	@FXML
	private Pane pEditStudent;
	@FXML
	private TextField tfIdEdit;
	@FXML
	private TextField tfEmailEdit;
	@FXML
	private ComboBox<String> cxGenderEdit;
	@FXML
	private DatePicker dpDateofEntryEdit;
	@FXML
	private TextField tfNameEdit;
	@FXML
	private TextField tfTelephoneEdit;	
	@FXML
	private TextField tfMoneyAvailableEdit;
	@FXML
	private RadioButton rbActiveEdit;
	@FXML
	private RadioButton rbNoActiveEdit;
	@FXML
	private ToggleGroup isActiveEdit;
	@FXML
	private Label lErrorEdit;
	@FXML
	private Button btnEdit;
	@FXML
	private Button btnDelete;
	
	@FXML
	private Pane pConsultStudent;
	@FXML
	private TableView<Student> tvDataStudent;
	@FXML
	private TableColumn<Student, String> tcIdStudent;
 	@FXML
    private TableColumn<Student, String> tcName;
    @FXML
    private TableColumn<Student, String> tcEmail;
    @FXML
    private TableColumn<Student, Integer> tcTelephone;
    @FXML
    private TableColumn<Student, String> tcActive;
    @FXML
    private TableColumn<Student, String> tcDateOfEntry;
    @FXML
    private TableColumn<Student, String> tcGender;
    @FXML
    private TableColumn<Student, Double> tcAvailableMoney;
	@FXML
	private TextField tfSearchStudent;
	@FXML
	private Button btnSearchStudent;		
	
	
	@FXML
	private ObservableList<String> genderList = FXCollections.observableArrayList("Masculino","Femenino");	
	
	@FXML
    public void initialize() {
		tvDataStudent.refresh();
        loadStudentList();
    }
			
	// Botones para las distintas ventanas  
	@FXML
	public void HandleAddPane(ActionEvent event) {
		hideAllPanes();
	    pAddStudent.setVisible(true);
	}	
	@FXML
	public void handleEditPane(ActionEvent event) {
		hideAllPanes();
	    pEditStudent.setVisible(true);

	    Student selectedStudent = tvDataStudent.getSelectionModel().getSelectedItem();

	    if (selectedStudent != null) {
	        setStudentData(selectedStudent);
	    } else {
	        JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún estudiante para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	    }
	}	
	
	@FXML
	public void handleConsultPane(ActionEvent event) {
		hideAllPanes();
	    pConsultStudent.setVisible(true);
	}	
	@FXML
	public void handleBack(ActionEvent event) {	   
		closeWindows();	    
	}	
	@FXML
	public void handleShowPane(ActionEvent event) {
		
	}
	
	
	// Agregar estudiante
	@FXML
	public boolean handleAddStudent(ActionEvent event) {
		String messageError = formValidation();
		String idStudentValidation = tfIdStudent.getText();
		
		if(!messageError.isEmpty()) {
			lError.setText(messageError);
			return false;
		}	
		
		if(StudentData.issetStudentByIdStudent(idStudentValidation, StudentData.getStudentList())) {
			notify("Ya existe el estudiante con el carné proporcionado");
			return false;
		}
		
		Student student = new Student();
		student.setIdStudent(tfIdStudent.getText());
		student.setName(tfName.getText());
		student.setEmail(tfEmail.getText());
		student.setTelephone(Integer.parseInt(tfTelephone.getText()));
		
		char gender = (cxGender.getSelectionModel().getSelectedIndex() == 0) ? 'M' : 'F';
		student.setGender(gender);
		
		boolean isActive = (rbActive.isSelected()) ? true : false;
		student.setActive(isActive);
		
		student.setDateOfEntry(dpDateofEntry.getValue());
		student.setMoneyAvailable(Integer.parseInt(tfMoneyAvailable.getText()));
		
		Object[] options = {"Continuar", "Cancelar"};
		
		int confirmOption = JOptionPane.showOptionDialog(
				null, "¿Estás seguro que quieres registrar al estudiante?", 
				"Cofirmación", JOptionPane.DEFAULT_OPTION, 
				JOptionPane.PLAIN_MESSAGE, null, 
				options, options[0]);
		
		if(confirmOption == 0) {
			if(StudentData.saveStudent(student)) {				
				notify("¡Registro exitoso!");
				cleanForm();				
			} else {
				notify("Error al registrar estudiante");
			}
		} else {
			notify("Se canceló el proceso");
		}
		
		return true;
	}
	
	//Consultar estudiante
	@FXML
	public void handleSearch(ActionEvent event) {
	 	String searchQuery = tfSearchStudent.getText().trim();

	    if (searchQuery.isEmpty()) {	       
	        tvDataStudent.setItems(FXCollections.observableArrayList(StudentData.getStudentList()));	        
	    } else {
	        // Encontrar estudiante con el ID 
	        ObservableList<Student> filteredList = FXCollections.observableArrayList();
	        for (Student student : StudentData.getStudentList()) {
	            if (student.getIdStudent().equalsIgnoreCase(searchQuery)) {
	                filteredList.add(student);
	                break;  
	            }
	        }

	        if (filteredList.isEmpty()) {
	            // Si no se encontró ningún estudiante, mostrar un mensaje
	            JOptionPane.showMessageDialog(null, "No se encontró ningún estudiante con el ID proporcionado.", "Información", JOptionPane.INFORMATION_MESSAGE);
	        } else {
	            // Si se encontró el estudiante, se muestra en la tabla
	            tvDataStudent.setItems(filteredList);
	        }
	    }
	}
					
	//Editar estudiante
	@FXML
	public void handleEditStudent() {
		 // Obtener el estudiante seleccionado
	    Student selectedStudent = tvDataStudent.getSelectionModel().getSelectedItem();

	    if (selectedStudent == null) {
	        JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún estudiante para editar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	        return;
	    }

	    // Actualizar los datos del estudiante con la información del formulario de edición
	    selectedStudent.setName(tfNameEdit.getText());
	    selectedStudent.setEmail(tfEmailEdit.getText());
	    selectedStudent.setTelephone(Integer.parseInt(tfTelephoneEdit.getText()));
	    char gender = (cxGenderEdit.getSelectionModel().getSelectedIndex() == 0) ? 'M' : 'F';
	    selectedStudent.setGender(gender);
	    selectedStudent.setDateOfEntry(dpDateofEntryEdit.getValue());
	    selectedStudent.setMoneyAvailable(Double.parseDouble(tfMoneyAvailableEdit.getText()));
	    boolean isActive = rbActiveEdit.isSelected();
	    selectedStudent.setActive(isActive);

	    // Confirmar la edición
	    Object[] options = {"Continuar", "Cancelar"};
	    int confirmOption = JOptionPane.showOptionDialog(
	            null, "¿Estás seguro que quieres guardar los cambios en el estudiante?", 
	            "Confirmación", JOptionPane.DEFAULT_OPTION, 
	            JOptionPane.PLAIN_MESSAGE, null, 
	            options, options[0]);

	    if (confirmOption == 0) {
	        
	        if (StudentData.editStudent(selectedStudent)) {
	            // Actualizar la tabla
	            tvDataStudent.refresh();
	            JOptionPane.showMessageDialog(null, "¡Edición exitosa!");
	        } else {
	            JOptionPane.showMessageDialog(null, "Error al editar el estudiante.");
	        }
	    } else {
	        JOptionPane.showMessageDialog(null, "Se canceló el proceso de edición.");
	    }
	}
	
	// Eliminar estudiante
	@FXML
	public void handleDeleteStudent() {
		
	    Student selectedStudent = tvDataStudent.getSelectionModel().getSelectedItem();
	    
	    if (selectedStudent == null) {	        
	        JOptionPane.showMessageDialog(null, "No se ha seleccionado ningún estudiante para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
	        return;
	    }	    
	    
	    String itemName = selectedStudent.getName();
	    Object[] options = {"SÍ", "NO"};
	    
	    int confirmOption = JOptionPane.showOptionDialog(null, 
	            "¿Estás seguro de que deseas eliminar a " + itemName + "?", 
	            "Confirmación de Eliminación", 
	            JOptionPane.DEFAULT_OPTION,
	            JOptionPane.PLAIN_MESSAGE, 
	            null, options, options[0]);
	    
	    if (confirmOption == 0) {	        
	        if (StudentData.deleteStudent(selectedStudent)) {	            
	            tvDataStudent.getItems().remove(selectedStudent);
	            JOptionPane.showMessageDialog(null, "El estudiante ha sido eliminado exitosamente.");
	        } else {                
	            JOptionPane.showMessageDialog(null, "No se pudo eliminar el estudiante seleccionado.");
	        }
	    }
	}

	
	@FXML
	public void fillCXGender(Event event) {
		LogicControllerStudent.fillCBox(cxGender, genderList);
	}
	
	@FXML
	public void loadStudentList() {
		// Configura las propiedades para mostrar los datos de cada columna
	    tcIdStudent.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdStudent()));
	    tcName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
	    tcEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
	    tcTelephone.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getTelephone()));
	    tcActive.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(cellData.getValue().isActive() ? "Sí" : "No"));
	    tcDateOfEntry.setCellValueFactory(cellData -> 
	        new SimpleObjectProperty<>(cellData.getValue().getDateOfEntry().toString()));
	    tcGender.setCellValueFactory(cellData -> 
	        new SimpleStringProperty(cellData.getValue().getGender() == 'M' ? "M" : "F"));
	    tcAvailableMoney.setCellValueFactory(cellData -> 
	        new SimpleObjectProperty<>(cellData.getValue().getMoneyAvailable()));
	    
	    tvDataStudent.getItems().setAll(StudentData.getStudentList());
	}
	
	@FXML
	public void hideAllPanes() {
	    pAddStudent.setVisible(false);
	    pEditStudent.setVisible(false);	    
	    pConsultStudent.setVisible(false);    
	}
			
	//Validaciones y requerimientos 
	@FXML
	public String formValidation() {
		StringBuilder messageError = new StringBuilder();
		
		if(tfIdStudent.getText().isEmpty()) {
			messageError.append("*Carné es un campo requerido.\n");
		} else if(tfIdStudent.getText().length() > 10) {
			messageError.append("*Carné no puede exceder de 10 letras.\\n");
		}
		
		if(tfName.getText().isEmpty()) {
			messageError.append("*El nombre es un campo requerido.\n");				
		}
		
		if(tfTelephone.getText().isEmpty()) {
			messageError.append("*Telefono es un campo requerido.\n");
		} else if(tfTelephone.getText().length() < 8 || tfTelephone.getText().length() > 10) {
			messageError.append("*Teléfono debe tener entre 8 y 10 números.\n");
		}
		
		if (cxGender.getSelectionModel().getSelectedIndex() == -1) {
	        messageError.append("*Género es un campo requerido.\n");
	    }
		
		if(dpDateofEntry.getValue() == null) {			 
			messageError.append("*Fecha de ingreso es un campo requerido.\n");
		}
		if(isActive.getSelectedToggle() == null) {
			messageError.append("*El estado es un campo requerido.\n");
		}
		
		if(tfMoneyAvailable.getText().isEmpty()) {
			messageError.append("*Dinero disponible es un campo requerido.\n");
		} else {
			try {
				double moneyAvailable = Double.parseDouble(tfMoneyAvailable.getText());
				
				if(moneyAvailable < 5000 || moneyAvailable > 15000) {
					messageError.append("*Dinero disponible debe estar entre 5000 y 15000.\n");
				}
			} catch(NumberFormatException e) {
				 messageError.append("*Dinero disponible debe ser un número válido.\n");
			}
		}
		
		if (messageError.length() > 0) {			
			notify(messageError.toString());
	    }
		
		return messageError.toString();
	}
	
	public void setStudentData(Student student) {
	    hideAllPanes();
	    pEditStudent.setVisible(true);

	    if (student != null) {
	        tfIdEdit.setText(student.getIdStudent());
	        tfIdEdit.setEditable(false);
	        tfNameEdit.setText(student.getName());
	        tfEmailEdit.setText(student.getEmail());
	        tfTelephoneEdit.setText(String.valueOf(student.getTelephone()));
	        
	        String gender = student.getGender() == 'M' ? "Masculino" : "Femenino";
	        cxGenderEdit.setItems(genderList);
	        cxGenderEdit.getSelectionModel().select(gender);
	        cxGenderEdit.setDisable(true);
	        
	        dpDateofEntryEdit.setValue(student.getDateOfEntry());
	        tfMoneyAvailableEdit.setText(String.valueOf(student.getMoneyAvailable()));

	        if (student.isActive()) {
	            rbActiveEdit.setSelected(true);
	        } else {
	            rbNoActiveEdit.setSelected(true);
	        }
	    }
	}



	
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
	
	public void cleanForm() {
	    tfIdStudent.clear();
	    tfEmail.clear();
	    tfName.clear();
	    tfTelephone.clear();
	    tfMoneyAvailable.clear();
	    cxGender.getSelectionModel().clearAndSelect(0);
	    dpDateofEntry.setValue(null);
	    isActive.selectToggle(null);
	    lError.setText("");
	}

}
