package com.example.mobile.course.reviewmyplace;

import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.mobile.course.reviewmyplace.helper.DatabaseHelper;

public class ReviewAllActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_all);

        // Get ready to access the database later
        databaseHelper = new DatabaseHelper(this);
    }

    private void populateListView() {
        Cursor cursor = databaseHelper.getAllReviewRecordsfromEstId("13");
    }
}
