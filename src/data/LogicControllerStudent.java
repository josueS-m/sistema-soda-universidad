package data;

import javafx.collections.ObservableList;
import javafx.scene.control.ComboBox;

public class LogicControllerStudent {
	
	//Llenar los comboBox con datos necesarios
	public static void fillCBox(ComboBox<String> fillCombo, ObservableList<String> data) {
		fillCombo.setItems(data);;
	}		

}
