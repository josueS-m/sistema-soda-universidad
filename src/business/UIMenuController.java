package business;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class UIMenuController {
	
	@FXML
	private Button btnStudentMenu;
	@FXML
	private Button btnServiceMenu;
	@FXML
	private Button btnRechargeMenu;    
	
	@FXML
	public void handleUIStudent(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIStudent.fxml"));
			Parent root = loader.load();
			UIStudentController controller = loader.getController();			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(
					e -> controller.closeWindows());
			
			Stage temp = (Stage) this.btnStudentMenu.getScene().getWindow();
			temp.close();
			
		}catch (IOException e) {
			 e.printStackTrace();
		}	
	}
	
	@FXML
	public void handleUIService(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIService.fxml"));
			Parent root = loader.load();
			UIServiceController controller = loader.getController();			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(
					e -> controller.closeWindows());
			
			Stage temp = (Stage) this.btnServiceMenu.getScene().getWindow();
			temp.close();
			
		}catch (IOException e) {
			 e.printStackTrace();
		}	
	}
	
	@FXML
	public void handleUIRecharge(ActionEvent event) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("/presentation/UIRecharge.fxml"));
			Parent root = loader.load();
			UIRechargeController controller = loader.getController();			
			Scene scene = new Scene(root);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.show();
			
			stage.setOnCloseRequest(
					e -> controller.closeWindows());
			
			Stage temp = (Stage) this.btnRechargeMenu.getScene().getWindow();
			temp.close();
			
		}catch (IOException e) {
			 e.printStackTrace();
		}			
		
	}
	
	
}
