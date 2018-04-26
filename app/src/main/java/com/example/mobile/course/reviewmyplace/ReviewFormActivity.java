package com.example.mobile.course.reviewmyplace;

import android.app.DatePickerDialog;
import android.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.helper.DatePickerFragment;

import java.util.Calendar;
import java.util.Locale;
import java.util.Map;

public class ReviewFormActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_form);

        // Initialize picked date (with default value - current date)
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        String monthName =
                calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        // String representation of the selected date
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

        TextView pickedDate = (TextView) findViewById(R.id.review_form_picked_date);
        pickedDate.setText(strDate);
    }

    public void showDatePickerDialog(View view) {
        DialogFragment dialogFragment = new DatePickerFragment();
        dialogFragment.show(getFragmentManager(), "datePicker");
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int day) {
        // Extract name of selected month
        Calendar calendar = Calendar.getInstance();
        calendar.set(year, month, day);

        String monthName = calendar.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault());

        // String representation of the selected date
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

        TextView pickedDate = (TextView) findViewById(R.id.review_form_picked_date);
        pickedDate.setText(strDate);
    }
}
