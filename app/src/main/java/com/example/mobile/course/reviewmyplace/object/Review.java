package com.example.mobile.course.reviewmyplace.object;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.util.Calendar;
import java.util.Locale;

public class Review implements Parcelable {
    private int reviewID;               // id for the review assigned by the database
    private int establishmentID;        // id of the associated establishment
    // private String userID; (why?)
    private Calendar reviewDate;        // date at which the review is created/added
    private String mealType;            // type of the meal (e.g. breakfast, lunch, dinner, etc.)
    private String reviewContent;       // content of the review (i.e. description)
    private String currency;            // the currency selected by user when generating the review
    private float maxCost;              // maximum cost of food in the establishment
    private float minCost;              // minimum cost of food in the establishment
    private float overallRating;        // overall rating for the establishment as a whole
    private float serviceRating;        // rating for service
    private float atmosphereRating;     // rating for atmosphere
    private float foodRating;           // rating for food (in general)

    /** Constructors */
    public Review() {
        // Default values for ID: negative numbers (and different)
        setReviewID(-1);
        setEstablishmentID(-2);

        // Default date: current date
        setReviewDate();

        setMealType("");
        setReviewContent("");
        setCurrency("");

        setMaxCost(0.0f);
        setMinCost(0.0f);

        // Default rating: 2.5
        setRating(2.5f, 2.5f, 2.5f, 2.5f);
    }

    public Review(int id, int establishmentID, Calendar date, String mealType, String content, String currency, float minCost,
                  float maxCost, float serviceRating, float atmosphereRating, float foodRating, float overallRating) {
        setReviewID(id);
        setEstablishmentID(establishmentID);
        setReviewDate(date);
        setMealType(mealType);
        setReviewContent(content);
        setCurrency(currency);
        setMinCost(minCost);
        setMaxCost(maxCost);
        setRating(overallRating, serviceRating, atmosphereRating, foodRating);
    }

    public Review(int establishmentID, Calendar date, String mealType, String content, String currency, float minCost,
                  float maxCost, float serviceRating, float atmosphereRating, float foodRating, float overallRating) {
        setEstablishmentID(establishmentID);
        setReviewDate(date);
        setMealType(mealType);
        setReviewContent(content);
        setCurrency(currency);
        setMinCost(minCost);
        setMaxCost(maxCost);
        setRating(overallRating, serviceRating, atmosphereRating, foodRating);
    }

    public Review(Calendar date, float overallRating, String content){
        setReviewDate(date);
        setOverallRating(overallRating);
        setReviewContent(content);
    }

    public Review(Parcel in) {
        String[] strData = new String[3];
        float[] floatData = new float[6];
        int[] intData = new int[5];

        // Extract String fields
        in.readStringArray(strData);
        mealType = strData[0];
        reviewContent = strData[1];
        currency = strData[2];

        // Extract float fields
        in.readFloatArray(floatData);
        minCost = floatData[0];
        maxCost = floatData[1];
        serviceRating = floatData[2];
        atmosphereRating = floatData[3];
        foodRating = floatData[4];
        overallRating = floatData[5];

        // Extract int fields
        in.readIntArray(intData);
        reviewID = intData[0];
        establishmentID = intData[1];
        reviewDate = Calendar.getInstance();
        reviewDate.set(intData[4], intData[3], intData[2]);
    }

    /** Get/Set for reviewID */
    public void setReviewID(int reviewID) {
        this.reviewID = reviewID;
    }

    public int getReviewID() {
        return reviewID;
    }

    /** Get/Set for establishmentID */
    public void setEstablishmentID(int establishmentID) {
        this.establishmentID = establishmentID;
    }

    public int getEstablishmentID() {
        return establishmentID;
    }

    /** Get/Set for reviewDate */
    public void setReviewDate(Calendar reviewDate) {
        this.reviewDate = (Calendar) reviewDate.clone();
    }

    public void setReviewDate() {
        reviewDate = Calendar.getInstance();
    }

    public Calendar getReviewDate() {
        return reviewDate;
    }

    /** Get/Set for mealType */
    public void setMealType(String mealType) {
        this.mealType = mealType;
    }

    public String getMealType() {
        return mealType;
    }

    /** Get/Set for reviewContent */
    public void setReviewContent(String reviewContent) {
        this.reviewContent = reviewContent;
    }

    public String getReviewContent() {
        return reviewContent;
    }

    /** Get/Set for maxCost */
    public void setMaxCost(float maxCost) {
        this.maxCost = maxCost;
    }

    public float getMaxCost() {
        return maxCost;
    }

    /** Get/Set for minCost */
    public void setMinCost(float minCost) {
        this.minCost = minCost;
    }

    public float getMinCost() {
        return minCost;
    }

    /** Get/Set for currency */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCurrency() {
        return currency;
    }

    /**
     * Get/Set for overallRating
     * Min: 1 | Max: 5 | Step: 0.5
     * @param overallRating
     */
    public void setOverallRating(float overallRating) {
        // Check for invalid rating
        if (overallRating < 1 || overallRating >5) {
            Log.i("", "Invalid overall rating. Must be in range [1,5]");
            return;
        }

        this.overallRating = overallRating;
    }

