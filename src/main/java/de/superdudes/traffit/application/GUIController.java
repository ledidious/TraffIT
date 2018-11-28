package de.superdudes.traffit.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.io.File;

public class GUIController {
	public Number currentWidth;

	public Number getCurrentWidth() {
		return currentWidth;
	}

	public void setCurrentWidth(Number currentWidth) {
		this.currentWidth = currentWidth;
	}

	// Set the path to the icons of the vehicles
	private String pathToCar = new File("src/main/resources/car.png").getAbsolutePath();
	private String pathToMotorcycle = new File("src/main/resources/motorcycle.png").getAbsolutePath();
	private String pathToTruck = new File("src/main/resources/truck.png").getAbsolutePath();

	// Set the path to the icons of the signs
	private String pathToSpeedLimit50 = new File("src/main/resources/limit50.png").getAbsolutePath();
	private String pathToSpeedLimit70 = new File("src/main/resources/limit70.png").getAbsolutePath();
	private String pathToSpeedLimit100 = new File("src/main/resources/limit100.png").getAbsolutePath();
	private String pathToContstruction = new File("src/main/resources/construction.png").getAbsolutePath();

	// Vehicle
	private Image iconCar = new Image("file:" + pathToCar);
	private Image iconMotorcycle = new Image("file:" + pathToMotorcycle);
	private Image iconTruck = new Image("file:" + pathToTruck);

	// Speed Limit
	private Image iconSpeedLimit50 = new Image("file:" + pathToSpeedLimit50);
	private Image iconSpeedLimit70 = new Image("file:" + pathToSpeedLimit70);
	private Image iconSpeedLimit100 = new Image("file:" + pathToSpeedLimit100);

	// Signs
	private Image iconConstruction = new Image("file:" + pathToContstruction);

	// ImageViews
	@FXML
	private ImageView ivCar;

	@FXML
	private ImageView ivMotorcycle;

	@FXML
	private ImageView ivTruck;

	@FXML
	private ImageView ivSpeedLimit50;

	@FXML
	private ImageView ivSpeedLimit70;

	@FXML
	private ImageView ivSpeedLimit100;

	@FXML
	private ImageView ivConstruction;

	// Buttons
	@FXML
	public Button button01;

	@FXML
	private Button button02;

	// Labels
	@FXML
	private Label label01;

	@FXML
	public Pane lane1;
	
	@FXML
	private GridPane linuxMint;

	@FXML
	public Cell[] createLane(Number currentWidth) {

		Cell[] ca = new Cell[(int) currentWidth];

		for (int i = 0; i < (int) currentWidth; i++) {

			ca[i] = new Cell(20 + i, 20, 1, 20, Color.RED);

		}

		return ca;

	}

	public GUIController() {
		System.out.println("Copyright: Superdudes 2018");
	}

	@FXML
	public Pane pane01;
	
	@FXML
	private AnchorPane anchPane01;
	
	@FXML
	public void initialize() {
		System.out.println("Init GUI");
		
		ivCar.setImage(iconCar);
		ivMotorcycle.setImage(iconMotorcycle);
		ivTruck.setImage(iconTruck);
		ivCar.setImage(iconCar);
		ivMotorcycle.setImage(iconMotorcycle);
		ivTruck.setImage(iconTruck);

		System.out.println("HHUHUUHDUASHDUHSUDHSAUD" + getCurrentWidth());
		//lane1.getChildren().addAll(createLane());
		
		//System.out.println(dc.getCurrentWidth());
		
		ivSpeedLimit50.setImage(iconSpeedLimit50);
		ivSpeedLimit70.setImage(iconSpeedLimit70);
		ivSpeedLimit100.setImage(iconSpeedLimit100);

		ivConstruction.setImage(iconConstruction);
	}

	// String path = new File( "src/main/resources/car.png" ).getAbsolutePath();

	@FXML
	private void handleDeletePerson() {
		System.out.println("Hier k�nnte Ihre Werbung stehen!");
	}

	@FXML
	private void changeTextFromLabel() {
		label01.setText("Ich habe Lust auf Bier und Titten! ;)");
		
		Stage stage = (Stage) anchPane01.getScene().getWindow();
		System.out.println(stage.getWidth());
	}
	
	public void sayHi() {
		System.out.println("Moinsen!");
	}
	
	public void drawStreet() {
		
	}
}