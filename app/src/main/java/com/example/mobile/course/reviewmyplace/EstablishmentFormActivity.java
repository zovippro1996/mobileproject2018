package com.example.mobile.course.reviewmyplace;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.regex.Pattern;

public class EstablishmentFormActivity extends AppCompatActivity {

    //Init Component in Activity
    EditText editText_UserID = (EditText)findViewById(R.id.editText_UserID);
    EditText editText_NameEstablishment = (EditText) findViewById(R.id.editText_NameEstablishment);
    Spinner spinner_TypeEstablishment = (Spinner) findViewById(R.id.spinner_TypeEstablishment);
    EditText editText_TypeFood = (EditText) findViewById(R.id.editText_TypeFood);
    EditText editText_Location = (EditText) findViewById(R.id.editText_Location);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.establishment_form_activity);


        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.establishment_type, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        spinner_TypeEstablishment.setAdapter(adapter);
    }


    protected boolean validateAllFields(){
        //GetValue of user input
        String UserID = editText_UserID.getText().toString();
        String NameEstablishment = editText_NameEstablishment.getText().toString();
        String TypeEstablishment = spinner_TypeEstablishment.getSelectedItem().toString();
        String TypeFood = editText_TypeFood.getText().toString();
        String Location = editText_Location.getText().toString();

        //Check If required field is empty
        if (
                (TextUtils.isEmpty(UserID))
                &&(TextUtils.isEmpty(NameEstablishment))
                &&(TextUtils.isEmpty(TypeEstablishment)))
        {
            return false;
        }

        //String Pattern
        final String REGEX = "\\d";

        //Validate String Pattern for UserID
        boolean isValidRegex_UserID = Pattern.matches(REGEX, UserID);
        if (!isValidRegex_UserID){
            return false;
        }

        return true;
    }

    /***
     * Activate when User Click Confirm Button
     * Validate First, then transfer value to confirmation activity
     */
    protected void clickConfirm_Button(){

        //GetValue of user input
        String UserID = editText_UserID.getText().toString();
        String NameEstablishment = editText_NameEstablishment.getText().toString();
        String TypeEstablishment = spinner_TypeEstablishment.getSelectedItem().toString();
        String TypeFood = editText_TypeFood.getText().toString();
        String Location = editText_Location.getText().toString();

        if (validateAllFields()){


        }

    }
}
