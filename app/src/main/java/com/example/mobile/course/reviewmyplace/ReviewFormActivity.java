package com.example.mobile.course.reviewmyplace;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.helper.DatePickerFragment;
import com.example.mobile.course.reviewmyplace.object.Review;

import java.util.Calendar;
import java.util.Locale;

public class ReviewFormActivity extends AppCompatActivity
        implements DatePickerDialog.OnDateSetListener, AdapterView.OnItemSelectedListener {

    public static final String EXTRA_REVIEW = "com.example.mobile.course.reviewmyplace.EXTRA_REVIEW";
    public static final String EXTRA_CURRENCY = "com.example.mobile.course.reviewmyplace.EXTRA_CURRENCY";

    static final String STATE_REVIEW = "review";
    static final String STATE_CURRENCY = "currency";

    private Review review;
    private String currency;

//    private int establishmentID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form);

        // Initialize 'empty' review
        review = new Review();

        // Retrieve establishmentID from the Intent passed to this Activity
        review.setEstablishmentID(-1);

        // Initialize picked date (with default value - current date)
        review.setReviewDate();
        int year = review.getReviewDate().get(Calendar.YEAR);
        int day = review.getReviewDate().get(Calendar.DAY_OF_MONTH);
        String monthName =
                review.getReviewDate().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        // String representation of the selected date
        String strPickedDate = day + "";
        if (day % 10 == 1 && day != 11) {
            strPickedDate += "st ";
        } else if (day % 10 == 2 && day != 12) {
            strPickedDate += "nd ";
        } else if (day % 10 == 3 && day != 13) {
            strPickedDate += "rd ";
        } else {
            strPickedDate += "th ";
        }
        strPickedDate += monthName + ", " + year;

        TextView pickedDate = findViewById(R.id.review_form_picked_date);
        pickedDate.setText(strPickedDate);

        // Import (currency) items to two spinners
        Spinner spinner = findViewById(R.id.review_form_min_currency);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currencies_array, android.R.layout.simple_spinner_dropdown_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);    // set OnItemSelectedListener

        spinner = findViewById(R.id.review_form_max_currency);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);    // set OnItemSelectedListener
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);

        // Save the input value
        savedInstanceState.putParcelable(STATE_REVIEW, review);
        savedInstanceState.putString(STATE_CURRENCY, currency);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        // Restore the previous inputs
        review = savedInstanceState.getParcelable(STATE_REVIEW);
        currency = savedInstanceState.getString(STATE_CURRENCY);

        // Date
        TextView textView = findViewById(R.id.review_form_picked_date);
        textView.setText(review.getStringDate());

        // Types of meal
        String[] types = review.getMealType().split("|");
        CheckBox checkbox;
        for (int i = 0; i < types.length; ++i) {
            if (types[i].trim().equalsIgnoreCase("breakfast")) {
                checkbox = findViewById(R.id.review_form_checkbox_breakfast);
                checkbox.setChecked(true);
            } else if (types[i].trim().equalsIgnoreCase("lunch")) {
                checkbox = findViewById(R.id.review_form_checkbox_lunch);
                checkbox.setChecked(true);
            } else if (types[i].trim().equalsIgnoreCase("dinner")) {
                checkbox = findViewById(R.id.review_form_checkbox_dinner);
                checkbox.setChecked(true);
            } else if (types[i].trim().equalsIgnoreCase("fast food")) {
                checkbox = findViewById(R.id.review_form_checkbox_fast_food);
                checkbox.setChecked(true);
            } else if (types[i].trim().equalsIgnoreCase("snack")) {
                checkbox = findViewById(R.id.review_form_checkbox_snack);
                checkbox.setChecked(true);
            } else if (types[i].trim().equalsIgnoreCase("other")) {
                checkbox = findViewById(R.id.review_form_checkbox_other);
                checkbox.setChecked(true);
            }
        }

        // Cost and currency
        EditText editText = findViewById(R.id.review_form_min_cost);
        editText.setText(Float.toString(review.getMinCost()));

        editText = findViewById(R.id.review_form_max_cost);
        editText.setText(Float.toString(review.getMaxCost()));

        Spinner minSpinner = findViewById(R.id.review_form_min_currency);
        Spinner maxSpinner = findViewById(R.id.review_form_max_currency);
        if (currency.equalsIgnoreCase("dollar")) {
            minSpinner.setSelection(0);
            maxSpinner.setSelection(0);
        } else if (currency.equalsIgnoreCase("euro")) {
            minSpinner.setSelection(1);
            maxSpinner.setSelection(1);
        } else if (currency.equalsIgnoreCase("pound")) {
            minSpinner.setSelection(2);
            maxSpinner.setSelection(2);
        } else if (currency.equalsIgnoreCase("vnd")) {
            minSpinner.setSelection(3);
            maxSpinner.setSelection(3);
        }

        // Ratings
        RatingBar ratingBar = findViewById(R.id.review_form_service_rating_bar);
        ratingBar.setRating(review.getServiceRating());

        ratingBar = findViewById(R.id.review_form_atmosphere_rating_bar);
        ratingBar.setRating(review.getAtmosphereRating());

        ratingBar = findViewById(R.id.review_form_food_rating_bar);
        ratingBar.setRating(review.getFoodRating());

        ratingBar = findViewById(R.id.review_form_overall_rating_bar);
        ratingBar.setRating(review.getOverallRating());
    }

    public void showDatePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    public void onNextButton(View view) {
        // Extract input data for min. & max. costs
        EditText editText = findViewById(R.id.review_form_min_cost);
        if (editText.getText().toString().matches("")) {
            review.setMinCost(0.0f);
        } else {
            review.setMinCost(Float.parseFloat(editText.getText().toString()));
        }

        editText = findViewById(R.id.review_form_max_cost);
        if (editText.getText().toString().matches("")) {
            review.setMaxCost(0.0f);
        } else {
            review.setMaxCost(Float.parseFloat(editText.getText().toString()));
        }

        // Extract input data for types of meal
        extractCheckboxData();

        // Extract input data for service + atmosphere + food + overall ratings
        RatingBar ratingBar = findViewById(R.id.review_form_service_rating_bar);
        review.setServiceRating(ratingBar.getRating());

        ratingBar = findViewById(R.id.review_form_atmosphere_rating_bar);
        review.setAtmosphereRating(ratingBar.getRating());

        ratingBar = findViewById(R.id.review_form_food_rating_bar);
        review.setFoodRating(ratingBar.getRating());

        ratingBar = findViewById(R.id.review_form_overall_rating_bar);
        review.setOverallRating(ratingBar.getRating());

        // Extract selected currency
        Spinner spinner = findViewById(R.id.review_form_min_currency);
        String currency = spinner.getSelectedItem().toString();

        // Intent to start new Activity
        Intent intent = new Intent(this, ReviewFormContActivity.class);
        intent.putExtra(EXTRA_REVIEW, review);
        intent.putExtra(EXTRA_CURRENCY, currency);
        startActivity(intent);
    }

    /**
     * Extract the String representation of meal types from all CheckBox and assign
     * to variable 'mealTypes'
     */
    private void extractCheckboxData() {
        // CheckBox components
        CheckBox breakfastCheckbox = findViewById(R.id.review_form_checkbox_breakfast);
        CheckBox lunchCheckbox = findViewById(R.id.review_form_checkbox_lunch);
        CheckBox dinnerCheckbox = findViewById(R.id.review_form_checkbox_dinner);
        CheckBox fastFoodCheckbox = findViewById(R.id.review_form_checkbox_fast_food);
        CheckBox snackCheckbox = findViewById(R.id.review_form_checkbox_snack);
        CheckBox otherCheckbox = findViewById(R.id.review_form_checkbox_other);

        // Integrate all types into a string (e.g. breakfast|lunch|dinner)
        String mealTypes = "";
        if (breakfastCheckbox.isChecked()) mealTypes += breakfastCheckbox.getText().toString() + "|";
        if (lunchCheckbox.isChecked()) mealTypes += lunchCheckbox.getText().toString() + "|";
        if (dinnerCheckbox.isChecked()) mealTypes += dinnerCheckbox.getText().toString() + "|";
        if (fastFoodCheckbox.isChecked()) mealTypes += fastFoodCheckbox.getText().toString() + "|";
        if (snackCheckbox.isChecked()) mealTypes += snackCheckbox.getText().toString() + "|";
        if (otherCheckbox.isChecked()) mealTypes += otherCheckbox.getText().toString() + "|";

        // Set to "None" if no types are selected by users
        if (mealTypes.matches("")) {
            mealTypes = "None";
        } else {
            // Replace the last character (i.e. '|')
            mealTypes = mealTypes.substring(0, mealTypes.length() - 1);
        }

        review.setMealType(mealTypes);
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Extract name of selected month
        review.getReviewDate().set(year, month, day);

        String monthName =
                review.getReviewDate().getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        // String representation of the selected date
        String strPickedDate = day + "";
        if (day % 10 == 1 && day != 11) {
            strPickedDate += "st ";
        } else if (day % 10 == 2 && day != 12) {
            strPickedDate += "nd ";
        } else if (day % 10 == 3 && day != 13) {
            strPickedDate += "rd ";
        } else {
            strPickedDate += "th ";
        }
        strPickedDate += monthName + ", " + year;

        TextView pickedDate = findViewById(R.id.review_form_picked_date);
        pickedDate.setText(strPickedDate);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
        // Retrieve the other currency-spinner
        int thisId = parent.getId();
        Spinner thisSpinner = (Spinner) parent;
        Spinner otherSpinner;

        if (thisId == R.id.review_form_min_currency) {
            otherSpinner = findViewById(R.id.review_form_max_currency);
        } else if (thisId == R.id.review_form_max_currency) {
            otherSpinner = findViewById(R.id.review_form_min_currency);
        } else {
            Log.w("", "Error: invalid id in onItemSelected");
            return;
        }

        // Set the same selection for both spinners
        otherSpinner.setSelection(thisSpinner.getSelectedItemPosition());

        // Set values for currency
        currency = thisSpinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Nothing
    }
}