package de.superdudes.traffit.application;

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
			controller.sayHi();

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

					controller.signlane.getChildren().clear();
					controller.signlane.getChildren().addAll(controller.buildSignLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));
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

			primaryStage.show();

			// Drag&Drop Function
			controller.ivMotorcycle.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					// controller.ivMotorcycle.setMouseTransparent(true);
					System.out.println("Event on Source: mouse pressed");
					event.setDragDetect(true);

				}
			});

			controller.ivCar.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					// controller.ivMotorcycle.setMouseTransparent(true);
					System.out.println("Event on Source: mouse pressed");
					event.setDragDetect(true);

				}
			});

			controller.ivTruck.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					// controller.ivMotorcycle.setMouseTransparent(true);
					System.out.println("Event on Source: mouse pressed");
					event.setDragDetect(true);

				}
			});

			controller.ivMotorcycle.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					controller.ivMotorcycle.startFullDrag();
					System.out.println("Event on Source: drag detected");
				}
			});

			controller.ivCar.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					controller.ivCar.startFullDrag();
					System.out.println("Event on Source: drag detected");
				}
			});

			controller.ivTruck.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					controller.ivTruck.startFullDrag();
					System.out.println("Event on Source: drag detected");
				}
			});

			// Event Handler for locking the window size and starting the Simulation.
			// Necessary because you can't manipulate the Stage from within the Controller
			controller.button01.setOnAction(e -> {
				primaryStage.setResizable(false);
				controller.startSimulation();
			});

			// vice versa
			controller.button02.setOnAction(e -> {
				primaryStage.setResizable(true);
				controller.stopSimulation();
			});

			// Event Handle for Fullscreen
			controller.button05.setOnAction(e -> {
				if (primaryStage.isFullScreen())
					primaryStage.setFullScreen(false);
				else
					primaryStage.setFullScreen(true);
			});

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
