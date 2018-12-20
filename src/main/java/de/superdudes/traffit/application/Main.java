package de.superdudes.traffit.application;

import java.awt.Dimension;
import java.util.Deque;
import java.util.LinkedList;

import de.superdudes.traffit.SimulationManager;
import de.superdudes.traffit.dto.StartingGrid;
import de.superdudes.traffit.dto.Street;
import de.superdudes.traffit.dto.Vehicle;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("TraffIT");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("GUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			GUIController controller = loader.getController();
			// controller.sayHi();

			Scene scene = new Scene(root);

			// fixed window minimum size
			root.setPrefSize(1280, 800);
			controller.currentHeight.setValue(800);
			controller.currentWidth.setValue(1280);
			primaryStage.setMinHeight(839);
			primaryStage.setMinWidth(1296);
			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

			controller.lane1.getChildren().addAll(controller.buildLane(1280, 800));
			controller.lane2.getChildren().addAll(controller.buildLane(1280, 800));
			controller.signlane.getChildren().addAll(controller.buildSignLane(1280, 800));
			
			StartingGrid grid1 = new StartingGrid("grid1");
			Street street1 = new Street(1280, 2, grid1);
			grid1.setStreet(street1);
			SimulationManager.setRunningSimulation(grid1);

			// Listener to resize the window
			scene.widthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
						Number newSceneWidth) {
					root.setPrefWidth((double) newSceneWidth);
					controller.currentWidth.setValue((double) newSceneWidth - 40);
					Cell.number = 0;
					controller.lane1.getChildren().clear();
					controller.lane1.getChildren().addAll(controller.buildLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));

					Cell.number = 0;
					controller.lane2.getChildren().clear();
					controller.lane2.getChildren().addAll(controller.buildLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));

					// Warum fehlte hier der Reset? Un�tig?
					Cell.number = 0;
					controller.signlane.getChildren().clear();
					controller.signlane.getChildren().addAll(controller.buildSignLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));
					
					street1.setLength((int) controller.lane1.getWidth()); 
					//System.out.println("lane1 breite: " + controller.lane1.getWidth());
					//System.out.println(street1.getLength());
				}
			});

			scene.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
						Number newSceneHeight) {
					root.setPrefHeight((double) newSceneHeight);
					controller.currentHeight.setValue((double) newSceneHeight - 40);
					Cell.number = 0;
					controller.lane1.getChildren().clear();
					controller.lane1.getChildren().addAll(controller.buildLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));

					Cell.number = 0;
					controller.lane2.getChildren().clear();
					controller.lane2.getChildren().addAll(controller.buildLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));

					Cell.number = 0;
					controller.signlane.getChildren().clear();
					controller.signlane.getChildren().addAll(controller.buildSignLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));
				}
			});	
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();

			// Event Handler to change the size of the Street
			controller.streetSize.setOnAction(e -> {

				Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
				double screenWidth = screenSize.getWidth();

				int newWidth = Integer.parseInt(controller.streetSize.getText());
				if (newWidth >= primaryStage.getMinWidth() & newWidth <= screenWidth) {
					primaryStage.setWidth(newWidth);
					controller.streetSize.setText(String.valueOf((int) primaryStage.getWidth()));
				} else if (newWidth > screenWidth) {
					primaryStage.setWidth(screenWidth);
					controller.streetSize.setText(String.valueOf((int) screenWidth));
				} else {
					primaryStage.setWidth(primaryStage.getMinWidth());
					controller.streetSize.setText(String.valueOf((int) primaryStage.getMinWidth()));
				}
			});

			// Drag&Drop Function
			// **** Vehicle ****
			controller.ivMotorcycle.setOnMousePressed(e -> {
				System.out.println("Event on Source: mouse pressed.");
				e.setDragDetect(true);
			});

			controller.ivCar.setOnMousePressed(e -> {
				System.out.println("Event on Source: mouse pressed.");
				e.setDragDetect(true);
			});

			controller.ivTruck.setOnMousePressed(e -> {
				System.out.println("Event on Source: mouse pressed.");
				e.setDragDetect(true);
			});

			controller.ivCar.setOnDragDetected(e -> {
				controller.ivCar.startFullDrag();
				System.out.println("Event on Source: drag detected");
			});

			controller.ivMotorcycle.setOnDragDetected(e -> {
				controller.ivMotorcycle.startFullDrag();
				System.out.println("Event on Source: drag detected");
			});

			controller.ivTruck.setOnDragDetected(e -> {
				controller.ivTruck.startFullDrag();
				System.out.println("Event on Source: drag detected");
			});

			// **** Signs ****
			controller.ivConstruction.setOnMousePressed(e -> {
				System.out.println("Event on Source: mouse pressed.");
				e.setDragDetect(true);
			});

			controller.ivSpeedLimit50.setOnMousePressed(e -> {
				System.out.println("Event on Source: mouse pressed.");
				e.setDragDetect(true);
			});

			controller.ivSpeedLimit70.setOnMousePressed(e -> {
				System.out.println("Event on Source: mouse pressed.");
				e.setDragDetect(true);
			});

			controller.ivSpeedLimit100.setOnMousePressed(e -> {
				System.out.println("Event on Source: mouse pressed.");
				e.setDragDetect(true);
			});

			controller.ivConstruction.setOnDragDetected(e -> {
				controller.ivConstruction.startFullDrag();
				System.out.println("Event on Source: drag detected");
			});

			controller.ivSpeedLimit50.setOnDragDetected(e -> {
				controller.ivSpeedLimit50.startFullDrag();
				System.out.println("Event on Source: drag detected");
			});

			controller.ivSpeedLimit70.setOnDragDetected(e -> {
				controller.ivSpeedLimit70.startFullDrag();
				System.out.println("Event on Source: drag detected");
			});

			controller.ivSpeedLimit100.setOnDragDetected(e -> {
				controller.ivSpeedLimit100.startFullDrag();
				System.out.println("Event on Source: drag detected");
			});

			/*
			 * Event Handler for locking the window size and starting the Simulation.
			 * Necessary because you can't manipulate the Stage from within the Controller
			 */
			controller.button01.setOnAction(e -> {
				// primaryStage.setResizable(false);
				controller.startSimulation();
			});

			// vice versa
			controller.button02.setOnAction(e -> {
				// primaryStage.setResizable(true);
				controller.stopSimulation();
			});

			// Event Handle for Fullscreen
			controller.button05.setOnAction(e -> {
				if (primaryStage.isFullScreen()) {
					controller.streetSize.setDisable(false);
					primaryStage.setFullScreen(false);
				} else {
					controller.streetSize.setDisable(true);
					primaryStage.setFullScreen(true);
				}
			});

			controller.streetSize.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*")) {
						controller.streetSize.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
