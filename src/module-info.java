module Test {
	requires javafx.controls;
	requires com.fasterxml.jackson.databind;
	requires com.fasterxml.jackson.datatype.jsr310;
	
	exports domain;
	opens business to javafx.graphics, javafx.fxml;
}
