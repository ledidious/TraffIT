package de.superdudes.traffit.application;

import de.superdudes.traffit.SimulationManager;
import de.superdudes.traffit.util.Resources;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class GUIController {

	public DoubleProperty currentWidth = new SimpleDoubleProperty();
	public DoubleProperty currentHeight = new SimpleDoubleProperty();

	// Defines the path to the icons of the vehicles.
	private String pathToCar = Resources.getClasspathResource("png/car.png");
	private String pathToMotorcycle = Resources.getClasspathResource("png/motorcycle.png");
	private String pathToTruck = Resources.getClasspathResource("png/truck.png");

	// Defines the path to the icons of the signs.
	private String pathToSpeedLimit50 = Resources.getClasspathResource("png/limit50.png");
	private String pathToSpeedLimit70 = Resources.getClasspathResource("png/limit70.png");
	private String pathToSpeedLimit100 = Resources.getClasspathResource("png/limit100.png");
	private String pathToContstruction = Resources.getClasspathResource("png/construction.png");

	// Vehicles
	private Image iconCar = new Image("file:" + pathToCar);
	private Image iconMotorcycle = new Image("file:" + pathToMotorcycle);
	private Image iconTruck = new Image("file:" + pathToTruck);

	// Speed Limits
	private Image iconSpeedLimit50 = new Image("file:" + pathToSpeedLimit50);
	private Image iconSpeedLimit70 = new Image("file:" + pathToSpeedLimit70);
	private Image iconSpeedLimit100 = new Image("file:" + pathToSpeedLimit100);

	// Other Signs
	private Image iconConstruction = new Image("file:" + pathToContstruction);

	// Text fields and Spinners
	@FXML
	TextField streetSize;

	// ImageViews
	@FXML
	public ImageView ivCar;
	@FXML
	public ImageView ivMotorcycle;
	@FXML
	public ImageView ivTruck;
	@FXML
	public ImageView ivSpeedLimit50;
	@FXML
	public ImageView ivSpeedLimit70;
	@FXML
	public ImageView ivSpeedLimit100;
	@FXML
	public ImageView ivConstruction;

	// Buttons
	@FXML
	private Button button01;
	@FXML
	private Button button02;
	@FXML
	public Button button03;
	@FXML
	private Button button04;
	@FXML
	public Button button05;
	@FXML
	private Button button06;

	// Panes
	@FXML
	public Pane lane1;
	@FXML
	public Pane lane2;
	@FXML
	public Pane signlane;
	@FXML
	private Pane pane01;
	@FXML
	private AnchorPane anchPane01;

	// The Logic
	public Pane getLane1() {
		return lane1;
	}

	public void setLane1(Pane lane1) {
		this.lane1 = lane1;
	}

	public Pane getLane2() {
		return lane2;
	}

	public void setLane2(Pane lane2) {
		this.lane2 = lane2;
	}

	public Pane getSignlane() {
		return signlane;
	}

	public void setSignlane(Pane signlane) {
		this.signlane = signlane;
	}

	/**
	 * resets the cell counter to 0, clears all cell objects and build a new lane
	 * depending on the current width and height of the window size
	 *
	 * @param width
	 * @param height
	 */
	public void buildStreet(int width, int height) {
		Cell.number = 0;

		lane1.getChildren().clear();
		lane1.getChildren().addAll(buildLane(width, height));

		Cell.number = 0;

		lane2.getChildren().clear();
		lane2.getChildren().addAll(buildLane(width, height));

		Cell.number = 0;

		signlane.getChildren().clear();
		signlane.getChildren().addAll(buildSignLane(width, height));
	}

	/**
	 * builds a lane for the vehicles in the form of a cellarray
	 * depending on the current width and height of the window size
	 *
	 * @param width
	 * @param height
	 * @return ca
	 */
	public Cell[] buildLane(int width, int height) {
		Cell[] ca = new Cell[width];

		for (int i = 0; i < width; i++) {
			ca[i] = new Cell(60 + i, 20, 1, (height * 0.15), Color.GRAY);
		}

		return ca;
	}

	/**
	 * builds a lane for the signs in the form of a cellarray
	 * depending on the current width and height of the window size
	 *
	 * @param width
	 * @param height
	 * @return ca
	 */
	public Cell[] buildSignLane(int width, int height) {
		Cell[] ca = new Cell[width];

		for (int i = 0; i < width; i++) {
			ca[i] = new Cell(60 + i, 20, 1, (height * 0.15), Color.TRANSPARENT);
		}

		return ca;
	}

	public GUIController() {
		System.out.println("Copyright: Superdudes 2018");
	}

	/**
	 * Called to initialize a controller after its root element has been completely processed.
	 */
	@FXML
	public void initialize() {
		System.out.println("Init GUI");

		ivCar.setImage(iconCar);
		ivMotorcycle.setImage(iconMotorcycle);
		ivTruck.setImage(iconTruck);
		ivCar.setImage(iconCar);
		ivMotorcycle.setImage(iconMotorcycle);
		ivTruck.setImage(iconTruck);

		ivSpeedLimit50.setImage(iconSpeedLimit50);
		ivSpeedLimit70.setImage(iconSpeedLimit70);
		ivSpeedLimit100.setImage(iconSpeedLimit100);

		ivConstruction.setImage(iconConstruction);

		button02.setDisable(true);
		button03.setDisable(false);
		button04.setDisable(false);
		button06.setDisable(true);
		streetSize.setDisable(true);
	}

	/**
	 * Locks the program for further changes and starts the simulation.
	 */
	@FXML
	private void startSimulation() {
		button01.setDisable(true);
		button02.setDisable(false);
		button03.setDisable(true);
		button04.setDisable(true);
		button05.setDisable(true);
		button06.setDisable(false);

		streetSize.setDisable(true);
		System.out.println("Simulation has been started...");

		SimulationManager.start();
	}

	/**
	 * Pauses the simulation and allows to save the current state.
	 */
	@FXML
	private void haltSimulation() {
		button01.setDisable(false);
		button04.setDisable(false);
		button06.setDisable(true);
		System.out.println("Simulation has been paused...");

		SimulationManager.halt();
	}

	/**
	 * Ends the simulation and resets all vehicles to their corresponding starting positions.
	 */
	@FXML
	private void stopSimulation() {
		button01.setDisable(false);
		button02.setDisable(true);
		button03.setDisable(false);
		button04.setDisable(false);
		button05.setDisable(false);
		button06.setDisable(true);

		streetSize.setDisable(false);
		System.out.println("Simulation has been stopped!");

		SimulationManager.stop();
	}

	/**
	 * Loads a stored simulation from the database.
	 */
	@FXML
	public boolean loadSimulation() {
		return SimulationManager.load();
	}

	/**
	 * Saves the current state of the simulation in the database.
	 */
	@FXML
	private void saveSimulation() {
		SimulationManager.save();
	}
}