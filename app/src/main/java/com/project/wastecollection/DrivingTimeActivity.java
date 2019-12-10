package com.project.wastecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class DrivingTimeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driving_time);

        getSupportActionBar().setTitle("Driving Time ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
