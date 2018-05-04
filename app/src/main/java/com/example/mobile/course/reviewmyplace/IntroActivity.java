package com.example.mobile.course.reviewmyplace;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

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
        Intent intent = new Intent(this, EstablishmentFormActivity.class);
        startActivity(intent);


    }

    /**
     * Redirect user to DashboardActivity
     */
    public void moveToDashboard(View view) {
//        Intent intent = new Intent(this, DashboardBoard.class);
//        startActivity(intent);
    }
}
