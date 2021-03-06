package de.superdudes.traffit.application;

import de.superdudes.traffit.SimulationManager;
import de.superdudes.traffit.dto.*;
import de.superdudes.traffit.exception.ObjectMisplacedException;
import de.superdudes.traffit.exception.SimulationRunningException;
import de.superdudes.traffit.util.GUIUtils;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import de.superdudes.traffit.application.Cell;

public class Cell extends Rectangle {
	private Boolean stopPainting = false; // Marks the cell as used so that the color doesn't change when the cursor
	// moves over it.
	static int number = 0; // Counter for the number of generated cells (per lane)

	/**
	 * counting the cellid
	 */
	public void count() {
		if (this.getId() == null) {
			this.setId(Integer.toString(number));
			number++;
		}
	}

	/**
	 * constructor for the cell
	 * create objects of vehicles and signs
	 *
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 * @param fill
	 */
	public Cell(double x, double y, double width, double height, Paint fill) {
		super(x, y, width, height);
		setFill(fill);
		setOnMouseDragOver(e -> {
			if (!stopPainting)
				this.setFill(javafx.scene.paint.Color.YELLOW);
		});

		setOnMouseDragExited(e -> {
			if (!stopPainting)
				this.setFill(fill);
		});

		// Event handler for adding objects to lanes
		setOnMouseDragReleased(e -> {
			ImageView source = (ImageView) e.getGestureSource();
			String id = source.getId();

			Pane myParent = (Pane) this.getParent();

			Text text = new Text();
			text.setStyle("-fx-font-weight: bold");
			text.setStyle("-fx-font: 24 arial;");

			if (SimulationManager.isRunning()) {
				GUIUtils.showErrorDialog((Stage) getScene().getWindow(), new SimulationRunningException());
				return;
			}

			switch (id) {
				case "ivCar":
					if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {
						int backendLaneId;

						if (myParent.getId().equals("lane1")) {
							backendLaneId = 0;
						} else {
							backendLaneId = 1;
						}

						StartingGrid myGrid = SimulationManager.getStartingGrid();
						Lane backendLane = myGrid.getStreet().getLanes().get(backendLaneId);
						de.superdudes.traffit.dto.Cell[] laneCells = backendLane.getCells();

						try {
							Vehicle vehicle = new Vehicle(Vehicle.Type.CAR, laneCells[Integer.parseInt(this.getId())]);
							drawVehicle(vehicle);
						} catch (ObjectMisplacedException e2) {
							GUIUtils.showErrorDialog((Stage) getScene().getWindow(), e2);
						}
					} else {
						System.out.println("no lane");
						this.setFill(javafx.scene.paint.Color.TRANSPARENT);
					}
					break;

				case "ivMotorcycle":
					if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {
						int backendLaneId;

						if (myParent.getId().equals("lane1")) {
							backendLaneId = 0;
						} else {
							backendLaneId = 1;
						}

						StartingGrid myGrid = SimulationManager.getStartingGrid();
						Lane backendLane = myGrid.getStreet().getLanes().get(backendLaneId);
						de.superdudes.traffit.dto.Cell[] laneCells = backendLane.getCells();

						try {
							Vehicle vehicle = new Vehicle(Vehicle.Type.MOTORCYCLE,
									laneCells[Integer.parseInt(this.getId())]);
							drawVehicle(vehicle);
						} catch (ObjectMisplacedException e2) {
							GUIUtils.showErrorDialog((Stage) getScene().getWindow(), e2);
						}
					} else {
						System.out.println("no lane");
						this.setFill(javafx.scene.paint.Color.TRANSPARENT);
					}
					break;

				case "ivTruck":
					if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {
						int backendLaneId;

						if (myParent.getId().equals("lane1")) {
							backendLaneId = 0;
						} else {
							backendLaneId = 1;
						}

						StartingGrid myGrid = SimulationManager.getStartingGrid();
						Lane backendLane = myGrid.getStreet().getLanes().get(backendLaneId);
						de.superdudes.traffit.dto.Cell[] laneCells = backendLane.getCells();

						try {
							Vehicle vehicle = new Vehicle(Vehicle.Type.TRUCK, laneCells[Integer.parseInt(this.getId())]);
							drawVehicle(vehicle);
						} catch (ObjectMisplacedException e2) {
							GUIUtils.showErrorDialog((Stage) getScene().getWindow(), e2);
						}
					} else {
						System.out.println("no lane");
						this.setFill(javafx.scene.paint.Color.TRANSPARENT);
					}
					break;

				case "ivSpeedLimit50":
					if (myParent.getId().equals("signlane")) {
						int ivSpeedLimit50Width = showModalDialog();

						if (ivSpeedLimit50Width > 0) {
							StartingGrid myGrid = SimulationManager.getStartingGrid();

							Lane backendLane0 = myGrid.getStreet().getLanes().get(0);
							Lane backendLane1 = myGrid.getStreet().getLanes().get(1);

							de.superdudes.traffit.dto.Cell[] laneCells0 = backendLane0.getCells();
							de.superdudes.traffit.dto.Cell[] laneCells1 = backendLane1.getCells();

							try {
								StreetSign signOnLane01 = new StreetSign(50, laneCells0[Integer.parseInt(this.getId())],
										ivSpeedLimit50Width);
								StreetSign signOnLane02 = new StreetSign(50, laneCells1[Integer.parseInt(this.getId())],
										ivSpeedLimit50Width);

								drawStreetSign(signOnLane01);
								drawStreetSign(signOnLane02);
							} catch (ObjectMisplacedException e2) {
								GUIUtils.showErrorDialog((Stage) getScene().getWindow(), e2);
							}
						}
					} else {
						this.setFill(javafx.scene.paint.Color.GREY);
					}
					break;

				case "ivSpeedLimit70":
					if (myParent.getId().equals("signlane")) {
						int ivSpeedLimit70Width = showModalDialog();

						if (ivSpeedLimit70Width > 0) {
							StartingGrid myGrid = SimulationManager.getStartingGrid();

							Lane backendLane0 = myGrid.getStreet().getLanes().get(0);
							Lane backendLane1 = myGrid.getStreet().getLanes().get(1);

							de.superdudes.traffit.dto.Cell[] laneCells0 = backendLane0.getCells();
							de.superdudes.traffit.dto.Cell[] laneCells1 = backendLane1.getCells();

							try {
								StreetSign signOnLane01 = new StreetSign(70, laneCells0[Integer.parseInt(this.getId())],
										ivSpeedLimit70Width);
								StreetSign signOnLane02 = new StreetSign(70, laneCells1[Integer.parseInt(this.getId())],
										ivSpeedLimit70Width);

								drawStreetSign(signOnLane01);
								drawStreetSign(signOnLane02);
							} catch (ObjectMisplacedException e2) {
								GUIUtils.showErrorDialog((Stage) getScene().getWindow(), e2);
							}
						}
					} else {
						this.setFill(javafx.scene.paint.Color.GREY);
					}
					break;

				case "ivSpeedLimit100":
					if (myParent.getId().equals("signlane")) {
						int ivSpeedLimit100Width = showModalDialog();

						if (ivSpeedLimit100Width > 0) {
							StartingGrid myGrid = SimulationManager.getStartingGrid();

							Lane backendLane0 = myGrid.getStreet().getLanes().get(0);
							Lane backendLane1 = myGrid.getStreet().getLanes().get(1);

							de.superdudes.traffit.dto.Cell[] laneCells0 = backendLane0.getCells();
							de.superdudes.traffit.dto.Cell[] laneCells1 = backendLane1.getCells();

							try {
								StreetSign signOnLane01 = new StreetSign(100, laneCells0[Integer.parseInt(this.getId())],
										ivSpeedLimit100Width);
								StreetSign signOnLane02 = new StreetSign(100, laneCells1[Integer.parseInt(this.getId())],
										ivSpeedLimit100Width);

								drawStreetSign(signOnLane01);
								drawStreetSign(signOnLane02);
							} catch (ObjectMisplacedException e2) {
								GUIUtils.showErrorDialog((Stage) getScene().getWindow(), e2);
							}
						}
					} else {
						this.setFill(javafx.scene.paint.Color.GREY);
					}
					break;

				case "ivConstruction":

					if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {
						int constructionWidth = showModalDialog();

						int backendLaneId;

						if (myParent.getId().equals("lane1")) {
							backendLaneId = 0;
						} else {
							backendLaneId = 1;
						}

						StartingGrid myGrid = SimulationManager.getStartingGrid();

						Lane backendLane = myGrid.getStreet().getLanes().get(backendLaneId);

						de.superdudes.traffit.dto.Cell[] laneCells = backendLane.getCells();

						if (constructionWidth > 0) {
							try {
								final ConstructionSite constructionSite = new ConstructionSite(constructionWidth, laneCells[Integer.parseInt(this.getId())]);
								drawConstructionSite(constructionSite);
							} catch (ObjectMisplacedException e2) {
								GUIUtils.showErrorDialog((Stage) getScene().getWindow(), e2);
							}
						}
					} else {
						this.setFill(javafx.scene.paint.Color.DARKSEAGREEN);
					}

					break;
				default:
					System.err.println("Object not recognized.");
			}

			System.out.println(this.getId());
			this.stopPainting = true;
		});

		count();
	}

