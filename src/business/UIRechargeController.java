package business;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;

import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.DatePicker;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.scene.control.TableColumn;

public class UIRechargeController {
	@FXML
	private Button btnAddStudent3;
	@FXML
	private Button btnAddStudent;
	@FXML
	private Pane pAvailableBalance;
	@FXML
	private TextField tfConsultIdStudent;
	@FXML
	private Button btnConsultStudent;
	@FXML
	private TableView tvMoneyAvailable;
	@FXML
	private TableColumn tcIdStudent;
	@FXML
	private TableColumn tcNameStudent;
	@FXML
	private TableColumn tcDateOfRecharge;
	@FXML
	private TableColumn tcBalance;
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

	// Event Listener on Button[#btnAddStudent3].onAction
	@FXML
	public void handleRechargePane(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnAddStudent].onAction
	@FXML
	public void handleConsultPane(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnConsultStudent].onAction
	@FXML
	public void handleConsultBalance(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnBackMenu].onAction
	@FXML
	public void handleBackMenu(ActionEvent event) {
		closeWindows();
	}
	// Event Listener on Button[#btnAddNewStudent].onAction
	@FXML
	public void handleShowToAddStudent(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnBack].onAction
	@FXML
	public void handleBackToConsult(ActionEvent event) {
		// TODO Autogenerated
	}
	// Event Listener on Button[#btnAddRecharge].onAction
	@FXML
	public void handleAddRecharge(ActionEvent event) {
		// TODO Autogenerated
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
}
