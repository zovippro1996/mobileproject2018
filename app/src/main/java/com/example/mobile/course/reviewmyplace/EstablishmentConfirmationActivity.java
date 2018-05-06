package com.example.mobile.course.reviewmyplace;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteException;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mobile.course.reviewmyplace.helper.DatabaseHelper;
import com.example.mobile.course.reviewmyplace.object.Establishment;
import com.example.mobile.course.reviewmyplace.object.EstablishmentType;

public class EstablishmentConfirmationActivity extends AppCompatActivity {

    private DatabaseHelper databaseHelper;

    //Initialize Component and Variables
    TextView textView_UserID = null;
    TextView textView_EstablishmentName = null;
    TextView textView_EstablishmentType = null;
    TextView textView_FoodType = null;
    TextView textView_Location = null;

    String userID = "";
    String establishmentName = "";
    int establishmentType_num = -1;
    String establishmentType = "NONE";
    String foodType = "";
    String Location = "";
    EstablishmentType new_establishmentType;

    int new_establishmentID = 0;
    String str_establishmentID = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_confirmation);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

        // Get Value from Establishment Form
        userID = intent.getStringExtra("UserID");
        establishmentName = intent.getStringExtra("EstablishmentName");
        establishmentType = getResources().getStringArray(R.array.establishment_value)
                [Integer.parseInt(intent.getStringExtra("EstablishmentType"))];
        establishmentType_num = Integer.parseInt(intent.getStringExtra("EstablishmentType"));
        foodType = intent.getStringExtra("FoodType");
        Location = intent.getStringExtra("Location");

        // Set Text for Text View Based on Establishment Form
        textView_UserID = (TextView) findViewById(R.id.textView_UserID);
        textView_EstablishmentName = (TextView) findViewById(R.id.textView_EstablishmentName);
        textView_EstablishmentType = (TextView) findViewById(R.id.textView_EstablishmentType);
        textView_FoodType = (TextView) findViewById(R.id.textView_FoodType);
        textView_Location = (TextView) findViewById(R.id.textView_Location);

        textView_UserID.setText(userID);
        textView_EstablishmentName.setText(establishmentName);
        String EstType_Label = getResources().getStringArray(R.array.establishment_label)[Integer
                .parseInt(establishmentType)];
        textView_EstablishmentType.setText(EstType_Label);
        textView_FoodType.setText(foodType);
        textView_Location.setText(Location);

        // Get ready to access the database later
        databaseHelper = new DatabaseHelper(this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void clickConfirm_Button(View v) {

        switch(establishmentType_num){
            case -1:
                new_establishmentType = EstablishmentType.NONE;
                break;

            case 1:
                new_establishmentType = EstablishmentType.RESTAURANT;
                break;

            case 2:
                new_establishmentType = EstablishmentType.COFFEE_SHOP;
                break;

            case 3:
                new_establishmentType = EstablishmentType.BAR;
                break;

            default:
                new_establishmentType = EstablishmentType.NONE;
                break;
        }

        Establishment new_establishment = new Establishment(userID, establishmentName,
                new_establishmentType, foodType, Location);


        try {
            // Insert into database
            long result_rowID = databaseHelper.insertEstablishment(new_establishment);

            String[] tableColumns = new String[] {
                    "_id",
            };

            String result_rowID_str = Long.toString(result_rowID);
            String[] whereArgs = new String[] {
                    result_rowID_str
            };

            Cursor new_establishmentID_cursor = databaseHelper.getReadableDatabase().query
                    ("establishments", tableColumns, "ROWID = ?", whereArgs, null,
                            null, null, "1" );

            if (new_establishmentID_cursor.moveToFirst()) {
                str_establishmentID = new_establishmentID_cursor.getString
                        (new_establishmentID_cursor
                        .getColumnIndex("_id"));
            }
            // Notify successful saving
            popupToast("Your review has been saved successfully");
        } catch (SQLiteException sqle) {
            Log.w(this.getClass().getName(), "Error saving to database");

            // Notify unsuccessful saving
            popupToast("Couldn't save your review into database");
        }

        Intent intent = new Intent(this, EstablishmentDetailActivity.class);
        intent.putExtra("str_establishmentID", str_establishmentID);
        startActivity(intent);
    }

    private void popupToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
