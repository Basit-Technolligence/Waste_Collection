package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IssueActivity extends BaseActivity {
    Button accident,itemFound,vechicleProblem,tripIssue,appIsuue;
    String i1,i2,i3,i4,i5;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_issue);

        getSupportActionBar().setTitle("Report Issues");
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        accident = (Button)findViewById( R.id.issue1 );
        itemFound = (Button)findViewById( R.id.issue2 );
        vechicleProblem = (Button)findViewById( R.id.issue3 );
        tripIssue = (Button)findViewById( R.id.issue4 );
        appIsuue = (Button)findViewById( R.id.issue5 );

        accident.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dref.addValueEventListener( new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                } );

            }
        } );






    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_issue;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }
}
