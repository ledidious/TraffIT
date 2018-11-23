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
    public void start( Stage primaryStage ) {
        try {
            AnchorPane root = ( AnchorPane ) FXMLLoader.load( getClass().getResource( "GUI.fxml" ) );
            Scene scene = new Scene( root );

            //fixed window minimum size
            root.setPrefSize( 1280, 800 );
            primaryStage.setMinHeight( 839 );
            primaryStage.setMinWidth( 1296 );

            primaryStage.setFullScreen( true );

            //Listener to resize the window
            scene.widthProperty().addListener( new ChangeListener<Number>() {

                @Override
                public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneWidth, Number newSceneWidth ) {
                    System.out.println( "Width: " + newSceneWidth );
                    root.setPrefWidth( ( double ) newSceneWidth );
                }
            } );

            scene.heightProperty().addListener( new ChangeListener<Number>() {
                @Override
                public void changed( ObservableValue<? extends Number> observableValue, Number oldSceneHeight, Number newSceneHeight ) {
                    System.out.println( "Height: " + newSceneHeight );
                    root.setPrefHeight( ( double ) newSceneHeight );
                }
            } );

            scene.getStylesheets().add( getClass().getResource( "application.css" ).toExternalForm() );
            primaryStage.setScene( scene );
            primaryStage.show();
        }
        catch( Exception e ) {
            e.printStackTrace();
        }
    }

    public static void main( String[] args ) {
        launch( args );
    }
}
