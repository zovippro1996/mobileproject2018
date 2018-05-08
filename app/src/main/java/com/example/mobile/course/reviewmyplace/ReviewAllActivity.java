package com.example.mobile.course.reviewmyplace;

import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.os.Bundle;
import android.view.View;
import android.widget.ResourceCursorAdapter;

import com.example.mobile.course.reviewmyplace.helper.DatabaseHelper;
import com.example.mobile.course.reviewmyplace.helper.ReviewCursorAdapter;

public class ReviewAllActivity extends ListActivity {

    String mStrEstablishmentID = "";
    private DatabaseHelper mDatabaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_all);

        // Get ready to access the database later
        mDatabaseHelper = new DatabaseHelper(this);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        mStrEstablishmentID = intent.getStringExtra("mStrEstablishmentID");

        populateListView();
    }

    //Generate ListView when the Activity Start
    private void populateListView() {
        // Get all review records ...
//        Cursor cursor = mDatabaseHelper.getAllReviewRecordsfromEstId(mStrEstablishmentID);
        Cursor cursor = mDatabaseHelper.getAllReviewRecordsOrderByDate();

//        String[] fromField = new String[] {mDatabaseHelper.COL_DATE};
//
//        int[] toViewID = new int[]{R.id.textView_reviewDate};
//
//        SimpleCursorAdapter simpleCursorAdapter;
//
//        simpleCursorAdapter = new SimpleCursorAdapter(getBaseContext(), R.layout
//                .list_review_record, cursor, fromField, toViewID, 0);

        // Create a list adapter to bind all data from cursor to ListView
        final ReviewCursorAdapter adapter = new ReviewCursorAdapter(
                this,
                R.layout.list_review_record,
                cursor,
                ResourceCursorAdapter.FLAG_REGISTER_CONTENT_OBSERVER
        );

        // Bind adapter to the ListView
        setListAdapter(adapter);
    }

    // Click Event for Create new review
    public void createReview(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, ReviewFormActivity.class);
        intent.putExtra("mStrEstablishmentID", mStrEstablishmentID);
//        startActivity(intent);
        intent.putExtra(ReviewFormActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(ReviewFormActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }
}
