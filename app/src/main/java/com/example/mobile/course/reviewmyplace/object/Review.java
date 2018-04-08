package com.example.mobile.course.reviewmyplace.object;

import android.util.Log;

import java.util.Date;

public class Review {
    private int reviewID;               // id for the review assigned by the database
    private int establishmentID;        // id of the associated establishment
    // private String userID; (why?)
    private Date reviewDate;            // date at which the review is created/added
    private String mealType;            // type of the meal (e.g. breakfast, lunch, dinner, etc.)
    private String reviewContent;       // content of the review (i.e. description)
    private float billCost;             // average cost of the establishment
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
        setReviewDate(new Date());

        setMealType("");
        setReviewContent("");
        setBillCost(0.0f);

        // Default rating: 2.5
        setRating(2.5f, 2.5f, 2.5f, 2.5f);
    }

    public Review(int id, int establishmentID, Date date, String mealType, String content, float cost,
                  float serviceRating, float atmosphereRating, float foodRating, float overallRating) {
        setReviewID(id);
        setEstablishmentID(establishmentID);
        setReviewDate(date);
        setMealType(mealType);
        setReviewContent(content);
        setBillCost(cost);
        setRating(overallRating, serviceRating, atmosphereRating, foodRating);
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
    public void setReviewDate(Date reviewDate) {
        this.reviewDate = (Date) reviewDate.clone();
    }

    public Date getReviewDate() {
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

    /** Get/Set for billCost */
    public void setBillCost(float billCost) {
        this.billCost = billCost;
    }

    public float getBillCost() {
        return billCost;
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
     * @param overall
     * @param service
     * @param atmosphere
     * @param food
     */
    public void setRating(float overall, float service, float atmosphere, float food) {
        setOverallRating(overall);
        setServiceRating(service);
        setAtmosphereRating(atmosphere);
        setFoodRating(food);
    }
}
