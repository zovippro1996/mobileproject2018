package com.example.mobile.course.reviewmyplace.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.mobile.course.reviewmyplace.object.Establishment;
import com.example.mobile.course.reviewmyplace.object.EstablishmentType;
import com.example.mobile.course.reviewmyplace.object.Review;

import java.util.ArrayList;
import java.util.Calendar;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "Establishment_Review";

    // Associated columns for Establishment table
    private static final String TABLE_NAME_ESTABLISHMENT = "establishments";
    public static final String COL_LOCATION_DESC = "location_description";
    public static final String COL_ESTABLISHMENT_TYPE = "establishment_type";
    public static final String COL_ESTABLISHMENT_NAME = "establishment_name";
    public static final String COL_FOOD = "food";
    public static final String COL_USER_ID = "user_id";

    // Associated columns for Review table
    private static final String TABLE_NAME_REVIEW = "reviews";
    public static final String COL_ESTABLISHMENT_ID = "establishment_id";
    public static final String COL_DATE = "date";
    public static final String COL_MEAL_TYPES = "meal_types";
    public static final String COL_MIN_COST = "min_cost";
    public static final String COL_MAX_COST = "max_cost";
    public static final String COL_CURRENCY = "currency";
    public static final String COL_SERVICE_RATING = "service_rating";
    public static final String COL_ATMOSPHERE_RATING = "atmosphere_rating";
    public static final String COL_FOOD_RATING = "food_rating";
    public static final String COL_OVERALL_RATING = "overall_rating";
    public static final String COL_COMMENT = "comment";

    private SQLiteDatabase database;


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

        // Reference to database for later uses
        database = getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        // Establishment table
        database.execSQL("CREATE TABLE " + TABLE_NAME_ESTABLISHMENT + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ESTABLISHMENT_TYPE + " TEXT," +             // Enum -> store as enum's name
                COL_ESTABLISHMENT_NAME + " TEXT," +
                COL_FOOD + " TEXT," +
                COL_USER_ID + " TEXT," +
                COL_LOCATION_DESC + " TEXT);");

        // Review table
        database.execSQL("CREATE TABLE " + TABLE_NAME_REVIEW + "(" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_ESTABLISHMENT_ID + " INTEGER," +
                COL_DATE + " INTEGER," +                        // Date -> store as number of milliseconds
                COL_MEAL_TYPES + " TEXT," +
                COL_MIN_COST + " REAL," +
                COL_MAX_COST + " REAL," +
                COL_CURRENCY + " TEXT," +
                COL_SERVICE_RATING + " REAL," +
                COL_ATMOSPHERE_RATING + " REAL," +
                COL_FOOD_RATING + " REAL," +
                COL_OVERALL_RATING + " REAL," +
                COL_COMMENT + " TEXT," +
                "FOREIGN KEY(" + COL_ESTABLISHMENT_ID + ") REFERENCES " + TABLE_NAME_ESTABLISHMENT + "(_id));");

        // Add FOREIGN KEY later (comment for testing)
