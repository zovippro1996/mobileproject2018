package com.example.mobile.course.reviewmyplace;

import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.NavUtils;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.course.reviewmyplace.helper.DatabaseHelper;
import com.example.mobile.course.reviewmyplace.helper.FragmentDetail;
import com.example.mobile.course.reviewmyplace.helper.FragmentReview;
import com.example.mobile.course.reviewmyplace.object.Establishment;
import com.example.mobile.course.reviewmyplace.object.EstablishmentType;
import com.example.mobile.course.reviewmyplace.object.Review;
import com.facebook.CallbackManager;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EstablishmentDetailActivity extends AppCompatActivity {

    //Initialize component use in layout
    TextView textView_EstablishmentName = null;
    TextView textView_Location = null;
    ImageView imageView = null;

    String str_establishmentID = "";
    String userID = "";
    String establishmentName = "";
    int establishmentType_num = -1;
    String establishmentType = "NONE";
    String foodType = "";
    String Location = "";
    EstablishmentType current_establishmenttype ;

    FloatingActionButton fab_createReview = null;

    Bundle bundle = new Bundle();

    ShareButton shareButton;
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        str_establishmentID = intent.getStringExtra(EstablishmentConfirmationActivity.EXTRA_ESTABLISHMENT_ID);

        bundle.putString("establishmentID", str_establishmentID);

        // Get ready to access the database later
        databaseHelper = new DatabaseHelper(this);

        //Select Column
        String[] tableColumns = new String[]{
                "_id", "establishment_name", "establishment_type", "food",
                "location_description", "user_id"
        };

        String[] whereArgs = new String[]{
                str_establishmentID
        };

        Cursor current_establishmentCursor = databaseHelper.getReadableDatabase().query
                ("establishments", tableColumns, "_id = ?", whereArgs, null,
                        null, null, "1");

        setContentView(R.layout.activity_establishment_detail);

        if (current_establishmentCursor.moveToFirst()) {
            establishmentName = current_establishmentCursor.getString
                    (current_establishmentCursor
                            .getColumnIndex("establishment_name"));

            establishmentType = current_establishmentCursor.getString
                    (current_establishmentCursor
                            .getColumnIndex("establishment_type"));
            current_establishmenttype = EstablishmentType.valueOf
                    (establishmentType);

            userID = current_establishmentCursor.getString
                    (current_establishmentCursor
                            .getColumnIndex("user_id"));

            foodType = current_establishmentCursor.getString
                    (current_establishmentCursor
                            .getColumnIndex("food"));

            Location = current_establishmentCursor.getString
                    (current_establishmentCursor
                            .getColumnIndex("location_description"));
        }
        current_establishmentCursor.close();

        //Add Establishment to Bundle
        Establishment current_establishment = new Establishment(userID, establishmentName,
                current_establishmenttype, foodType, Location);
        bundle.putParcelable("establishment", current_establishment);

        //Add Review_number to Bundle
        long review_number = databaseHelper.getNumberOfReviewRecordsFromEstablishment(str_establishmentID);
        bundle.putLong("review_number", review_number);

        if(review_number > 0){
            Cursor latest_review_cursor = databaseHelper.getAllReviewRecordsOrderByDate_latest(str_establishmentID);
            latest_review_cursor.moveToFirst();

            Calendar review_caldate = Calendar.getInstance();
            review_caldate.setTimeInMillis(latest_review_cursor.getLong(latest_review_cursor.getColumnIndex
                    ("date")));
            float rating = latest_review_cursor.getFloat(latest_review_cursor.getColumnIndex
                    ("overall_rating"));
            String comment = latest_review_cursor.getString(latest_review_cursor.getColumnIndex(
                    (databaseHelper.COL_COMMENT)));
            Review review = new Review(review_caldate, rating, comment);

            bundle.putParcelable("review", review);

            if (review_number>1){
                latest_review_cursor.moveToNext();
                Calendar review_caldate_2 = Calendar.getInstance();
                review_caldate_2.setTimeInMillis(latest_review_cursor.getLong(latest_review_cursor
                        .getColumnIndex
                        ("date")));
                float rating_2 = latest_review_cursor.getFloat(latest_review_cursor.getColumnIndex
                        ("overall_rating"));

                String comment_2 = latest_review_cursor.getString(latest_review_cursor
                        .getColumnIndex(
                        (databaseHelper.COL_COMMENT)));

                Review review_2 = new Review(review_caldate_2, rating_2, comment_2);
                bundle.putParcelable("review_2", review_2);
            }
        }


        imageView = (ImageView) findViewById(R.id.imageView);
        switch (establishmentType) {
            case "RESTAURANT":
                imageView.setImageResource(R.drawable.default_restaurant);
                break;
            case "BAR":
                imageView.setImageResource(R.drawable.default_bar);
                break;
            case "COFFEE_SHOP":
                imageView.setImageResource(R.drawable.default_coffeeshop);
                break;
            default:
                imageView.setImageResource(R.drawable.default_restaurant);
        }

        textView_EstablishmentName = (TextView) findViewById(R.id.textView_EstablishmentName);
        textView_EstablishmentName.setText(establishmentName);
        textView_Location = (TextView) findViewById(R.id.textView_Location);
        textView_Location.setText(Location);

        fab_createReview = (FloatingActionButton)findViewById(R.id.fab_createReview);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager_1);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        // Add Fragments to adapter one by one
        adapter.addFragment(new FragmentReview(), "REVIEW");
        adapter.addFragment(new FragmentDetail(), "DETAIL");
        viewPager.setAdapter(adapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout_main);
        tabLayout.setupWithViewPager(viewPager);

        ActionBar actionBar = getSupportActionBar();
        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);
        }

    }



    //Navigate Up to PARENT
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                // set the new task and clear flags
                upIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(upIntent);
                return true;
            case R.id.action_establishment_detail_delete:
                onDeleteOptionItem();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //BACK TO PARENT
    @Override
    public void onBackPressed() {
        Intent upIntent = NavUtils.getParentActivityIntent(this);
        // set the new task and clear flags
        upIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(upIntent);
    }


    //Change Between Tabs
    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            fragment.setArguments(bundle);
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }

    //OnClick Method for Show All Reviews
    public void showAllReview(View view) {
        Intent intent = new Intent(this, ReviewAllActivity.class);
        intent.putExtra(EstablishmentConfirmationActivity.EXTRA_ESTABLISHMENT_ID, str_establishmentID);

        startActivity(intent);
    }

    //OnClick Method for Create New Review
    public void createReview(View view) {
        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(this, view, "transition");
        int revealX = (int) (view.getX() + view.getWidth() / 2);
        int revealY = (int) (view.getY() + view.getHeight() / 2);

        Intent intent = new Intent(this, ReviewFormActivity.class);
        intent.putExtra(EstablishmentConfirmationActivity.EXTRA_ESTABLISHMENT_ID, str_establishmentID);
//        startActivity(intent);
        intent.putExtra(ReviewFormActivity.EXTRA_CIRCULAR_REVEAL_X, revealX);
        intent.putExtra(ReviewFormActivity.EXTRA_CIRCULAR_REVEAL_Y, revealY);

        ActivityCompat.startActivity(this, intent, options.toBundle());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the option menu from XML
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_establishment_detail, menu);

        return true;
    }

    private void onDeleteOptionItem() {
        // Pop-up dialog asking for confirmation
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.action_delete_message);
        builder.setPositiveButton(R.string.action_delete_positive_button, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                deleteEstablishment();      // delete the current Establishment record
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

    private void deleteEstablishment() {
        try {
            // Delete record in database
            databaseHelper.deleteEstablishmentRecord(Integer.parseInt(str_establishmentID));

            // Notification
            popupToast("Delete establishment successfully");

            Intent upIntent = NavUtils.getParentActivityIntent(this);
            // set the new task and clear flags
            upIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(upIntent);

            // TODO: remove Activity instance on stack
        } catch (SQLiteException sqle) {
            Log.w(this.getClass().getName(), "Error saving to database");

            // Notify unsuccessful saving
            popupToast("Couldn't delete establishment");
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
