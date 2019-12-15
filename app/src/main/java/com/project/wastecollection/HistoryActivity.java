package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HistoryActivity extends BaseActivity{

    RecyclerView recyclerView;
    ArrayList<String> dustbinName=new ArrayList<>();
    ArrayList<String> date=new ArrayList<>();
    ArrayList<String> time=new ArrayList<>();
    ArrayList<String> location=new ArrayList<>();
    ArrayList<String> latitude=new ArrayList<>();
    ArrayList<String> longitude=new ArrayList<>();
    DatabaseReference database=FirebaseDatabase.getInstance().getReference("History");
    String checkUser;

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,HomeActivity.class));
        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setTitle("History");
        //setContentView(R.layout.activity_history);
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
         recyclerView.setLayoutManager(new LinearLayoutManager(this));

        FirebaseUser currentUser= FirebaseAuth.getInstance().getCurrentUser();
        final String uid=currentUser.getUid();
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot ds_history:dataSnapshot.getChildren())
                if(ds_history.exists()){
                    checkUser=ds_history.child("user").getValue().toString();
                    if(checkUser.equals(uid)){
                      dustbinName.add(ds_history.child("dustbin").getValue().toString());
                      date.add(ds_history.child("date").getValue().toString());
                      time.add(ds_history.child("time").getValue().toString());
                      location.add(ds_history.child("location").getValue().toString());
                    }
                }
                else{
                    //
                }

                recyclerView.setAdapter(new HistoryAdapter(dustbinName,date,location,time));

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });


    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_history;
    }
}