    public float getOverallRating() {
        return overallRating;
    }

    /**
     * Get/Set for serviceRating
     * Min: 1 | Max: 5 | Step: 0.5
     * @param serviceRating
     */
    public void setServiceRating(float serviceRating) {
        // Check for invalid rating
        if (serviceRating < 1 || serviceRating > 5) {
            Log.i("", "Invalid service rating. Must be in range [1,5]");
            return;
        }

        this.serviceRating = serviceRating;
    }

    public float getServiceRating() {
        return serviceRating;
    }

    /**
     * Get/Set for atmosphereRating
     * Min: 1 | Max: 5 | Step: 0.5
     * @param atmosphereRating
     */
    public void setAtmosphereRating(float atmosphereRating) {
        // Check for invalid rating
        if (atmosphereRating < 1 || atmosphereRating > 5) {
            Log.i("", "Invalid atmosphere rating. Must be in range [1,5]");
            return;
        }

        this.atmosphereRating = atmosphereRating;
    }

    public float getAtmosphereRating() {
        return atmosphereRating;
    }

    /**
     * Get/Set for foodRating
     * Min: 1 | Max: 5 | Step: 0.5
     * @param foodRating
     */
    public void setFoodRating(float foodRating) {
        // Check for invalid rating
        if (foodRating < 1 || foodRating > 5) {
            Log.i("", "Invalid food rating. Must be in range [1,5]");
            return;
        }

        this.foodRating = foodRating;
    }

    public float getFoodRating() {
        return foodRating;
    }

    /**
     * Set four kinds of rating all at once
     * @param overall - Overall rating from user
     * @param service - Rating for service of the associated establishment
     * @param atmosphere - Rating for atmosphere in the associated establishment
     * @param food - Rating for food served at the associated establishment
     */
    public void setRating(float overall, float service, float atmosphere, float food) {
        setOverallRating(overall);
        setServiceRating(service);
        setAtmosphereRating(atmosphere);
        setFoodRating(food);
    }

    /**
     * Return a String representation of the date (Calendar) (e.g. 23rd January, 2018)
     * @return strDate - String representation of the date (Calendar)
     */
    public String getStringDate() {
        int day = reviewDate.get(Calendar.DAY_OF_MONTH);
        int year = reviewDate.get(Calendar.YEAR);
        String monthName = reviewDate.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        String strDate = day + "";
        if (day % 10 == 1 && day != 11) {
            strDate += "st ";
        } else if (day % 10 == 2 && day != 12) {
            strDate += "nd ";
        } else if (day % 10 == 3 && day != 13) {
            strDate += "rd ";
        } else {
            strDate += "th ";
        }
        strDate += monthName + ", " + year;

        return strDate;
    }

    /**
     * Return a String representation of the given date
     * @param date Given date
     * @return String representation of the specified date
     */
    public static String getStringDate(Calendar date) {
        int day = date.get(Calendar.DAY_OF_MONTH);
        int year = date.get(Calendar.YEAR);
        String monthName = date.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        String strDate = day + "";
        if (day % 10 == 1 && day != 11) {
            strDate += "st ";
        } else if (day % 10 == 2 && day != 12) {
            strDate += "nd ";
        } else if (day % 10 == 3 && day != 13) {
            strDate += "rd ";
        } else {
            strDate += "th ";
        }
        strDate += monthName + ", " + year;

        return strDate;
    }

    public static String getStringCostRange(float min, float max, String currency) {
        String convertedCurrency;
        if (currency.matches("dollar")) convertedCurrency = "USD (\u0024)";
        else if (currency.matches("euro")) convertedCurrency = "EURO (\u20ac)";
        else if (currency.matches("pound")) convertedCurrency = "POUND (\u00a3)";
        else if (currency.matches("vnd")) convertedCurrency = "VND";
        else convertedCurrency = currency;

        return String.format(Locale.getDefault(), "%s %.2f - %.2f ", currency, min, max);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringArray(new String[] {
                mealType,                               // index: 0
                reviewContent,                          // index: 1
                currency                                // index: 2
        });

        parcel.writeFloatArray(new float[] {
                minCost,                                // index: 0
                maxCost,                                // index: 1
                serviceRating,                          // index: 2
                atmosphereRating,                       // index: 3
                foodRating,                             // index: 4
                overallRating                           // index: 5
        });

        parcel.writeIntArray(new int[] {
                reviewID,                               // index: 0
                establishmentID,                        // index: 1
                reviewDate.get(Calendar.DAY_OF_MONTH),  // index: 2
                reviewDate.get(Calendar.MONTH),         // index: 3 (Note: values for MONTH [0,11]
                reviewDate.get(Calendar.YEAR)           // index: 4
        });
    }

    public static final Parcelable.Creator<Review>CREATOR = new Parcelable.Creator<Review>() {
        @Override
        public Review createFromParcel(Parcel parcel) {
            return new Review(parcel);
        }

        @Override
        public Review[] newArray(int size) {
            return new Review[size];
        }
    };
}
