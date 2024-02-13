package org.example.program2;

import javafx.scene.control.Alert;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * The Target class represents a geographical target with a name, latitude, longitude, and moving speed.
 * It provides methods to calculate the distance from the target, the bearing to the target, and the time to reach the target.
 */
public class Target {
    final double RADIUS_EARTH = 6371.01;
    private String targetName;
    private double targetLatitude;
    private double targetLongitude;

    // Hard coding my latitude and longitude as Delta College
    private double myLatitude = 43.559;
    private double myLongitude = -83.986;
    private double movingSpeed;

    /**
     * No-argument constructor for the Target class.
     */
    public Target() {
    }

    /**
     * Parameterized constructor for the Target class.
     *
     * @param targetName the name of the target
     * @param targetLatitude the latitude of the target
     * @param targetLongitude the longitude of the target
     * @param myLatitude the latitude of the current location
     * @param myLongitude the longitude of the current location
     * @param movingSpeed the speed at which the target is moving
     */
    public Target(String targetName,double targetLatitude, double targetLongitude, double myLatitude, double myLongitude, double movingSpeed) {
        this.targetName = targetName;
        this.targetLatitude = targetLatitude;
        this.targetLongitude = targetLongitude;
        this.myLatitude = myLatitude;
        this.myLongitude = myLongitude;
        this.movingSpeed = movingSpeed;
    }

    // Getters and setters
    /**
     * Gets the name of the target.
     *
     * @return the name of the target
     */
    public String getTargetName() {
        return targetName;
    }

    /**
     * Sets the name of the target.
     *
     * @param targetName the name of the target
     */
    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    /**
     * Gets the latitude of the target.
     *
     * @return the latitude of the target
     */
    public double getTargetLatitude() {
        return targetLatitude;
    }

    /**
     * Sets the latitude of the target.
     *
     * @param targetLatitude the latitude of the target
     * @throws IllegalArgumentException if the latitude is not between -90.0 and 90.0 degrees
     */
    public void setTargetLatitude(double targetLatitude) throws IllegalArgumentException {
        validateLatitude(targetLatitude);
        this.targetLatitude = targetLatitude;
    }

    /**
     * Validates the latitude of the target.
     *
     * @param latitude the latitude of the target
     * @throws IllegalArgumentException if the latitude is not between -90.0 and 90.0 degrees
     */
    private void validateLatitude(double latitude) throws IllegalArgumentException {
        if (latitude < -90.0 || latitude > 90.0) {
            throw new IllegalArgumentException("Latitude must be between -90.0 and 90.0 degrees.");
        }
    }

    /**
     * Gets the longitude of the target.
     *
     * @return the longitude of the target
     */
    public double getTargetLongitude() {
        return targetLongitude;
    }

    /**
     * Sets the longitude of the target.
     *
     * @param targetLongitude the longitude of the target
     * @throws IllegalArgumentException if the longitude is not between -180.0 and 180.0 degrees
     */
    public void setTargetLongitude(double targetLongitude) throws IllegalArgumentException {
        validateLongitude(targetLongitude);
        this.targetLongitude = targetLongitude;
    }

    /**
     * Validates the longitude of the target.
     *
     * @param longitude the longitude of the target
     * @throws IllegalArgumentException if the longitude is not between -180.0 and 180.0 degrees
     */
    private void validateLongitude(double longitude) throws IllegalArgumentException {
        if (longitude < -180.0 || longitude > 180.0) {
            throw new IllegalArgumentException("Longitude must be between -180.0 and 180.0 degrees.");
        }
    }

    /**
     * Gets the latitude of the current location.
     *
     * @return the latitude of the current location
     */
    public double getMyLatitude() {
        return myLatitude;
    }

    /**
     * Sets the latitude of the current location.
     *
     * @param myLatitude the latitude of the current location
     */
    public void setMyLatitude(double myLatitude) {
        this.myLatitude = myLatitude;
    }

    /**
     * Gets the longitude of the current location.
     *
     * @return the longitude of the current location
     */
    public double getMyLongitude() {
        return myLongitude;
    }

    /**
     * Sets the longitude of the current location.
     *
     * @param myLongitude the longitude of the current location
     */
    public void setMyLongitude(double myLongitude) {
        this.myLongitude = myLongitude;
    }

    /**
     * Gets the moving speed of the target.
     *
     * @return the moving speed of the target
     */
    public double getMovingSpeed() {
        return movingSpeed;
    }

    /**
     * Sets the moving speed of the target.
     *
     * @param movingSpeed the moving speed of the target
     * @throws IllegalArgumentException if the moving speed is negative
     */
    public void setMovingSpeed(double movingSpeed) throws IllegalArgumentException {
        validateMovingSpeed(movingSpeed);
        this.movingSpeed = movingSpeed;
    }

    /**
     * Validates the moving speed of the target.
     *
     * @param speed the moving speed of the target
     * @throws IllegalArgumentException if the moving speed is negative
     */
    private void validateMovingSpeed(double speed) throws IllegalArgumentException {
        if (speed < 0) {
            throw new IllegalArgumentException("Speed cannot be negative.");
        }
    }


    /**
     * Calculates the distance from the current location to the target.
     *
     * @return the distance from the current location to the target
     */
    public double distanceFrom() {


        // Convert coordinates to radians
        double lat1 = Math.toRadians(targetLatitude);
        double lon1 = Math.toRadians(targetLongitude);
        double lat2 = Math.toRadians(myLatitude);
        double lon2 = Math.toRadians(myLongitude);

        return RADIUS_EARTH * Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon2 - lon1));
    }

    /**
     * Calculates the bearing from the current location to the target in degrees.
     *
     * @return the bearing from the current location to the target in degrees
     */
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

    /**
     * Converts the bearing from the current location to the target to an ordinal direction.
     *
     * @return the bearing from the current location to the target as an ordinal direction
     */
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

    /**
     * Calculates the time to reach the target from the current location.
     *
     * @return the time to reach the target from the current location
     * @throws IllegalArgumentException if the moving speed is zero
     */
    public double timeToTarget() {
        double distance = distanceFrom();
        double speed = getMovingSpeed();

        if (speed == 0) {
            throw new IllegalArgumentException("Speed cannot be zero.");
        }

        return distance / speed;
    }

    /**
     * Validates the data of the target.
     *
     * @return true if the data of the target is valid, false otherwise
     */
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

    /**
     * Gets the current date and time.
     *
     * @return the current date and time as a string
     */
    public String getDateTime() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return now.format(formatter);
    }

    /**
     * Sets the name of the target.
     *
     * @param targetName the name of the target
     * @throws EmptyNameException if the target name is null or empty
     */
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
        StringBuilder targetAlert = new StringBuilder();
        targetAlert.append("Target: ").append(targetName).append("\n");
        targetAlert.append("Distance: ").append(String.format("%.2f", distanceFrom())).append(" km").append("\n");
        targetAlert.append("Bearing: ").append(String.format("%.0f", bearingToDegrees())).append(" degrees (").append(bearingToOrdinal()).append(")").append("\n");
        targetAlert.append("Currently: ").append(getDateTime()).append("\n");
        targetAlert.append("Time to target: ").append(String.format("%.2f", timeToTarget())).append(" hours");
        return targetAlert.toString();
    }
}