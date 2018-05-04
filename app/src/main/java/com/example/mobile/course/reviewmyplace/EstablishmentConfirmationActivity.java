package com.example.mobile.course.reviewmyplace;

import android.app.TaskStackBuilder;
import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class EstablishmentConfirmationActivity extends AppCompatActivity {

    TextView textview_UserID = null;
    TextView textView_EstablishmentName = null;
    TextView textView_EstablishmentType = null;
    TextView textView_FoodType = null;
    TextView textView_Location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_confirmation);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();

//        // Get Value from Establishment Form
        String UserID = intent.getStringExtra("UserID");
        String EstablishmentName = intent.getStringExtra("EstablishmentName");
        String EstablishmentType = getResources().getStringArray(R.array.establishment_value)
                [Integer.parseInt(intent.getStringExtra("EstablishmentType"))];

        String FoodType = intent.getStringExtra("FoodType");
        String Location = intent.getStringExtra("Location");

        // Set Text for Text View Based on Establishment Form
        textview_UserID = (TextView) findViewById(R.id.textview_UserID);
        textView_EstablishmentName = (TextView) findViewById(R.id.textView_EstablishmentName);
        textView_EstablishmentType = (TextView) findViewById(R.id.textView_EstablishmentType);
        textView_FoodType = (TextView) findViewById(R.id.textView_FoodType);
        textView_Location = (TextView) findViewById(R.id.textView_Location);

        textview_UserID.setText(UserID);
        textView_EstablishmentName.setText(EstablishmentName);
        String EstType_Label = getResources().getStringArray(R.array.establishment_label)[Integer
                .parseInt(EstablishmentType)];
        textView_EstablishmentType.setText(EstType_Label);
        textView_FoodType.setText(FoodType);
        textView_Location.setText(Location);


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


        Intent intent = new Intent(this, EstablishmentDetailActivity.class);
        startActivity(intent);

    }
}