//        "FOREIGN KEY(" + COL_ESTABLISHMENT_ID + ") REFERENCES " + TABLE_NAME_ESTABLISHMENT + "(_id));");
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, int oldVersion, int newVersion) {
        // User will lose old data but at least we'll warn them this is happening
        Log.w(this.getClass().getName(), DATABASE_NAME
                + " database upgrade to version " + newVersion
                + " old data lost");

        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_REVIEW);
        database.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME_ESTABLISHMENT);

        onCreate(database);
    }

    /**
     * Insert the given Establishment object into the corresponding table in database
     * @param establishment Establishment object to be stored
     * @return ID of the establishment just been inserted
     */
    public long insertEstablishment(Establishment establishment) {
        ContentValues rowValues = new ContentValues();

        // Assemble row of data in ContentValues object
        rowValues.put(COL_ESTABLISHMENT_TYPE, establishment.getEstablishmentType().name());
        rowValues.put(COL_ESTABLISHMENT_NAME, establishment.getEstablishmentName());
        rowValues.put(COL_FOOD, establishment.getFood());
        rowValues.put(COL_USER_ID, establishment.getUserID());
        rowValues.put(COL_LOCATION_DESC, establishment.getEstablishmentLocation().getDescription());

        // Record the id of the 'establishment' just been inserted
        return database.insertOrThrow(TABLE_NAME_ESTABLISHMENT, null, rowValues);
    }

    /**
     * Insert all given inputs into Establishment table in database
     * @param type  Type of establishment
     * @param name  Name of the establishment
     * @param food  Food served in the establishment
     * @param userID  Entered user_id from user
     * @param locationDesc   Description of establishment's location
     * @return ID ò the establishment just been inserted
     */
    public long insertEstablishment(EstablishmentType type, String name, String food,
                                    String userID, String locationDesc) {
        ContentValues rowValues = new ContentValues();

        // Assemble row of data in ContentValues object
        rowValues.put(COL_ESTABLISHMENT_TYPE, type.name());
        rowValues.put(COL_ESTABLISHMENT_NAME, name);
        rowValues.put(COL_FOOD, food);
        rowValues.put(COL_USER_ID, userID);
        rowValues.put(COL_LOCATION_DESC, locationDesc);

        // Record the id of the 'establishment' just been inserted
        return database.insertOrThrow(TABLE_NAME_ESTABLISHMENT, null, rowValues);
    }

    /**
     * Insert the given Review object into the corresponding table in database
     * @param review Review object to be stored
     * @return ID of the review just been inserted
     */
    public long insertReview(Review review) {
        ContentValues rowValues = new ContentValues();

        // Assemble row of data in the ContentValues object
        rowValues.put(COL_ESTABLISHMENT_ID, review.getEstablishmentID());
        rowValues.put(COL_DATE, review.getReviewDate().getTimeInMillis());
        rowValues.put(COL_MEAL_TYPES, review.getMealType());
        rowValues.put(COL_MIN_COST, review.getMinCost());
        rowValues.put(COL_MAX_COST, review.getMaxCost());
        rowValues.put(COL_CURRENCY, review.getCurrency());
        rowValues.put(COL_SERVICE_RATING, review.getServiceRating());
        rowValues.put(COL_ATMOSPHERE_RATING, review.getAtmosphereRating());
        rowValues.put(COL_FOOD_RATING, review.getFoodRating());
        rowValues.put(COL_OVERALL_RATING, review.getOverallRating());
        rowValues.put(COL_COMMENT, review.getReviewContent());

        return database.insertOrThrow(TABLE_NAME_REVIEW, null, rowValues);
    }

    /**
     * Get the number of establishment records (by doing a query - maybe inefficient)
     * @return Number of establishment records
     */
    public long getNumberOfEstablishmentRecords() {
        Cursor cursor = database.query(TABLE_NAME_ESTABLISHMENT, null, null,
                null, null, null, null);

        long counter = cursor.getCount();
        cursor.close();

        return counter;
    }

    /**
     * Get all records of Establishment table ordered by the time added into database
     * @return A cursor holding all establishment records ordered by name
     */
    public Cursor getAllEstablishmentRecords() {
        return database.query(TABLE_NAME_ESTABLISHMENT, null, null,
                null, null, null, null);
    }

    /**
     * Get all records of Establishment table whose name matches the on-typing filter name
     * and whose type is one of the filter types, ordered by either establishment_name or
     * the time they were added into database (based on value of 'sorted')
     * @param filterName The on-typing filter name
     * @param filterTypes List of all establishment types user wants to filter
     * @param sorted Sort all records in alphabetical order or not
     * @return A cursor holding all matching Establishment records
     */
    public Cursor getFilteredEstablishmentRecords(String filterName, ArrayList<String> filterTypes,
                                                  boolean sorted) {
        // Construct 'String' representation of range of types
        StringBuilder typesBuilder = new StringBuilder("(");
        if (filterTypes.isEmpty()) {
            typesBuilder.append("'RESTAURANT','COFFEE_SHOP','BAR')");
        } else {
            for (String type : filterTypes) {
                typesBuilder.append("'");
                typesBuilder.append(type);
                typesBuilder.append("',");
            }
            typesBuilder.setCharAt(typesBuilder.length() - 1, ')');
        }

        return database.query(TABLE_NAME_ESTABLISHMENT, null,
                COL_ESTABLISHMENT_NAME + " like ? and " + COL_ESTABLISHMENT_TYPE + " in " + typesBuilder.toString(),
                new String[] {"%" + filterName + "%"}, null, null, sorted ?
                        COL_ESTABLISHMENT_NAME :
                        null);
    }

    /**
     * Get Establishment record whose ID matches the given ID
     * @param id Given ID to filter
     * @return A cursor holding (all) matching Establishment record(s)
     */
    public Cursor getEstablishmentRecord(int id) {
        return database.query(TABLE_NAME_ESTABLISHMENT, null,
                COL_ESTABLISHMENT_ID + " = ?", new String[] {String.valueOf(id)},
                null, null, null);
    }

    /**
     * Get all Review records ordered by date column
     * @return A cursor holding all Review records ordered by date
     */
    public Cursor getAllReviewRecordsOrderByDate() {
        return database.query(TABLE_NAME_REVIEW, null, null, null,
                null, null, COL_DATE);
    }

    /**
     * Get all Review records ordered by date column
     * @return A cursor holding all Review records ordered by date
     */
    public Cursor getAllReviewRecordsOrderByDate_latest(String establishmentID) {

        return database.query(TABLE_NAME_REVIEW, null, COL_ESTABLISHMENT_ID + " = ?",
                new String[] {establishmentID},null, null, COL_OVERALL_RATING +" DESC","2");
    }

    /**
     * Get the best Review record in terms of the specified criterion
     * @param criterion Criterion to choose the best Review record. Must be one of the following
     *                  {"date", "service", "atmosphere", "food", "overal"}
     * @return A cursor holding the best qualified
     */
    public Cursor getBestReviewRecordBasedOn(String criterion) {
        String orderBy;
        if (criterion.equalsIgnoreCase("date")) {
            orderBy = COL_DATE;
        } else if (criterion.equalsIgnoreCase("service")) {
            orderBy = COL_SERVICE_RATING;
        } else if (criterion.equalsIgnoreCase("atmosphere")) {
            orderBy = COL_ATMOSPHERE_RATING;
        } else if (criterion.equalsIgnoreCase("food")) {
            orderBy = COL_FOOD_RATING;
        } else if (criterion.equalsIgnoreCase("overall")) {
            orderBy = COL_OVERALL_RATING;
        } else {
            // Invalid value
            orderBy = "null";

            Log.e("ReviewOrderCriterion", "Invalid column name for ORDER BY filter");
        }

        return database.query(TABLE_NAME_REVIEW, null, null, null,
                null, null,
                orderBy.equalsIgnoreCase("null") ? orderBy + " DESC" : null,
                "1");
    }

    /**
     * Get all Review records ordered by service_rating column
     * @return A cursor holding all Review records ordered by service rating
     */
    public Cursor getAllReviewRecordsOrderByService() {
        return database.query(TABLE_NAME_REVIEW, null, null, null,
                null, null, COL_SERVICE_RATING);
    }

    /**
     * Get all Review records ordered by atmosphere_rating column
     * @return A cursor holding all Review records ordered by atmosphere rating
     */
    public Cursor getAllReviewRecordsOrderByAtmosphere() {
        return database.query(TABLE_NAME_REVIEW, null, null, null,
                null, null, COL_ATMOSPHERE_RATING);
    }

    /**
     * Get all Review records ordered by food_rating column
     * @return A cursor holding all Review records ordered by food rating
     */
    public Cursor getAllReviewRecordsOrderByFood() {
        return database.query(TABLE_NAME_REVIEW, null, null, null,
                null, null, COL_FOOD_RATING);
    }

    /**
     * Get all Review records ordered by overall_rating column
     * @return A cursor holding all Review records ordered by overall rating
     */
    public Cursor getAllReviewRecordsOverall() {
        return database.query(TABLE_NAME_REVIEW, null, null, null,
                null, null, COL_OVERALL_RATING);
    }

    /**
     * Get all reviews associated with establishment whose ID matches the specified ID
     * @param establishmentID Specified ID of the Establishment whose reviews need to  be retrieved
     * @return A cursor holding all matching Reviews
     */
    public Cursor getAllReviewRecordsFromEstablishment(String establishmentID) {

        return database.query(TABLE_NAME_REVIEW, null, COL_ESTABLISHMENT_ID + " = ?",
                new String[] {establishmentID}, null, null, COL_DATE +" DESC");
    }

    /**
     * Get the number of establishment records (by doing a query - maybe inefficient)
     * @return Number of establishment records
     */
    public long getNumberOfReviewRecords() {
        Cursor cursor = database.query(TABLE_NAME_REVIEW, null, null,
                null, null, null, null);

        long counter = cursor.getCount();
        cursor.close();

        return counter;
    }

    /**
     * Get the number of reviews associated with the specified establishment (through ID)
     * @param establishmentID ID of the desired establishment
     * @return Number of matching reviews
     */
    public long getNumberOfReviewRecordsFromEstablishment(String establishmentID) {
        Cursor cursor = database.query(TABLE_NAME_REVIEW, null, COL_ESTABLISHMENT_ID + " = ?",
                new String[] {establishmentID}, null, null, null);

        long counter = cursor.getCount();
        cursor.close();

        return counter;
    }



    /**
     * Delete all Establishment records
     */
    public void deleteAllEstablishmentRecords() {
        database.delete(TABLE_NAME_ESTABLISHMENT, null, null);
    }

    /**
     * Delete all Review records
     */
    public void deleteAllReviewRecords() {
        database.delete(TABLE_NAME_REVIEW, null, null);
    }

    /**
     * Delete the Establishment record with the matching _id
     * @param id ID of the establishment to be deleted
     */
    public void deleteEstablishmentRecord(int id) {
        // Delete all associated Review records first (if any)
        deleteAllReviewRecordsOfEstablishment(id);

        database.delete(TABLE_NAME_ESTABLISHMENT, "_id = ?", new String[] {String.valueOf(id)});
    }

    /**
     * Delete the Review record with the matching _id
     * @param id ID of the review to be deleted
     */
    public void deleteReviewRecord(int id) {
        database.delete(TABLE_NAME_REVIEW, "_id = ?", new String[] {String.valueOf(id)});
    }

    /**
     * Delete all Review records associated with the specified Establishment
     * @param establishment_id Id of the associated Establishment record
     */
    public void deleteAllReviewRecordsOfEstablishment(int establishment_id) {
        database.delete(TABLE_NAME_REVIEW, COL_ESTABLISHMENT_ID + " = ?", new String[] {String.valueOf(establishment_id)});
    }
}
