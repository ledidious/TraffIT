package de.superdudes.traffit.application;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;

public class GUIController {

    //Set the path to the icons of the vehicles
    private String pathToCar        = new File( "src/main/resources/car.png" ).getAbsolutePath();
    private String pathToMotorcycle = new File( "src/main/resources/motorcycle.png" ).getAbsolutePath();
    private String pathToTruck      = new File( "src/main/resources/truck.png" ).getAbsolutePath();

    //Set the path to the icons of the signs
    private String pathToSpeedLimit50  = new File( "src/main/resources/limit30.png" ).getAbsolutePath();
    private String pathToSpeedLimit70  = new File( "src/main/resources/limit30.png" ).getAbsolutePath();
    private String pathToSpeedLimit100 = new File( "src/main/resources/limit30.png" ).getAbsolutePath();
    private String pathToContstruction = new File( "src/main/resources/construction.png" ).getAbsolutePath();

    //Vehicle
    private Image iconCar        = new Image( "file:" + pathToCar );
    private Image iconMotorcycle = new Image( "file:" + pathToMotorcycle );
    private Image iconTruck      = new Image( "file:" + pathToTruck );

    //Speed Limit
    private Image iconSpeedLimit50  = new Image( "file:" + pathToSpeedLimit50 );
    private Image iconSpeedLimit70  = new Image( "file:" + pathToSpeedLimit70 );
    private Image iconSpeedLimit100 = new Image( "file:" + pathToSpeedLimit100 );

    //Signs
    private Image iconConstruction = new Image( "file:" + pathToContstruction );

    //ImageViews
    @FXML
    private ImageView ivCar;

    @FXML
    private ImageView ivMotorcycle;

    @FXML
    private ImageView ivTruck;

    @FXML
    private ImageView ivSpeedLimit50;

    @FXML
    private ImageView ivSpeedLimit70;

    @FXML
    private ImageView ivSpeedLimit100;

    @FXML
    private ImageView ivConstruction;

    //Buttons
    @FXML
    private Button button01;

    @FXML
    private Button button02;

    //Labels
    @FXML
    private Label label01;

    public GUIController() {
        System.out.println( "Copyright: Superdudes 2018" );
    }

    @FXML
    public void initialize() {
        ivCar.setImage( iconCar );
        ivMotorcycle.setImage( iconMotorcycle );
        ivTruck.setImage( iconTruck );
    }

    String path = new File( "src/main/resources/car.png" ).getAbsolutePath();

    @FXML
    private void handleDeletePerson() {
        System.out.println( "Hier k�nnte Ihre Werbung stehen!" );
    }

    @FXML
    private void changeTextFromLabel() {
        label01.setText( "Ich habe Lust auf Bier und Titten! ;)" );
    }
}