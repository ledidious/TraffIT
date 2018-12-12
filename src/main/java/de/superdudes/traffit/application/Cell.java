package de.superdudes.traffit.application;

import java.io.File;

import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {


	private ImageView currentImage;

	public Cell(double x, double y, double width, double height, Paint fill) {
		super(x, y, width, height);
		setFill(fill);
		setOnMouseDragOver(e -> this.setFill(javafx.scene.paint.Color.YELLOW));
		setOnMouseDragExited(e -> this.setFill(fill));

		setOnMouseDragReleased(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent event) {
				Image iconCar = new Image("file:" + new
				File("src/main/resources/car.png").getAbsolutePath());
				
				currentImage = new ImageView();
				currentImage.setImage(iconCar);
				currentImage.setId(this.getClass().getSimpleName() + System.currentTimeMillis());
				
				System.out.println("Event on Target: mouse drag released");
			}
		});

	}

	public Cell(double x, double y, double width, double height) {
		super(x, y, width, height);

	}

}