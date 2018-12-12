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
	public int cellId; 
	
	public void count() {
		cellId = number;
		number++; 
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
			
			switch (id) {
			case "ivCar":
				this.setFill(javafx.scene.paint.Color.BLUE);
				break;
			case "ivMotorcycle":
				this.setFill(javafx.scene.paint.Color.GREEN);
				break;
			case "ivTruck":
				this.setFill(javafx.scene.paint.Color.RED);
				break;
			};

			
			
			System.out.println("Hi Mario! Ich sende Text!");
			this.stopPainting = true;
			
			count();
		});
	}

	public Cell(double x, double y, double width, double height) {
		super(x, y, width, height);

	}

}