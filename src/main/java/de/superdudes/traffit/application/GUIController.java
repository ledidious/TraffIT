package de.superdudes.traffit.application;

import java.io.File;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class GUIController {

	//Set the path to the icons of the vehicles
	private String pathToCar = new File("src/main/resources/car.png").getAbsolutePath();
	private String pathToMotorcycle = new File("src/main/resources/motorcycle.png").getAbsolutePath();
	private String pathToTruck = new File("src/main/resources/truck.png").getAbsolutePath();
	
	private Image iconCar = new Image("file:" + pathToCar);
	private Image iconMotorcycle = new Image("file:" + pathToMotorcycle);
	private Image iconTruck = new Image("file:" + pathToTruck);
	
	@FXML
	private ImageView ivCar;

	@FXML
	private ImageView ivMotorcycle;
	
	@FXML
	private ImageView ivTruck;
	
	@FXML
	private Button button01;

	@FXML
	private Button button02;
	
	@FXML 
	private Pane pane01;
	
	@FXML 
	private void resizeButton() {
		
		//System.out.println(button01.getLayoutX());
		//System.out.println(pane01.getParent().getLayoutX());
		
		
		pane01.widthProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldWidthValue, Number newWidthValue) {
				//button01.setLayoutX((double)newWidthValue - button01.getWidth());
				System.out.println(pane01.getBoundsInParent().getWidth());
			}
		});
		
		
		pane01.heightProperty().addListener(new ChangeListener<Number>() {

			@Override
			public void changed(ObservableValue<? extends Number> observable, Number oldHeightValue, Number newHeightValue) {
				//button01.setLayoutX((double)newHeightValue - button01.getHeight());	
				System.out.println(pane01.getBoundsInParent().getHeight());
			}
		});
		
		
	}

	@FXML
	private Label label01;
	
	public GUIController() {
		System.out.println("Copyright: Superdudes 2018");
	}

	@FXML
	public void initialize() {
		ivCar.setImage(iconCar);
		ivMotorcycle.setImage(iconMotorcycle);
		ivTruck.setImage(iconTruck);
		resizeButton();
	}
	
	String path = new File("src/main/resources/car.png").getAbsolutePath();
		

	@FXML
	private void handleDeletePerson() {
		System.out.println("Hier könnte Ihre Werbung stehen!");
	}

	@FXML
	private void changeTextFromLabel() {
		label01.setText("Ich habe Lust auf Bier und Titten! ;)");
	}
}