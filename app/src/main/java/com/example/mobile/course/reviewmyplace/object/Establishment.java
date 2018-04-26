package com.example.mobile.course.reviewmyplace.object;

public class Establishment {
    private int establishmentID;                    // id for the establishment assigned by the
    // database
    private String userID;                          // userID input from user (for what?)
    private String establishmentName;               // name of establishment (e.g. Trung Nguyen
    // Coffee)
    private EstablishmentType establishmentType;    // type of establishment {Restaurant | Coffe
    // Shop | Bar}
    private String food;                            // food served in the establishment
    private Location establishmentLocation;         // location of the establishment

    /**
     * Constructors
     */
    public Establishment() {
        // Default ID: negative value
        setEstablishmentID(-1);

        setUserID("");
        setEstablishmentName("");
        setFood("");

        // Default type: EstablishmentType.NONE
        setEstablishmentType(EstablishmentType.NONE);

        // Default Location: a Location object with empty description
        setEstablishmentLocation("");
    }


    public Establishment(int id, String userID, String name, EstablishmentType type, String food,
                         String locationDescription) {
        setEstablishmentID(id);
        setUserID(userID);
        setEstablishmentName(name);
        setEstablishmentType(type);
        setFood(food);
        setEstablishmentLocation(locationDescription);
    }

    public Establishment(int id, String userID, String name, EstablishmentType type, String food,
                         Location location) {
        setEstablishmentID(id);
        setUserID(userID);
        setEstablishmentName(name);
        setEstablishmentType(type);
        setFood(food);
        setEstablishmentLocation(location);
    }

    /**
     * Get/Set for establishmentID
     */
    public void setEstablishmentID(int establishmentID) {
        this.establishmentID = establishmentID;
    }

    public int getEstablishmentID() {
        return establishmentID;
    }

    /**
     * Get/Set for userID
     */
    public void setUserID(String userID) {
        this.userID = userID;
    }

    public String getUserID() {
        return userID;
    }

    /**
     * Get/Set for establishmentName
     */
    public void setEstablishmentName(String establishmentName) {
        this.establishmentName = establishmentName;
    }

    public String getEstablishmentName() {
        return establishmentName;
    }

    /**
     * Get/Set for establishmentType
     */
    public void setEstablishmentType(EstablishmentType establishmentType) {
        this.establishmentType = establishmentType;
    }

    public EstablishmentType getEstablishmentType() {
        return establishmentType;
    }

    /**
     * Get/Set for food
     */
    public void setFood(String food) {
        this.food = food;
    }

    public String getFood() {
        return food;
    }

    /**
     * Setter for establishmentLocation that receives Location parameter
     *
     * @param location
     */
    public void setEstablishmentLocation(Location location) {
        establishmentLocation = new Location(location);
    }

    /**
     * Setter for establishmentLocation that receives String parameter
     *
     * @param description
     */
    public void setEstablishmentLocation(String description) {
        establishmentLocation = new Location(description);
    }

    /**
     * Setter for establishmentLocation
     */
    public Location getEstablishmentLocation() {
        return establishmentLocation;
    }
}
