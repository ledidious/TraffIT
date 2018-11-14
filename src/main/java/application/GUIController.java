package application;

import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GUIController implements Initializable {
	@FXML
	private HBox Lane1;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		// TODO (don't really need to do anything here).
	}

	@FXML
	protected void fillWithLane() {

		ArrayList<Cell> CellsLane1 = new ArrayList<>();

		for (int i = 0; i < 1900; i++) {

			Cell cell = (new Cell(i + 10, 20, 1, 40, Color.BLACK));
			CellsLane1.add(cell);
		}
		
		fillWithLane();
	}
}
