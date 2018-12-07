package de.superdudes.traffit.application;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

public class Cell extends Rectangle {

    public Cell( double x, double y, double width, double height, Paint fill ) {
        super( x, y, width, height );
        setFill( fill );
        setOnMouseDragOver(e -> this.setFill(javafx.scene.paint.Color.YELLOW));
        setOnMouseDragExited(e -> this.setFill(fill));
    }

    public Cell( double x, double y, double width, double height ) {
        super( x, y, width, height );

    }
    
    
}