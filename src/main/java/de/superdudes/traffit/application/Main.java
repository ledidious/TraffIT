package de.superdudes.traffit.application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
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
			// primaryStage.setFullScreen(true);

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
					controller.lane1.getChildren().clear();
					controller.lane1.getChildren().addAll(controller.buildLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));

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
					controller.lane1.getChildren().clear();
					controller.lane1.getChildren().addAll(controller.buildLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));

					controller.lane2.getChildren().clear();
					controller.lane2.getChildren().addAll(controller.buildLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));

					controller.signlane.getChildren().clear();
					controller.signlane.getChildren().addAll(controller.buildSignLane(
							(controller.currentWidth.intValue() - 100), controller.currentHeight.intValue()));
				}
			});

			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.show();

			// Drag&Drop Test
			controller.ivMotorcycle.setOnMousePressed(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					// controller.ivMotorcycle.setMouseTransparent(true);
					System.out.println("Event on Source: mouse pressed");
					event.setDragDetect(true);

				}
			});

			/*
			 * controller.ivMotorcycle.setOnMouseReleased(new EventHandler<MouseEvent>() {
			 * public void handle(MouseEvent event) {
			 * controller.ivMotorcycle.setMouseTransparent(false);
			 * System.out.println("Event on Source: mouse released"); } });
			 */

			controller.ivMotorcycle.setOnDragDetected(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					controller.ivMotorcycle.startFullDrag();
					System.out.println("Event on Source: drag detected");
				}
			});

			/*
			 * // Add mouse event handlers for the target
			 * controller.ivTarget.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
			 * public void handle(MouseEvent event) {
			 * System.out.println("Event on Target: mouse dragged"); } });
			 * 
			 * controller.ivTarget.setOnMouseDragOver(new EventHandler<MouseEvent>() {
			 * public void handle(MouseEvent event) {
			 * System.out.println("Event on Target: mouse drag over"); } });
			 */

			controller.ivTarget.setOnMouseDragReleased(new EventHandler<MouseEvent>() {
				public void handle(MouseEvent event) {
					controller.ivTarget.setImage(controller.ivMotorcycle.getImage());
					System.out.println("Event on Target: mouse drag released");
				}
			});

			/*
			 * controller.ivTarget.setOnMouseDragExited(new EventHandler<MouseEvent>() {
			 * public void handle(MouseEvent event) {
			 * System.out.println("Event on Target: mouse drag exited"); } });
			 */

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
			controller.button05.setOnAction(e -> {primaryStage.setFullScreen(true);});
			
			

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void dragAndDrop(GUIController controller) {
		// Drag&Drop Test
		controller.ivMotorcycle.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				controller.ivMotorcycle.setMouseTransparent(true);
				System.out.println("Event on Source: mouse pressed");
				event.setDragDetect(true);
			}
		});

		controller.ivMotorcycle.setOnMouseReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				controller.ivMotorcycle.setMouseTransparent(false);
				System.out.println("Event on Source: mouse released");
			}
		});

		controller.ivMotorcycle.setOnDragDetected(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				controller.ivMotorcycle.startFullDrag();
				System.out.println("Event on Source: drag detected");
			}
		});

		// Add mouse event handlers for the target
		controller.ivTarget.setOnMouseDragEntered(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Event on Target: mouse dragged");
			}
		});

		controller.ivTarget.setOnMouseDragOver(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Event on Target: mouse drag over");
			}
		});

		controller.ivTarget.setOnMouseDragReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				controller.ivTarget.setImage(controller.ivMotorcycle.getImage());
				System.out.println("Event on Target: mouse drag released");
			}
		});

		controller.ivTarget.setOnMouseDragExited(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				System.out.println("Event on Target: mouse drag exited");
			}
		});
	}

	public static void main(String[] args) {
		launch(args);
	}
}
