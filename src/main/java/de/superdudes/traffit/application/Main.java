package de.superdudes.traffit.application;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class Main extends Application {

	@Override
	public void start(Stage primaryStage) {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("GUI.fxml"));

			AnchorPane root = (AnchorPane) loader.load();

			GUIController controller = loader.getController();
			controller.sayHi();
			//root.getChildren().add(controller.button01);
			//System.out.println(controller.button01.getHeight());

			Scene scene = new Scene(root);

			System.out.println(root.getChildren().toString());

			// fixed window minimum size
			root.setPrefSize(1280, 800);
			primaryStage.setMinHeight(839);
			primaryStage.setMinWidth(1296);

			primaryStage.setFullScreen(true);

			// Listener to resize the window
			scene.widthProperty().addListener(new ChangeListener<Number>() {

				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
						Number newSceneWidth) {
					System.out.println("Width: " + newSceneWidth);
					//controller.setCurrentWidth(newSceneWidth);
					root.setPrefWidth((double) newSceneWidth);
					//controller.setCurrentWidth(newSceneWidth);
				}
			});

			scene.heightProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneHeight,
						Number newSceneHeight) {
					System.out.println("Height: " + newSceneHeight);
					root.setPrefHeight((double) newSceneHeight);
					
					controller.createLane((int) newSceneHeight);
				}
			});
			
			//System.out.println("Test: " + controller.getCurrentWidth());
			
			
			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
			primaryStage.setScene(scene);

			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
