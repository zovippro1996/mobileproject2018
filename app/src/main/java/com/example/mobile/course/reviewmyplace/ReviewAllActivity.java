package com.example.mobile.course.reviewmyplace;

import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.example.mobile.course.reviewmyplace.helper.DatabaseHelper;
import com.example.mobile.course.reviewmyplace.object.Review;

import java.util.ArrayList;

public class ReviewAllActivity extends AppCompatActivity {

    String str_establishmentID = "";
    private DatabaseHelper databaseHelper;

    ListView listView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_all);

        // Get ready to access the database later
        databaseHelper = new DatabaseHelper(this);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        str_establishmentID = intent.getStringExtra("str_establishmentID");

        populateListView();
    }

    //Generate ListView when the Activity Start
    private void populateListView() {
//        Cursor cursor = databaseHelper.getAllReviewRecordsfromEstId(str_establishmentID);
        Cursor cursor = databaseHelper.getAllReviewRecordsOrderByDate();
        String[] fromField = new String[] {databaseHelper.COL_DATE};

        int[] toViewID = new int[]{R.id.textView_reviewDate};

        SimpleCursorAdapter simpleCursorAdapter;

        simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout
                .revew_all_itemlayout, cursor, fromField, toViewID, 0);

        listView = (ListView)findViewById(R.id.listView);
        listView.setAdapter(simpleCursorAdapter);
    }

    //Click Event for Create new review
    public void createReview(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, ReviewFormActivity.class);
        intent.putExtra("str_establishmentID", str_establishmentID);
//        startActivity(intent);
        intent.putExtra(ReviewFormActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(ReviewFormActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
