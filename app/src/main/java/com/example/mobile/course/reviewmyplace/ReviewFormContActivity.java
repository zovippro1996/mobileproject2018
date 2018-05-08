package com.example.mobile.course.reviewmyplace;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteException;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.course.reviewmyplace.helper.DatabaseHelper;
import com.example.mobile.course.reviewmyplace.object.Review;

import java.util.Locale;

public class ReviewFormContActivity extends AppCompatActivity {

    private Review mReview;
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form_cont);

        // Extract extra data from intent
        Intent intent = getIntent();
        mReview = intent.getParcelableExtra(ReviewFormActivity.EXTRA_REVIEW);

        // Display input date
        TextView textView = findViewById(R.id.review_form_cont_picked_date);
        textView.setText(mReview.getStringDate());

        // Display input types of meal
        textView = findViewById(R.id.review_form_cont_meal_types);
        textView.setText(mReview.getMealType());

        // Display input min. & max. costs with currency
        textView = findViewById(R.id.review_form_cont_min_cost);
        textView.setText(String.format(Locale.getDefault(), "%.2f", mReview.getMinCost()));

        textView = findViewById(R.id.review_form_cont_max_cost);
        textView.setText(String.format(Locale.getDefault(), "%.2f", mReview.getMaxCost()));

        textView = findViewById(R.id.review_form_cont_currency);
        textView.setText(convertCurrency(mReview.getCurrency()));

        // Display input ratings
        RatingBar ratingBar = findViewById(R.id.review_form_cont_service_rating);
        ratingBar.setRating(mReview.getServiceRating());

        ratingBar = findViewById(R.id.review_form_cont_atmosphere_rating);
        ratingBar.setRating(mReview.getAtmosphereRating());

        ratingBar = findViewById(R.id.review_form_cont_food_rating);
        ratingBar.setRating(mReview.getFoodRating());

        ratingBar = findViewById(R.id.review_form_cont_overall_rating);
        ratingBar.setRating(mReview.getOverallRating());

        // Get ready to access the database later
        mDatabaseHelper = new DatabaseHelper(this);
    }

    /**
     * Convert value of currency (set in array list) into its String representation (name + symbol)
     * @param value Value set in array list for each supported currency
     * @return String representation (name + symbol) of the corresponding currency
     */
    private String convertCurrency(String value) {
        if (value.matches("dollar")) return "USD (\u0024)";
        if (value.matches("euro")) return "EURO (\u20ac)";
        if (value.matches("pound")) return "POUND (\u00a3)";
        if (value.matches("vnd")) return "VND";

        return value;
    }

    /**
     * Display alert dialog asking for user confirmation before saving
     * @param view View context
     */
    public void onSaveButton(View view) {
        // Extract input comment (if any)
        EditText editText = findViewById(R.id.review_form_cont_comment);
        mReview.setReviewContent(editText.getText().toString());

        // Use the Builder class to ask for confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.alert_dialog_confirm_message);
        builder.setPositiveButton(R.string.alert_dialog_confirm_positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                saveReview();       // save the input Review into the database
            }
        });
        builder.setNegativeButton(R.string.alert_dialog_confirm_negative, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // do nothing
            }
        });

        // Create and display the AlertDialog
        builder.show();
    }

    protected void saveReview() {
        try {
            // Insert into database
            mDatabaseHelper.insertReview(mReview);

            // Notify successful saving
            popupToast("Your mReview has been saved successfully");

            // Open the corresponding Establishment detailed screen (i.e. EstablishmentDetailActivity)
            Intent intent = new Intent(this, EstablishmentDetailActivity.class);
            intent.putExtra(EstablishmentConfirmationActivity.EXTRA_ESTABLISHMENT_ID, Integer.toString( mReview
                    .getEstablishmentID()));
            startActivity(intent);
        } catch (SQLiteException sqle) {
            Log.w(this.getClass().getName(), "Error saving to database");

            // Notify unsuccessful saving
            popupToast("Couldn't save your mReview into database");
        }
    }

    /**
     * Display a popup "toast" alert at the bottom of the device
     * @param message Message to be displayed in the popup
     */
    private void popupToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
