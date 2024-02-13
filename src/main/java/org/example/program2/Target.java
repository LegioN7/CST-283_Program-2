package org.example.program2;

import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Target {

    private String targetName;
    private double targetLatitude;
    private double targetLongitude;

    // Hard coding my latitude and longitude as Delta College
    private double myLatitude = 43.559;
    private double myLongitude = -83.986;
    private double movingSpeed;

    // Constructor
    // Blank no-arg constructor
    public Target() {
    }

    // Constructor with parameters
    public Target(String targetName,double targetLatitude, double targetLongitude, double myLatitude, double myLongitude, double movingSpeed) {
        this.targetName = targetName;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.myLatitude = myLatitude;
        this.myLongitude = myLongitude;
        this.movingSpeed = movingSpeed;
    }

    // Getters and setters
    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public double getTargetLatitude() {
        return targetLatitude;
    }

    public void setTargetLatitude(double targetLatitude) throws IllegalArgumentException {
        validateLatitude(targetLatitude);
        this.targetLatitude = targetLatitude;
    }

    // Validate Latitude
    // Latitude must be between -90.0 and 90.0 degrees
    private void validateLatitude(double latitude) throws IllegalArgumentException {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90.0 and 90.0 degrees.");
        }
    }

    public double getTargetLongitude() {
        return targetLongitude;
    }

    public void setTargetLongitude(double targetLongitude) throws IllegalArgumentException {
        validateLongitude(targetLongitude);
        this.targetLongitude = targetLongitude;
    }

    // Validate Longitude
    // Longitude must be between -180.0 and 180.0 degrees
    private void validateLongitude(double longitude) throws IllegalArgumentException {
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180.0 and 180.0 degrees.");
        }
    }

    // Is not used because I hard coded my latitude and longitude
    public double getMyLatitude() {
        return myLatitude;
    }

    // Is not used because I hard coded my latitude and longitude
    public void setMyLatitude(double myLatitude) {
        this.myLatitude = myLatitude;
    }

    // Is not used because I hard coded my latitude and longitude
    public double getMyLongitude() {
        return myLongitude;
    }

    // Is not used because I hard coded my latitude and longitude
    public void setMyLongitude(double myLongitude) {
        this.myLongitude = myLongitude;
    }

    public double getMovingSpeed() {
        return movingSpeed;
    }

    public void setMovingSpeed(double movingSpeed) throws IllegalArgumentException {
        validateMovingSpeed(movingSpeed);
        this.movingSpeed = movingSpeed;
    }

    // Validate Moving Speed
    // Moving Speed cannot be negative
    private void validateMovingSpeed(double speed) throws IllegalArgumentException {
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative.");
        }
    }


    // Provided by Mr.Klingler
    // Distance from target
    // Radius of the Earth is 6371.01 km
    // Convert coordinates to radians
    // Use the Haversine formula to calculate the distance
    public double distanceFrom() {
        final double RADIUS_EARTH = 6371.01;

        // Convert coordinates to radians
        double lat1 = Math.toRadians(targetLatitude);
        double lon1 = Math.toRadians(targetLongitude);
        double lat2 = Math.toRadians(myLatitude);
        double lon2 = Math.toRadians(myLongitude);

        return RADIUS_EARTH * Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
    }

    // Provided by Mr.Klingler
    // Bearing to target
    // Convert coordinates to radians
    // Use the formula to calculate the bearing
    // Return the bearing in degrees
    public double bearingToDegrees() {
        double latitude1 = Math.toRadians(myLatitude);
        double longitude1 = Math.toRadians(myLongitude);
        double latitude2 = Math.toRadians(targetLatitude);
        double longitude2 = Math.toRadians(targetLongitude);

        double longDiff = longitude2 - longitude1;

        double y = Math.sin(longDiff) * Math.cos(latitude2);
        double x = Math.cos(latitude1) * Math.sin(latitude2) - Math.sin(latitude1) * Math.cos(latitude2) * Math.cos(longDiff);

        double bearing = Math.atan2(y, x);

        return (Math.toDegrees(bearing) + 360) % 360;
    }

    // Convert bearing to ordinal
    // Return the ordinal direction

    public String bearingToOrdinal() {
        double bearing = bearingToDegrees();
        String[] compassPoints = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW"};

        // Convert bearing to index of compassPoints array
        int index = (int) ((bearing / 22.5) + 0.5) % 16;
        if (index < 0) {
            index += 16;
        }

        return compassPoints[index];
    }

    // Time to target
    // Calculate the distance from the target
    // Validate Speed is not zero - I've been reading its good to validate the input with exceptions and I found this on https://www.geeksforgeeks.org/best-practices-to-handle-exceptions-in-java/
    public double timeToTarget() {
        double distance = distanceFrom();
        double speed = getMovingSpeed();

        if (speed == 0) {
            throw new IllegalArgumentException("Speed cannot be zero.");
        }

        return distance / speed;
    }

    // I'm only implementing this because it is a requirement of the program assignment
    // I'd rather validate and allow modular use of the code
    public boolean dataValid() {
        if (getTargetName() == null || getTargetName().trim().isEmpty()) {
            return false;
        }
        if (getTargetLatitude() < -90.0 || getTargetLatitude() > 90.0) {
            return false;
        }
        if (getTargetLongitude() < -180.0 || getTargetLongitude() > 180.0) {
            return false;
        }
        if (getMovingSpeed() < 0) {
            return false;
        }
        return true;
    }

    // Get the current date and time
    // Return the date and time as a string
    public String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    // Alert for the navigation report
    // Show the target data in an alert
    // If the data is invalid, show an error alert
    // Data is sent to the alert as a toString
    public void showTargetData() {
        if (!dataValid()) {
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setTitle("Error");
            errorAlert.setHeaderText("Invalid Data");
            errorAlert.setContentText("Please enter valid data.");
            errorAlert.showAndWait();
            return;
        }

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Navigation Report");
        alert.setHeaderText(null);

        alert.setContentText(toString());

        alert.showAndWait();
    }

    @Override
    public String toString() {
        StringBuilder TargetAlert = new StringBuilder();
        TargetAlert.append("Target: ").append(targetName).append("\n");
        TargetAlert.append("Distance: ").append(distanceFrom()).append(" km").append("\n");
        TargetAlert.append("Bearing: ").append(bearingToDegrees()).append(" degrees (").append(bearingToOrdinal()).append(")").append("\n");
        TargetAlert.append("Currently: ").append(getDateTime()).append("\n");
        TargetAlert.append("Time to target: ").append(timeToTarget()).append(" hours");
        return TargetAlert.toString();
    }
}