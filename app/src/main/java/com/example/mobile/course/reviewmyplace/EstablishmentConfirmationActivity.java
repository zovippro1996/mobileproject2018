package com.example.mobile.course.reviewmyplace;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
//
//        // Set Text for Text View Based on Establishment Form
        textview_UserID.setText(UserID);
        textView_EstablishmentName.setText(EstablishmentName);
        textView_EstablishmentType.setText(EstablishmentType);
        textView_FoodType.setText(FoodType);
        textView_Location.setText(Location);


        // Capture the layout's TextView and set the string as its text


    }
}