	private int showModalDialog() {
		Stage dialog = new Stage();
		Stage myStage = (Stage) this.getScene().getWindow();

		dialog.initOwner(myStage);
		dialog.initModality(Modality.APPLICATION_MODAL);
		dialog.initStyle(StageStyle.UTILITY);

		dialog.setAlwaysOnTop(true);

		dialog.setHeight(250);
		dialog.setWidth(400);

		AnchorPane dialogPane = new AnchorPane();

		Label label01 = new Label();
		label01.setText("Please set the Width(Cells) of the Construction:");
		label01.setLayoutX(10);
		label01.setLayoutY(1);

		Spinner<Integer> spinner01 = initSpinner();
		spinner01.setLayoutX(100);
		spinner01.setLayoutY(50);

		Button button01 = new Button();
		button01.setText("OK");
		button01.setLayoutX(170);
		button01.setLayoutY(100);

		Button button02 = new Button();
		button02.setText("Cancel");
		button02.setLayoutX(220);
		button02.setLayoutY(100);

		dialogPane.getChildren().add(label01);
		dialogPane.getChildren().add(spinner01);
		dialogPane.getChildren().add(button01);
		dialogPane.getChildren().add(button02);

		Scene dialogScene = new Scene(dialogPane);

		button01.setOnAction(e -> {
			if (Integer.parseInt(this.getId()) + spinner01.getValue() > number) {
				label01.setText("Construction site doesn't fit on Street.");
				label01.setTextFill(javafx.scene.paint.Color.RED);
			} else {
				dialog.close();
			}
		});

		button02.setOnAction(e -> {
			spinner01.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 1));
			spinner01.getValueFactory().setValue(0);
			dialog.close();
		});

		dialog.setScene(dialogScene);
		dialog.showAndWait();

		return spinner01.getValue();
	}

	/**
	 * Prepare the spinner for further use.
	 *
	 * @return the initiated spinner
	 */
	private Spinner<Integer> initSpinner() {
		Spinner<Integer> theSpinner = new Spinner<Integer>();

		theSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, Cell.number));
		theSpinner.setEditable(true);

		// Now losing focus will update value of the spinner
		TextFormatter<Integer> formatter = new TextFormatter<Integer>(theSpinner.getValueFactory().getConverter(),
				theSpinner.getValueFactory().getValue());
		theSpinner.getEditor().setTextFormatter(formatter);
		theSpinner.getValueFactory().valueProperty().bindBidirectional(formatter.valueProperty());

		IntegerStringConverter.createFor(theSpinner);

		return theSpinner;
	}

	/**
	 * Draws the given vehicle. The current cell is to be regarded as the starting
	 * point.
	 *
	 * @param aVehicle
	 */
	public void drawVehicle(Vehicle aVehicle) {
		Cell[] myNeighbours = new Cell[aVehicle.getLength()];

		for (int i = 0; i < myNeighbours.length; i++) {
			myNeighbours[i] = (Cell) ((Pane) this.getParent()).getChildren()
					.get(Integer.parseInt(this.getId()) + (i + 1));
		}

		switch (aVehicle.getType()) {
			case CAR:
				for (Cell c : myNeighbours) {
					c.setFill(javafx.scene.paint.Color.BLUE);
					c.stopPainting = true;
				}

				this.setFill(javafx.scene.paint.Color.BLUE);
				this.stopPainting = true;

				break;
			case TRUCK:
				for (Cell c : myNeighbours) {
					c.setFill(javafx.scene.paint.Color.RED);
					c.stopPainting = true;
				}

				this.setFill(javafx.scene.paint.Color.RED);
				this.stopPainting = true;
				break;
			case MOTORCYCLE:
				for (Cell c : myNeighbours) {
					c.setFill(javafx.scene.paint.Color.GREEN);
					c.stopPainting = true;
				}

				this.setFill(javafx.scene.paint.Color.GREEN);
				this.stopPainting = true;
				break;
			default:
				break;
		}
	}

	/**
	 * Draws the given sign. The current cell is to be regarded as the starting
	 * point.
	 *
	 * @param sign
	 */
	public void drawStreetSign(StreetSign sign) {
		Cell[] myNeighbours = new Cell[sign.getLength()];

		for (int i = 0; i < myNeighbours.length; i++) {
			myNeighbours[i] = (Cell) ((Pane) this.getParent()).getChildren()
					.get(Integer.parseInt(this.getId()) + (i + 1));
		}

		for (Cell c : myNeighbours) {
			c.setFill(javafx.scene.paint.Color.SALMON);
			c.stopPainting = true;
		}

		double xValue = myNeighbours[(myNeighbours.length / 2)].getX();
		double yValue = myNeighbours[(myNeighbours.length / 2)].getY();

		Text text = new Text();
		text.setStyle("-fx-font-weight: bold");
		text.setStyle("-fx-font: 24 arial;");
		text.setLayoutX(xValue - 12);
		text.setLayoutY(yValue + (this.getHeight() / 2));

		switch (sign.getSpeedLimit()) {
			case 50:
				text.setText("50");
				break;

			case 70:
				text.setText("70");
				break;

			case 100:
				text.setText("100");
				text.setStyle("-fx-font: 20 arial;");
				text.setLayoutX(xValue - 17);
				break;

			default:
				break;
		}

		((Pane) this.getParent()).getChildren().add(text);

		this.setFill(javafx.scene.paint.Color.SALMON);
		this.stopPainting = true;
	}

	public void drawConstructionSite(ConstructionSite constructionSite) {

		final Pane myParent = (Pane) this.getParent();
		int counter = 0;

		Cell[] allConstructionCells = new Cell[constructionSite.getLength()];

		for (int i = 0; i < allConstructionCells.length; i++) {
			allConstructionCells[i] = (Cell) myParent.getChildren()
					.get(Integer.parseInt(this.getId()) + (i));
		}

		for (int i = 0; i < allConstructionCells.length; i++) {
			if (counter <= 5) {
				allConstructionCells[i].setFill(javafx.scene.paint.Color.BLACK);
				allConstructionCells[i].stopPainting = true;
			} else {
				allConstructionCells[i].setFill(javafx.scene.paint.Color.YELLOW);
				allConstructionCells[i].stopPainting = true;
			}
			counter++;
			if (counter == 10) {
				counter = 0;
			}
		}
	}

	/**
	 * Removes all objects from the lane. Exception: Construction sites.
	 * Attention: They still exist in the backend.
	 */
	public void cleanUpLane() {
		Cell[] allCellsFromLane = new Cell[number];

		for (int i = 0; i < allCellsFromLane.length; i++) {
			allCellsFromLane[i] = (Cell) ((Pane) this.getParent()).getChildren().get(i);
		}

		for (Cell c : allCellsFromLane) {
			if (c.getFill() != javafx.scene.paint.Color.YELLOW & c.getFill() != javafx.scene.paint.Color.BLACK) {
				c.setFill(javafx.scene.paint.Color.GRAY);
				c.stopPainting = false;
			}
		}
	}
}