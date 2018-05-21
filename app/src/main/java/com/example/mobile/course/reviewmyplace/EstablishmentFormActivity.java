package com.example.mobile.course.reviewmyplace;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.mobile.course.reviewmyplace.object.Establishment;
import com.example.mobile.course.reviewmyplace.object.EstablishmentType;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EstablishmentFormActivity extends AppCompatActivity {

    //Initialize Component in Activity
    EditText editText_UserID = null;
    EditText editText_EstablishmentName = null;
    Spinner spinner_EstablishmentType = null;
    EditText editText_FoodType = null;
    EditText editText_Location = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_establishment_form);

        editText_UserID = (EditText) findViewById(R.id.editText_UserID);
        editText_EstablishmentName = (EditText) findViewById(R.id
                .editText_EstablishmentName);
        spinner_EstablishmentType = (Spinner) findViewById(R.id.spinner_EstablishmentType);
        editText_FoodType = (EditText) findViewById(R.id.editText_FoodType);
        editText_Location = (EditText) findViewById(R.id.editText_Location);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.establishment_label, R.layout.spinner_item_estform);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_EstablishmentType.setAdapter(adapter);
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        //GetValue of user input
        String UserID = editText_UserID.getText().toString();
        String EstablishmentName = editText_EstablishmentName.getText().toString();
        String FoodType = editText_FoodType.getText().toString();
        String Location = editText_Location.getText().toString();

        // Save the input value
        savedInstanceState.putString("UserID", UserID);
        savedInstanceState.putString("EstablishmentName", EstablishmentName);
        savedInstanceState.putString("FoodType", FoodType);
        savedInstanceState.putString("Location", Location);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        //GetValue of user input
        String UserID = savedInstanceState.getString("UserID");
        String EstablishmentName = savedInstanceState.getString("EstablishmentName");
        String FoodType = savedInstanceState.getString("FoodType");
        String Location = savedInstanceState.getString("Location");


        editText_UserID.setText(UserID);
        editText_EstablishmentName.setText(EstablishmentName);
        editText_FoodType.setText(FoodType);
        editText_Location.setText(Location);
    }

    protected boolean validateAllFields(){
        //GetValue of user input
        String UserID = editText_UserID.getText().toString();
        String EstablishmentName = editText_EstablishmentName.getText().toString();
        String EstablishmentType = getResources().getStringArray(R.array.establishment_value)
                [spinner_EstablishmentType.getSelectedItemPosition()];
        String FoodType = editText_FoodType.getText().toString();
        String Location = editText_Location.getText().toString();

        // Check if UserID correct form
        final String REGEX = "(\\w+)";
        Pattern pattern = Pattern.compile(REGEX);
        //Validate String Pattern for UserID
        Matcher m = pattern.matcher(UserID);

//      Check If required field is empty
        if(TextUtils.isEmpty(UserID)) {
            editText_UserID = (EditText) findViewById(R.id.editText_UserID);
            editText_UserID.setError("This is a Required Field");
            editText_UserID.requestFocus();
            return false;
        }else if (!UserID.matches(REGEX)){
            editText_UserID = (EditText) findViewById(R.id.editText_UserID);
            editText_UserID.setError("Only Allow Alphebetic, Numeric and Underscope");
            return false;
        } else if (TextUtils.isEmpty(EstablishmentName)){
            editText_EstablishmentName = (EditText) findViewById(R.id.editText_EstablishmentName);
            editText_EstablishmentName.setError("This is a Required Field");
            editText_EstablishmentName.requestFocus();
            return false;
        } else if((TextUtils.isEmpty(EstablishmentType)) || (EstablishmentType.equals("-1"))) {
            TextView errorText = (TextView)spinner_EstablishmentType.getSelectedView();
            errorText.setError("anything here, just to add the icon");
            errorText.setTextColor(Color.RED);//just to highlight that this is an error
            errorText.setText("This is a Required Field");//changes the selected item text to this
            return false;
        }
        return true;
    }

    /***
     * Activate when User Click Confirm Button
     * Validate First, then transfer value to confirmation activity
     */
    public void clickConfirm_Button(View view) {

        //GetValue of user input
        String userID = editText_UserID.getText().toString();
        String establishmentName = editText_EstablishmentName.getText().toString();
        String establishmentType = getResources().getStringArray(R.array.establishment_value)
                [spinner_EstablishmentType.getSelectedItemPosition()];

        String foodType = editText_FoodType.getText().toString();
        String Location = editText_Location.getText().toString();


//        Move to other activities if pass all validation
        if (validateAllFields()){

            Intent intent = new Intent(this, EstablishmentConfirmationActivity.class);
            intent.putExtra("UserID", userID);
            intent.putExtra("EstablishmentName", establishmentName);
            intent.putExtra("EstablishmentType", establishmentType);
            intent.putExtra("FoodType", foodType);
            intent.putExtra("Location", Location);
            startActivity(intent);
        }
    }
}
