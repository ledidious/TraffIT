package de.superdudes.traffit.application;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Cell extends Rectangle {

	private ImageView currentImage;
	private Boolean stopPainting = false;
	static int number = 1;

	public void count() {
		if (this.getId() == null) {
			this.setId(Integer.toString(number));
			number++;
		}
	}

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

		setOnMouseDragReleased(e -> {
			ImageView source = (ImageView) e.getGestureSource();
			String id = source.getId();

			Pane myParent = (Pane) this.getParent();

			Text text = new Text();
			text.setStyle("-fx-font-weight: bold");
			text.setStyle("-fx-font: 24 arial;");

			switch (id) {
			case "ivCar":

				if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {

					Cell[] myNeighboursCar = new Cell[20];

					for (int i = 0; i < myNeighboursCar.length; i++) {
						myNeighboursCar[i] = (Cell) myParent.getChildren()
								.get(Integer.parseInt(this.getId()) + (i + 1));
					}

					for (Cell c : myNeighboursCar) {
						c.setFill(javafx.scene.paint.Color.BLUE);
						c.stopPainting = true;
					}

					this.setFill(javafx.scene.paint.Color.BLUE);
					this.stopPainting = true;

				}

				else {
					System.out.println("no lane");
					this.setFill(javafx.scene.paint.Color.TRANSPARENT);
				}

				break;
			case "ivMotorcycle":

				if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {

					Cell[] myNeighboursMotor = new Cell[5];

					for (int i = 0; i < myNeighboursMotor.length; i++) {
						myNeighboursMotor[i] = (Cell) myParent.getChildren()
								.get(Integer.parseInt(this.getId()) + (i + 1));
					}

					for (Cell c : myNeighboursMotor) {
						c.setFill(javafx.scene.paint.Color.GREEN);
						c.stopPainting = true;
					}

					this.setFill(javafx.scene.paint.Color.GREEN);
					this.stopPainting = true;
				}

				else {
					System.out.println("no lane");
					this.setFill(javafx.scene.paint.Color.TRANSPARENT);
				}

				break;
			case "ivTruck":

				if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {

					Cell[] myNeighboursTruck = new Cell[40];

					for (int i = 0; i < myNeighboursTruck.length; i++) {
						myNeighboursTruck[i] = (Cell) myParent.getChildren()
								.get(Integer.parseInt(this.getId()) + (i + 1));
					}

					for (Cell c : myNeighboursTruck) {
						c.setFill(javafx.scene.paint.Color.RED);
						c.stopPainting = true;
					}

					this.setFill(javafx.scene.paint.Color.RED);
					this.stopPainting = true;
				}

				else {
					System.out.println("no lane");
					this.setFill(javafx.scene.paint.Color.TRANSPARENT);
				}

				break;

			case "ivSpeedLimit50":

				if (myParent.getId().equals("signlane")) {

					Cell[] myNeighbours50 = new Cell[500];

					text.setText("50");

					for (int i = 0; i < myNeighbours50.length; i++) {
						myNeighbours50[i] = (Cell) myParent.getChildren().get(Integer.parseInt(this.getId()) + (i + 1));
					}

					for (Cell c : myNeighbours50) {
						c.setFill(javafx.scene.paint.Color.SALMON);
						c.stopPainting = true;
					}

					double xValue50 = myNeighbours50[(myNeighbours50.length / 2)].getX();
					double yValue50 = myNeighbours50[(myNeighbours50.length / 2)].getY();

					myParent.getChildren().add(text);

					text.setLayoutX(xValue50 - 12);
					text.setLayoutY(yValue50 + (height / 2));

					this.setFill(javafx.scene.paint.Color.SALMON);
					this.stopPainting = true;

				} else {
					this.setFill(javafx.scene.paint.Color.GREY);
				}
				break;

			case "ivSpeedLimit100":

				if (myParent.getId().equals("signlane")) {

					Cell[] myNeighbours100 = new Cell[500];

					text.setText("100");
					text.setStyle("-fx-font: 20 arial;");

					for (int i = 0; i < myNeighbours100.length; i++) {
						myNeighbours100[i] = (Cell) myParent.getChildren()
								.get(Integer.parseInt(this.getId()) + (i + 1));
					}

					for (Cell c : myNeighbours100) {
						c.setFill(javafx.scene.paint.Color.SALMON);
						c.stopPainting = true;
					}

					double xValue100 = myNeighbours100[(myNeighbours100.length / 2)].getX();
					double yValue100 = myNeighbours100[(myNeighbours100.length / 2)].getY();

					myParent.getChildren().add(text);

					text.setLayoutX(xValue100 - 17);
					text.setLayoutY(yValue100 + (height / 2));

					this.setFill(javafx.scene.paint.Color.SALMON);
					this.stopPainting = true;
				}

				else {
					this.setFill(javafx.scene.paint.Color.GREY);
				}

				break;

			case "ivSpeedLimit70":

				if (myParent.getId().equals("signlane")) {

					Cell[] myNeighbours70 = new Cell[50];

					text.setText("70");

					for (int i = 0; i < myNeighbours70.length; i++) {
						myNeighbours70[i] = (Cell) myParent.getChildren().get(Integer.parseInt(this.getId()) + (i + 1));
					}

					for (Cell c : myNeighbours70) {
						c.setFill(javafx.scene.paint.Color.SALMON);
						c.stopPainting = true;
					}

					double xValue70 = myNeighbours70[(myNeighbours70.length / 2)].getX();
					double yValue70 = myNeighbours70[(myNeighbours70.length / 2)].getY();

					myParent.getChildren().add(text);

					text.setLayoutX(xValue70 - 12);
					text.setLayoutY(yValue70 + (height / 2));

					this.setFill(javafx.scene.paint.Color.SALMON);
					this.stopPainting = true;

				}

				else {
					this.setFill(javafx.scene.paint.Color.GREY);
				}
				break;

			case "ivConstruction":

				if (myParent.getId().equals("lane1") | myParent.getId().equals("lane2")) {
					int constructionWidth = showModalDialog();
					int counter = 0;

				if (constructionWidth > 0) {
					Cell[] allConstructionCells = new Cell[constructionWidth];

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

				else {
					this.setFill(javafx.scene.paint.Color.DARKSEAGREEN);
					}
				}

				break;
			default:
				System.err.println("Object not recognized.");
			}

			System.out.println(this.getId());

//			System.out.println(this.getId());
//			System.out.println("Das Parent Node lautet: ");
//			System.out.println(this.getParent());
//
//			System.out.println("Hi Mario! Ich sende Text!");
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

				// spinner01.getValueFactory().setValue(spinner01.getValue() - (number -
				// Integer.parseInt(this.getId())));
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

	private Spinner<Integer> initSpinner() {
		Spinner<Integer> theSpinner = new Spinner<Integer>();

		theSpinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(50, Cell.number));
		theSpinner.setEditable(true);

		// Now losing focus will update vale of spinner
		TextFormatter<Integer> formatter = new TextFormatter<Integer>(theSpinner.getValueFactory().getConverter(),
				theSpinner.getValueFactory().getValue());
		theSpinner.getEditor().setTextFormatter(formatter);
		theSpinner.getValueFactory().valueProperty().bindBidirectional(formatter.valueProperty());

		IntegerStringConverter.createFor(theSpinner);

		return theSpinner;
	}
}