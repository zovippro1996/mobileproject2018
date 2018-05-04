package com.example.mobile.course.reviewmyplace.object;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

public class Establishment implements Parcelable {
    private int establishmentID;                    // id for the establishment assigned by the database
    private String userID;                          // userID input from user (for what?)
    private String establishmentName;               // name of establishment (e.g. Trung Nguyen Coffee)
    private EstablishmentType establishmentType;    // type of establishment {Restaurant | Coffee Shop | Bar}
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

    public Establishment(Parcel in) {
        String[] strData = new String[3];
        int[] intData = new int[2];

        // Extract String fields
        in.readStringArray(strData);
        userID = strData[0];
        establishmentName = strData[1];
        food = strData[2];

        // Extract Integer (and EstablishmentType) fields
        in.readIntArray(intData);
        establishmentID = intData[0];
        switch (intData[1]) {
            case -1:
                establishmentType = EstablishmentType.NONE;
                break;
            case 1:
                establishmentType = EstablishmentType.RESTAURANT;
                break;
            case 2:
                establishmentType = EstablishmentType.COFFEE_SHOP;
                break;
            case 3:
                establishmentType = EstablishmentType.BAR;
                break;
            default:
                establishmentType = EstablishmentType.NONE;;
                Log.w("est_type_warning", "Invalid integer for establishmentType extracted from Parcel");
                break;
        }
    }

    /** Get/Set for establishmentID */
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                userID,                         // index: 0
                establishmentName,              // index: 1
                food                            // index: 2
        });

        parcel.writeIntArray(new int[] {
                establishmentID,                // index: 0
                establishmentType.getValue()    // index: 1
        });
    }

    public static final Parcelable.Creator<Establishment>CREATOR = new Parcelable.Creator<Establishment>() {
        @Override
        public Establishment createFromParcel(Parcel parcel) {
            return new Establishment(parcel);
        }

        @Override
        public Establishment[] newArray(int size) {
            return new Establishment[size];
        }
    };
}
