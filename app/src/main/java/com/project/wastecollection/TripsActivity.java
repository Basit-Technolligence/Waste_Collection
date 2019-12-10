package com.project.wastecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class TripsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trips);

        getSupportActionBar().setTitle("Driver Trips");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
