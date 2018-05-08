package com.example.mobile.course.reviewmyplace;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class IntroActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
    }

    /**
     * Redirect user to EstablishmentFormActivity to
     * starting creating new establishment
     */
    public void moveToEstablishmentForm(View view) {
        // Start EstablishmentFormActivity
        Intent intent = new Intent(this, EstablishmentFormActivity.class);
        startActivity(intent);
    }

    /**
     * Redirect user to DashboardActivity
     */
    public void moveToDashboard(View view) {
        // Start DashboardActivity
        Intent intent = new Intent(this, DashboardActivity.class);
        startActivity(intent);
        finish();
    }
}
