package business;

import javafx.fxml.FXML;

import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import data.LogicControllerStudent;
import domain.Student;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.scene.control.ComboBox;

import javafx.scene.image.ImageView;

import javafx.scene.control.DatePicker;

import javafx.scene.control.TableView;

import javafx.scene.layout.Pane;

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
	private Button btnSearchStudent;
	@FXML
	private Button btnDelete;
	@FXML
	private Pane pAddStudent;
	@FXML
	private TextField tfIdStudent;
	@FXML
	private TextField tfEmail;
	@FXML
	private ComboBox<String> cxGender;
	@FXML
	private DatePicker dpDateofEntry;
	@FXML
	private TextField tfName;
	@FXML
	private TextField tfTelephone;
	@FXML
	private ComboBox<Student> cxStateStudent;
	@FXML
	private TextField tfMoneyAvailable;
	@FXML
	private Button btnAdd;
	@FXML
	private Pane pEditStudent;
	@FXML
	private TextField tfIdEdit;
	@FXML
	private TextField tfEmailEdit;
	@FXML
	private ComboBox<Student> cxGenderEdit;
	@FXML
	private DatePicker dpDateofEntryEdit;
	@FXML
	private TextField tfNameEdit;
	@FXML
	private TextField tfTelephoneEdit;
	@FXML
	private ComboBox<Student> cxStateStudentEdit;
	@FXML
	private TextField tfMoneyAvailableEdit;
	@FXML
	private Button btnEdit;
	@FXML
	private Pane pConsultStudent;
	@FXML
	private TableView<Student> tvDataStudent;
	@FXML
	private TextField tfSearchStudent;
	@FXML
	private ImageView btnPDeleteStudent;
	@FXML
	private Pane pDeleteStudent;
	@FXML
	private TableView<Student> tvDataStudentDelete;	
	@FXML
	private ObservableList<String> genderList = FXCollections.observableArrayList("Masculino","Femenino");	
			
	// Event Listener on Button[#btnAddStudent].onAction
	@FXML
	public void HandleAddPane(ActionEvent event) {
		hideAllPanes();
	    pAddStudent.setVisible(true);
	}
	// Event Listener on Button[#btnEditStudent].onAction
	@FXML
	public void handleEditPane(ActionEvent event) {
		hideAllPanes();
	    pEditStudent.setVisible(true);
	}
	// Event Listener on Button[#btnDeleteStudent].onAction
	@FXML
	public void handleDeletePane(ActionEvent event) {
		 hideAllPanes();
		 pDeleteStudent.setVisible(true);    	
	}
	// Event Listener on Button[#btnConsultStudent].onAction
	@FXML
	public void handleConsultPane(ActionEvent event) {
		hideAllPanes();
	    pConsultStudent.setVisible(true);
	}
	// Event Listener on Button[#btnShowAll].onAction
	@FXML
	public void handleShowPane(ActionEvent event) {
		
	}
	// Event Listener on Button[#btnAdd].onAction
	@FXML
	public void handleAddStudent(ActionEvent event) {
		
	}
	
	@FXML
	public void handleBack(ActionEvent event) {
	    hideAllPanes();
	    pOptionCRUD.setVisible(true); 
	}
	
	@FXML
	public void handleSearch(ActionEvent event) {
		
	}
		
	public void fillCXGender(Event event) {
		LogicControllerStudent.fillCBox(cxGender, genderList);
	}
	
	public void fillCXState(ActionEvent event) {
		
	}
	
	@FXML
	public void hideAllPanes() {
	    pAddStudent.setVisible(false);
	    pEditStudent.setVisible(false);
	    pDeleteStudent.setVisible(false);
	    pConsultStudent.setVisible(false);
	    // Agrega más paneles si tienes otros
	}


}
