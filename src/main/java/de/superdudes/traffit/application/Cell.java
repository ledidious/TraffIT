package de.superdudes.traffit.application;

import java.io.File;

import javafx.event.EventHandler;
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

	public Cell(double x, double y, double width, double height, Paint fill) {
		super(x, y, width, height);
		setFill(fill);
		setOnMouseDragOver(e -> this.setFill(javafx.scene.paint.Color.YELLOW));
		setOnMouseDragExited(e -> {
			if (!stopPainting)
				this.setFill(fill);
		});

		setOnMouseDragReleased(e -> {
			this.setFill(javafx.scene.paint.Color.AQUA);
			System.out.println("Hi Mario! Ich sende Text!");
			this.stopPainting = true;
		});
	}

	public Cell(double x, double y, double width, double height) {
		super(x, y, width, height);

	}

}