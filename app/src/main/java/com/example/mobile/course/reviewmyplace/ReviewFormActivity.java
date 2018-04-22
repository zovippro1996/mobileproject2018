package com.example.mobile.course.reviewmyplace;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.object.DatePickerFragment;

import java.util.Calendar;

public class ReviewFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form);

        // Initialize picked date (with default value - current date)
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        String strDate = day + "/" + (month + 1) + "/" + year;

        TextView pickedDate = (TextView) findViewById(R.id.review_form_picked_date);
        pickedDate.setText(strDate);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Set the value of 'picked date' TextView into the selected date
        String strDate = day + "/" + (month + 1) + "/" + year;

        TextView pickedDate = (TextView) findViewById(R.id.review_form_picked_date);
        pickedDate.setText(strDate);
    }
}
