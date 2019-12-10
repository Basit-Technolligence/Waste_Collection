package com.project.wastecollection;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class IssueActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issue);

        getSupportActionBar().setTitle("Report Issues");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
