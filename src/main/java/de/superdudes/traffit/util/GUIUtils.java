package de.superdudes.traffit.util;

import de.superdudes.traffit.exception.ObjectMisplacedException;
import de.superdudes.traffit.exception.ObjectTooCloseException;
import de.superdudes.traffit.exception.SimulationInDbNotPresentException;
import de.superdudes.traffit.exception.SimulationRunningException;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class GUIUtils {

	/**
	 * Shows a modal box with a message corresponding to the exception passed.
	 *
	 * @param onStage
	 * @param exception
	 */
	public static void showErrorDialog(Stage onStage, Exception exception) {
		Stage errorDialog = new Stage();

		errorDialog.initOwner(onStage);
		errorDialog.initModality(Modality.APPLICATION_MODAL);
		errorDialog.initStyle(StageStyle.UTILITY);

		errorDialog.setAlwaysOnTop(true);

		errorDialog.setHeight(110);
		errorDialog.setWidth(290);

		AnchorPane dialogPane = new AnchorPane();

		Label label01 = new Label();
		label01.setTextFill(javafx.scene.paint.Color.RED);
		label01.setLayoutX(10);
		label01.setLayoutY(1);

		Button button01 = new Button();
		button01.setText("OK");
		button01.setLayoutX(120);
		button01.setLayoutY(25);

		if (exception instanceof ObjectTooCloseException) {
			final ObjectTooCloseException objectTooCloseException = (ObjectTooCloseException) exception;
			label01.setText(String.format(
					"%s is too close to %n%s.%n Min distance is 30 cells and 100 cells between construction sites",
					objectTooCloseException.getWhichObject(), objectTooCloseException.getOtherObject()));
			errorDialog.setHeight(160);
			errorDialog.setWidth(500);
			button01.setLayoutY(70);
		} else if (exception instanceof ObjectMisplacedException) {
			label01.setText("A cell is blocked by another object!");
		} else if (exception instanceof SimulationRunningException) {
			label01.setText("Simulation is running");
		} else if (exception instanceof SimulationInDbNotPresentException) {
			label01.setText("No simulation in db saved");
		} else {
			label01.setText("[The Type of the Message is unknown]");
		}

		button01.setOnAction(e -> errorDialog.close());

		dialogPane.getChildren().add(label01);
		dialogPane.getChildren().add(button01);

		Scene dialogScene = new Scene(dialogPane);
		errorDialog.setScene(dialogScene);
		errorDialog.showAndWait();
	}
}
