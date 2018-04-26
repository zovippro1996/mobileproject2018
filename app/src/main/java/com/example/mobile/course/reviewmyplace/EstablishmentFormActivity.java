package com.example.mobile.course.reviewmyplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EstablishmentFormActivity extends AppCompatActivity {

    //Init Component in Activity
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


    protected boolean validateAllFields(){
        //GetValue of user input
        String UserID = editText_UserID.getText().toString();
        String NameEstablishment = editText_EstablishmentName.getText().toString();
        String TypeEstablishment = getResources().getStringArray(R.array.establishment_value)
                [spinner_EstablishmentType.getSelectedItemPosition()];
        String FoodType = editText_FoodType.getText().toString();
        String Location = editText_Location.getText().toString();

//        Check If required field is empty
        if (
                (TextUtils.isEmpty(UserID))
                &&(TextUtils.isEmpty(NameEstablishment))
                &&(TextUtils.isEmpty(TypeEstablishment)))
        {
            return false;
        }

        final String REGEX = "[\\w+]";
        Pattern pattern = Pattern.compile(REGEX);
        //Validate String Pattern for UserID
        Matcher m = pattern.matcher(UserID);

        if (UserID.matches(REGEX)){
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
        String UserID = editText_UserID.getText().toString();
        String EstablishmentName = editText_EstablishmentName.getText().toString();
        String EstablishmentType = getResources().getStringArray(R.array.establishment_value)
                [spinner_EstablishmentType.getSelectedItemPosition()];
        String FoodType = editText_FoodType.getText().toString();
        String Location = editText_Location.getText().toString();

//        Move to other activities if pass all validation
        if (validateAllFields()){
            Intent intent = new Intent(this, EstablishmentConfirmationActivity.class);
            intent.putExtra("UserID", UserID);
            intent.putExtra("EstablishmentName", EstablishmentName);
            intent.putExtra("EstablishmentType", EstablishmentType);
            intent.putExtra("FoodType", FoodType);
            intent.putExtra("Location", Location);
            startActivity(intent);
        }
    }
}
