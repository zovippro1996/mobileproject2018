package com.example.mobile.course.reviewmyplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.object.Review;

import java.util.Locale;

public class ReviewFormContActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form_cont);

        // Extract extra data from intent
        Intent intent = getIntent();
        Review review = intent.getParcelableExtra(ReviewFormActivity.EXTRA_REVIEW);

        // Display input date
        TextView textView = findViewById(R.id.review_form_cont_picked_date);
        textView.setText(review.getStringDate());

        // Display input types of meal
        textView = findViewById(R.id.review_form_cont_meal_types);
        textView.setText(review.getMealType());

        // Display input min. & max. costs with currency
        textView = findViewById(R.id.review_form_cont_min_cost);
        textView.setText(String.format(Locale.getDefault(), "%.2f", review.getMinCost()));

        textView = findViewById(R.id.review_form_cont_max_cost);
        textView.setText(String.format(Locale.getDefault(), "%.2f", review.getMaxCost()));

        textView = findViewById(R.id.review_form_cont_currency);
        textView.setText(convertCurrency(review.getCurrency()));

        // Display input ratings
        RatingBar ratingBar = findViewById(R.id.review_form_cont_service_rating);
        ratingBar.setRating(review.getServiceRating());

        ratingBar = findViewById(R.id.review_form_cont_atmosphere_rating);
        ratingBar.setRating(review.getAtmosphereRating());

        ratingBar = findViewById(R.id.review_form_cont_food_rating);
        ratingBar.setRating(review.getFoodRating());

        ratingBar = findViewById(R.id.review_form_cont_overall_rating);
        ratingBar.setRating(review.getOverallRating());
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
     * Save all inputs into the database
     */
    public void onSaveButton(View view) {

    }
}
