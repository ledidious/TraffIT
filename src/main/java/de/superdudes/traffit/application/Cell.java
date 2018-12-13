package de.superdudes.traffit.application;

import java.io.File;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

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
		setOnMouseDragOver(e -> this.setFill(javafx.scene.paint.Color.YELLOW));
		setOnMouseDragExited(e -> {
			if (!stopPainting)
				this.setFill(fill);
		});

		setOnMouseDragReleased(e -> {
			ImageView source = (ImageView) e.getGestureSource();
			String id = source.getId();

			Pane myParent = (Pane) this.getParent();

			switch (id) {
			case "ivCar":
				Cell[] myNeighboursCar = new Cell[20];

				for (int i = 0; i < myNeighboursCar.length; i++) {
					myNeighboursCar[i] = (Cell) myParent.getChildren().get(Integer.parseInt(this.getId()) + (i + 1));
				}

				for (Cell c : myNeighboursCar) {
					c.setFill(javafx.scene.paint.Color.BLUE);
				}
				
				this.setFill(javafx.scene.paint.Color.BLUE);

				break;
			case "ivMotorcycle":
				Cell[] myNeighboursMotor = new Cell[5];

				for (int i = 0; i < myNeighboursMotor.length; i++) {
					myNeighboursMotor[i] = (Cell) myParent.getChildren().get(Integer.parseInt(this.getId()) + (i + 1));
				}

				for (Cell c : myNeighboursMotor) {
					c.setFill(javafx.scene.paint.Color.GREEN);
				}
				
				this.setFill(javafx.scene.paint.Color.GREEN);
				
				break;
			case "ivTruck":
				Cell[] myNeighboursMotorTruck = new Cell[40];

				for (int i = 0; i < myNeighboursMotorTruck.length; i++) {
					myNeighboursMotorTruck[i] = (Cell) myParent.getChildren().get(Integer.parseInt(this.getId()) + (i + 1));
				}

				for (Cell c : myNeighboursMotorTruck) {
					c.setFill(javafx.scene.paint.Color.RED);
				}
				
				this.setFill(javafx.scene.paint.Color.RED);
				
				break;
			}
			;

			
			/*
			 * for (Cell c : myNeighbours) { c = (Cell)
			 * myParent.getChildren().get(Integer.parseInt(this.getId()) + 1); }
			 */

			// myNeighbour.setFill(javafx.scene.paint.Color.AQUA);
			System.out.println(this.getId());
			System.out.println("Das Parent Node lautet: ");
			System.out.println(this.getParent());

			System.out.println("Hi Mario! Ich sende Text!");
			this.stopPainting = true;
		});

		count();
	}

	public Cell(double x, double y, double width, double height) {
		super(x, y, width, height);

	}

}