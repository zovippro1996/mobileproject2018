package com.example.mobile.course.reviewmyplace.helper;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.widget.RatingBar;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.R;
import com.example.mobile.course.reviewmyplace.object.Review;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;

import java.util.Calendar;

public class ReviewCursorAdapter extends ResourceCursorAdapter {
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    public ReviewCursorAdapter(Context context, int layout, Cursor cursor, int flags) {
        super(context, layout, cursor, flags);
    }

    /**
     * Bind all cursor properties to their corresponding view objects
     * @param view Existing view, returned earlier by newView
     * @param context Interface to application's global information
     * @param cursor The cursor from which to get the data (note: the cursor is already moved to the correct position)
     */
    public void bindView(View view, final Context context, Cursor cursor) {
        // Populate data from cursor to views in ListView

        // Date of the review
        Calendar date = Calendar.getInstance();
        date.setTimeInMillis(cursor.getLong(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_DATE)));

        TextView textView = view.findViewById(R.id.textView_reviewDate);
        textView.setText(Review.getStringDate(date));

        // Types of meal
        textView = view.findViewById(R.id.textView_mealType);
        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_MEAL_TYPES)));

        // Cost of the meal
        float minCost = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_MIN_COST));
        float maxCost = cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_MAX_COST));
        String currency = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_CURRENCY));

        textView = view.findViewById(R.id.textView_Cost);
        textView.setText(Review.getStringCostRange(minCost, maxCost, currency));

        // Overall rating
        RatingBar ratingBar = view.findViewById(R.id.ratingBar_overall);
        ratingBar.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_OVERALL_RATING)));

        // Service rating
        ratingBar = view.findViewById(R.id.ratingBar_service);
        ratingBar.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_SERVICE_RATING)));

        // Atmosphere rating
        ratingBar = view.findViewById(R.id.ratingBar_atmos);
        ratingBar.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_ATMOSPHERE_RATING)));

        // Food rating
        ratingBar = view.findViewById(R.id.ratingBar_food);
        ratingBar.setRating(cursor.getFloat(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_FOOD_RATING)));

        // Comment
        textView = view.findViewById(R.id.textView_reviewContent);
        textView.setText(cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper.COL_COMMENT)));
    }
}
