package application;

import java.awt.Button;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GUIController {
	
	@FXML
	private void handleDeletePerson() {
	    System.out.println("Hier könnte Ihre Werbung stehen!");
	}
	
	@FXML
	private Label label01;	
	
	@FXML
	private void changeTextFromLabel() {
		label01.setText("Ich habe Lust auf Bier und Titten! ;)");
	}
}
