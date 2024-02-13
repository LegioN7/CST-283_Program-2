package org.example.program2;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.scene.control.Alert;

// CST-283
// Aaron Pelto
// Winter 2024

// This application is for CST-283 - Program 2
// Whether it is a military operation or simple geocaching, many activities involve searching for a target at a precise geographic position on Earth from a given starting position.
// Write a Java program that utilizes the basic required formulas to search for a target.

// The class should also include the following elements:
// ● Include both a no-argument constructor and at least one parameterized constructor (even though you might not need them for this assignment).
// ● Be sure also to include all "set" and "get" methods associated with each of the primary data members of the class.
// ● Include a toString() method as part of the class that will return a String object that includes the current values stored in a class object for the primary data elements.
// ● Comment your Target class using javadoc


// Note: that these define line-of-sight targeting and are not related to road paths, etc.
// Note: the reference to directional bearings at the right. Traditional bearings are returned in degrees 0.0...360.0 but can also be defined using ordinal descriptions (N, NNE, NE, …)
// Note: that the general bearing calculation returns an angle measurement, but the ordinal bearing should return one of the 16 compass points as a String.
// You will need to define a translation from degrees to a given compass point based on ranges of degrees (and for grading there is some discretion in these ranges).
// For the current date/time, consider use of the Java GregorianCalendar or LocalDateTime classes.
// Next, build a simple JavaFX front-end user interface for your solution. Your front-end interface will communicate with one object of the back-end Target object.
// The basic operation of your interface will include:
// ● Text field designating name of target
// ● Text fields to enter latitude and longitude of target
// ● Text field for speed you will be moving toward target
// ● A "Go" or "Calculate" button to launch navigation calculation actions
// ● A "Clear" button to clear out all text fields and a "Quit" button to terminate application
//Include data validation to first ensure that the numerical values are valid numbers (using exception-handling) and that the target identification is not blank.
// Furthermore, validate that the latitude is between (–90.0 and//90.0) and the longitude is –180.0 to 180.0.
// Note: that North American longitudes are negative (west of the Greenwich line in England).
// Also be sure the speed is not negative.
// Feel free to hard-code the latitude and longitude of your current position as a constant.
// Feel free to use coordinates of Delta College or any other point of your choice
// (Delta College: latitude: 43.559; longitude://–83.986).
// Note that Google Maps can provide you with very precise geographical coordinates of any place with one or two clicks.
// If the input data are valid, perform necessary method calls to generate an output report using a JavaFX Alert action (Please avoid use of JOptionPane dialogs).
// The report could be similar to what you see below:
// Target: Coffee Shop
// Distance: 2.3 km
// Bearing 68 degrees (ENE)
// Currently: 13 JUL 2021 14:52
// Time to target: 0.22 hours

public class TargetingApp extends Application {

    private TextField nameField;
    private TextField latitudeField;
    private TextField longitudeField;
    private TextField speedField;
    private Target target;


    // Start the application
    // Set the title of the application
    // Create the scene and set the stage
    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Targeting App");
        Scene scene = createScene();
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // Create the scene for the application
    // Create the grid pane and add the labels, text fields, and buttons
    // Set the action for the buttons
    // Return the scene
    private Scene createScene() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(30, 30, 30, 30));
        grid.setVgap(15);
        grid.setHgap(10);
        grid.setAlignment(Pos.CENTER);

        Label nameLabel = new Label("Target Name:");
        GridPane.setConstraints(nameLabel, 0, 0);
        nameField = new TextField();
        nameField.setPromptText("Enter target name");
        GridPane.setConstraints(nameField, 1, 0);

        Label latitudeLabel = new Label("Latitude:");
        GridPane.setConstraints(latitudeLabel, 0, 1);
        latitudeField = new TextField();
        latitudeField.setPromptText("Enter latitude");
        GridPane.setConstraints(latitudeField, 1, 1);

        Label longitudeLabel = new Label("Longitude:");
        GridPane.setConstraints(longitudeLabel, 0, 2);
        longitudeField = new TextField();
        longitudeField.setPromptText("Enter longitude");
        GridPane.setConstraints(longitudeField, 1, 2);

        Label speedLabel = new Label("Speed:");
        GridPane.setConstraints(speedLabel, 0, 3);
        speedField = new TextField();
        speedField.setPromptText("Enter speed");
        GridPane.setConstraints(speedField, 1, 3);

        Button goButton = new Button("Go");
        goButton.setPadding(new Insets(10, 5, 10, 5));
        goButton.setPrefWidth(50);
        GridPane.setConstraints(goButton, 0, 4, 1, 1, HPos.CENTER, VPos.CENTER);
        goButton.setOnAction(e -> calculateTarget());

        Button clearButton = new Button("Clear");
        clearButton.setPadding(new Insets(10, 5, 10, 5));
        clearButton.setPrefWidth(50);
        GridPane.setConstraints(clearButton, 1, 4, 1, 1, HPos.CENTER, VPos.CENTER);
        clearButton.setOnAction(e -> clearFields());

        Button quitButton = new Button("Quit");
        quitButton.setPadding(new Insets(10, 5, 10, 5));
        quitButton.setPrefWidth(50);
        GridPane.setConstraints(quitButton, 2, 4, 1, 1, HPos.CENTER, VPos.CENTER);
        quitButton.setOnAction(e -> Platform.exit());

        HBox buttonBox = new HBox(10);
        buttonBox.getChildren().addAll(goButton, clearButton, quitButton);
        buttonBox.setAlignment(Pos.CENTER);

        GridPane.setConstraints(buttonBox, 0, 4, 3, 1);
        GridPane.setHalignment(buttonBox, HPos.CENTER);
        GridPane.setValignment(buttonBox, VPos.CENTER);

        grid.getChildren().addAll(nameLabel, nameField, latitudeLabel, latitudeField, longitudeLabel, longitudeField,
                speedLabel, speedField, buttonBox);

        return new Scene(grid, 400, 300);
    }

    // I was looking up on this and I found that the exception handling is a good way to validate the input
    // I found this on https://www.geeksforgeeks.org/best-practices-to-handle-exceptions-in-java/
    // https://docs.oracle.com/cd/E23095_01/Platform.93/apidoc/atg/integrations/InvalidInputException.html
    // https://notearena.com/lesson/custom-exceptions-in-java/
    public static class InvalidInputException extends Exception {
        public InvalidInputException(String message) {
            super(message);
        }
    }

    // Calculate the information for the target
    // Get the information from the text fields and calculate the target
    private void calculateTarget() {
        try {
            String name = nameField.getText().trim();
            if (name.trim().isEmpty()) {
                throw new InvalidInputException("Target name cannot be blank.");
            }
            double latitude = Double.parseDouble(latitudeField.getText());
            double longitude = Double.parseDouble(longitudeField.getText());
            double speed = Double.parseDouble(speedField.getText());

            target = new Target();
            target.setTargetName(name);
            target.setTargetLatitude(latitude);
            target.setTargetLongitude(longitude);
            target.setMovingSpeed(speed);

            target.showTargetData();
        } catch (IllegalArgumentException | InvalidInputException e) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Input Validation Error");
            errorAlert.setContentText(e.getMessage());
            errorAlert.showAndWait();
        }
    }

    // Clear the fields
    private void clearFields() {
        nameField.clear();
        latitudeField.clear();
        longitudeField.clear();
        speedField.clear();
    }

    public static void main(String[] args) {
        launch(args);
    }
}