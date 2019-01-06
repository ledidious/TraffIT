package de.superdudes.traffit.application;

import de.superdudes.traffit.SimulationManager;
import de.superdudes.traffit.dto.*;
import javafx.application.Application;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableBooleanValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.awt.*;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Main extends Application {

	/**
	 * Start the JavaFX Application
	 * <p>
	 * Contains Change Listener for the following cases: -necessity to update the
	 * position of the vehicle -The Width of the window changes -The height of the
	 * window changes -The user tries to enter letters into the textbox.
	 * <p>
	 * It also includes event handlers for various JavaFX elements.
	 */
	@Override
	public void start(Stage primaryStage) {
		try {
			primaryStage.setTitle("TraffIT");
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(getClass().getResource("GUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();
			GUIController controller = loader.getController();

			Scene scene = new Scene(root);

			// fixed window minimum size
			primaryStage.setMinWidth(1280);
			primaryStage.setMinHeight(800);

			primaryStage.setWidth(1280);
			primaryStage.setHeight(800);
			controller.currentWidth.setValue(primaryStage.getWidth());
			controller.currentHeight.setValue(primaryStage.getHeight());

			primaryStage.setFullScreen(true);
			primaryStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);

			controller.buildStreet((int) root.getWidth(), (int) root.getHeight());

			StartingGrid backendGrid = new StartingGrid("grid1");
			new Street(1280, 2, backendGrid);
			SimulationManager.setStartingGrid(backendGrid);

			scene.getStylesheets().add("css/application.css");

			// #### Change Listener ####
			// Change Listener for detecting changes in the backend of the simulation
			ObservableBooleanValue nemesis = new SimpleBooleanProperty(
					SimulationManager.getGenWasRendered().getValue());
			((BooleanProperty) nemesis).bindBidirectional(SimulationManager.getGenWasRendered());

			nemesis.addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
					if (newValue) {
						repaintContents(backendGrid, controller);
					}
				}
			});

			// Change Listener to detect changes made to the width of the window
			scene.widthProperty().addListener(new ChangeListener<Number>() {
				@Override
				public void changed(ObservableValue<? extends Number> observableValue, Number oldSceneWidth,
									Number newSceneWidth) {
					root.setPrefWidth((double) newSceneWidth);
					controller.currentWidth.setValue((double) newSceneWidth - 40);

					// Rebuilt the street
					controller.buildStreet((controller.currentWidth.intValue() - 100),
							controller.currentHeight.intValue());
					new Street((int) controller.lane1.getWidth(), 2, backendGrid);
				}
			});

			// Change Listener to detect changes made to the height of the window
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
			// Change Listener for preventing the user to enter letters into the textbox
			controller.streetSize.textProperty().addListener(new ChangeListener<String>() {
				@Override
				public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
					if (!newValue.matches("\\d*")) {
						controller.streetSize.setText(newValue.replaceAll("[^\\d]", ""));
					}
				}
			});

			// #### Event Handler ####
			// Event Handler to change the length of the Street
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

				// Build street new (is also done in widthProperty changeListener but for safety
				// reasons that same amount of gui and backend cells)
				controller.buildStreet((controller.currentWidth.intValue() - 100), controller.currentHeight.intValue());

				// Remove all vehicles in backend
				backendGrid.getStreet().getLanes().forEach(Lane::removeAllObjects);
			});

			// Event Handler for the Drag&Drop Function
			// **** Vehicles ****
			controller.ivMotorcycle.setOnMousePressed(e -> {
				e.setDragDetect(true);
			});

			controller.ivMotorcycle.setOnDragDetected(e -> {
				controller.ivMotorcycle.startFullDrag();
			});

			controller.ivCar.setOnMousePressed(e -> {
				e.setDragDetect(true);
			});

			controller.ivCar.setOnDragDetected(e -> {
				controller.ivCar.startFullDrag();
			});

			controller.ivTruck.setOnMousePressed(e -> {
				e.setDragDetect(true);
			});

			controller.ivTruck.setOnDragDetected(e -> {
				controller.ivTruck.startFullDrag();
			});

			// **** Signs ****
			controller.ivConstruction.setOnMousePressed(e -> {
				e.setDragDetect(true);
			});

			controller.ivConstruction.setOnDragDetected(e -> {
				controller.ivConstruction.startFullDrag();
			});

			controller.ivSpeedLimit50.setOnMousePressed(e -> {
				e.setDragDetect(true);
			});

			controller.ivSpeedLimit50.setOnDragDetected(e -> {
				controller.ivSpeedLimit50.startFullDrag();
			});

			controller.ivSpeedLimit70.setOnMousePressed(e -> {
				e.setDragDetect(true);
			});

			controller.ivSpeedLimit70.setOnDragDetected(e -> {
				controller.ivSpeedLimit70.startFullDrag();
			});

			controller.ivSpeedLimit100.setOnMousePressed(e -> {
				e.setDragDetect(true);
			});

			controller.ivSpeedLimit100.setOnDragDetected(e -> {
				controller.ivSpeedLimit100.startFullDrag();
			});

			// Event Handle for toggling between fullscreen and window mode
			controller.button05.setOnAction(e -> {
				if (primaryStage.isFullScreen()) {
					controller.streetSize.setDisable(false);
					primaryStage.setFullScreen(false);
				} else {
					controller.streetSize.setDisable(true);
					primaryStage.setFullScreen(true);
				}
			});

			// Event handler for loading a saved simulation. 
			controller.button03.setOnAction(e -> {
				controller.loadSimulation();
				repaintContents(backendGrid, controller);
			});

			primaryStage.setScene(scene);
			primaryStage.setResizable(false);
			primaryStage.show();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Entry point for gui.
	 *
	 * @param args program arguments
	 */
	public static void main(String[] args) {
		launch(args);
	}

	/**
	 * This method deletes the contents of the street and redraws them.
	 * It should be called as soon as the content of the road changes.
	 *
	 * @param startingGrid The startingGrid holding the data
	 * @param controller   The GUI-Controller that contains the lanes from the
	 *                     frontend.
	 */
	public void repaintContents(StartingGrid startingGrid, GUIController controller) {

		final Set<StreetSign> streetSigns = startingGrid.getStreet().getLanes().stream().flatMap(lane -> Stream.of(lane.getCells())).map(de.superdudes.traffit.dto.Cell::getStreetSign).filter(Objects::nonNull).distinct().collect(Collectors.toSet());
		final Set<ConstructionSite> constructionSites = startingGrid.getStreet().getLanes().stream().flatMap(lane -> Stream.of(lane.getCells())).map(de.superdudes.traffit.dto.Cell::getBlockingConstructionSite).filter(Objects::nonNull).distinct().collect(Collectors.toSet());

		final List<Cell> cellsLane1 = controller.getLane1().getChildren().stream().filter(Cell.class::isInstance).map(Cell.class::cast).collect(Collectors.toList());
		final List<Cell> cellsLane2 = controller.getLane2().getChildren().stream().filter(Cell.class::isInstance).map(Cell.class::cast).collect(Collectors.toList());
		final List<Cell> signLane = controller.getSignlane().getChildren().stream().filter(Cell.class::isInstance).map(Cell.class::cast).collect(Collectors.toList());

		cellsLane1.get(0).cleanUpLane();
		cellsLane2.get(0).cleanUpLane();

		for (Vehicle v : startingGrid.getVehicles()) {
			final List<Cell> cellsOfLane = v.getLane().getIndex() == 0 ? cellsLane1 : cellsLane2;

			// Workaround if gui cell count not equal to db cell count
			// fixme count of gui cells unequals db cell count. On ubuntu (on windows?):
			// Stage resizing listener not triggered or works wrong
			if (v.getFrontCell().getIndex() >= cellsOfLane.size() - 1) {
				v.setToStartOfLane();
			} else {
				cellsOfLane.get(v.getTailCell().getIndex()).drawVehicle(v);
			}
		}

		for (StreetSign streetSign : streetSigns) {
			signLane.get(streetSign.getTailCell().getIndex()).drawStreetSign(streetSign);
		}
		for (ConstructionSite constructionSite : constructionSites) {
			final List<Cell> cellsOfLane = constructionSite.getLane().getIndex() == 0 ? cellsLane1 : cellsLane2;

			cellsOfLane.get(constructionSite.getTailCell().getIndex()).drawConstructionSite(constructionSite);
		}
	}
}
