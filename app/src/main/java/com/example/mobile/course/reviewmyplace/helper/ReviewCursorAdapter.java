package com.example.mobile.course.reviewmyplace.helper;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.media.Image;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ResourceCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.course.reviewmyplace.EstablishmentConfirmationActivity;
import com.example.mobile.course.reviewmyplace.EstablishmentDetailActivity;
import com.example.mobile.course.reviewmyplace.R;
import com.example.mobile.course.reviewmyplace.object.Review;
import com.facebook.CallbackManager;
import com.facebook.share.widget.ShareDialog;

import java.util.Calendar;

public class ReviewCursorAdapter extends ResourceCursorAdapter {

    DatabaseHelper databaseHelper;

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

        databaseHelper = new DatabaseHelper(context);
        final int ReviewID = cursor.getInt(cursor.getColumnIndexOrThrow("_id"));
        final String mStrEstablishmentID = cursor.getString(cursor.getColumnIndexOrThrow(DatabaseHelper
                .COL_ESTABLISHMENT_ID));

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

        ImageView imageView_deleteReview = view.findViewById(R.id.imageView_deleteReview);

        //onClickActionListener_Delete Review
        imageView_deleteReview.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(final View view) {
                // Pop-up dialog asking for confirmation
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage(R.string.action_delete_message_review);
                builder.setPositiveButton(R.string.action_delete_positive_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try {
                            // Delete record in database
                            databaseHelper.deleteReviewRecord(ReviewID);
                            // Notify successful saving
                            Toast.makeText(context, "Review Deleted", Toast.LENGTH_LONG).show();

                            //Finish Trace and Redirect to EstDetailActivity
                            ((Activity)context).finish();
                            Intent intent = new Intent(view.getContext(), EstablishmentDetailActivity.class);
                            intent.putExtra(EstablishmentConfirmationActivity
                                    .EXTRA_ESTABLISHMENT_ID, mStrEstablishmentID);

                        } catch (SQLiteException sqle) {
                            Log.w(this.getClass().getName(), "Error saving to database");

                            // Notify unsuccessful saving
                            Toast.makeText(context, "Cannot Save Review", Toast.LENGTH_LONG).show();
                        }
                    }
                });
                builder.setNegativeButton(R.string.action_delete_negative_button, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // do nothing
                    }
                });

                // Show
                builder.show();

            }
        });
    }
}
