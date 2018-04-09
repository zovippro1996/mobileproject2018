package com.example.mobile.course.reviewmyplace.object;

public class Location {
    private String description;     // input from user
    private float longitude;        // determined from Google Maps (?)
    private float latitude;

    /** Constructors */
    public Location() {
        setDescription("");

        // Extract coordination (longitude + latitude)
        extractCoordinate();
    }

    public Location(String description) {
        setDescription(description);

        // Extract coordinate
        extractCoordinate();
    }

    public Location(Location location) {
        setDescription(location.getDescription());

        // Extract coordinate
        extractCoordinate();
    }

    /** Get/Set for description */
    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    /**
     * Get/Set for longitude
     * Can only be set privately based on the input description from user
     * @param longitude
     */
    private void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLongitude() {
        return longitude;
    }

    /**
     * Get/Set for latitude
     * Can only be set privately based on the input description from user
     * @param latitude
     */
    private void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLatitude() {
        return latitude;
    }

    /**
     * Determine longitude and latitude based on the input description from user
     * Then, set the values of variables 'longitude' and 'latitude' accordingly
     * If invalid location, set to default values (0.0)
     */
    private void extractCoordinate() {
        // Do something...

        setLongitude(0.0f);
        setLatitude(0.0f);
    }
}
