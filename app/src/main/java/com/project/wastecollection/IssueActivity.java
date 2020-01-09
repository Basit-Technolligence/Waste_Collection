package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class IssueActivity extends BaseActivity {
    Button accident,itemFound,vechicleProblem,tripIssue,appIsuue;
    DatabaseReference dref= FirebaseDatabase.getInstance().getReference();
    String latitude, longitude;
    FusedLocationProviderClient fusedLocationProviderClient;


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
        FirebaseUser curentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = curentUser.getUid();

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    latitude = String.valueOf(location.getLatitude());
                    longitude = String.valueOf(location.getLongitude());
                }
            }
        });
        accident.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String push = FirebaseDatabase.getInstance().getReference().child("History").push().getKey();
                dref.child("Issues").child(push).child("id").setValue(push);
                dref.child("Issues").child(push).child("longitude").setValue(longitude);
                dref.child("Issues").child(push).child("latitude").setValue(latitude);
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("issue").setValue("I got an accident.");
                dref.child("Notify/Notification").child(push).child("id").setValue(push);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We are sending help for you.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        } );

        vechicleProblem.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String push = FirebaseDatabase.getInstance().getReference().child("History").push().getKey();
                dref.child("Issues").child(push).child("id").setValue(push);
                dref.child("Issues").child(push).child("longitude").setValue(longitude);
                dref.child("Issues").child(push).child("latitude").setValue(latitude);
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("issue").setValue("I have a problem with my vehicle.");
                dref.child("Notify/Notification").child(push).child("id").setValue(push);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We are sending help for you.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();


            }
        } );
        itemFound.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String push = FirebaseDatabase.getInstance().getReference().child("History").push().getKey();
                dref.child("Issues").child(push).child("id").setValue(push);
                dref.child("Issues").child(push).child("longitude").setValue(longitude);
                dref.child("Issues").child(push).child("latitude").setValue(latitude);
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("issue").setValue("I found an item.");
                dref.child("Notify/Notification").child(push).child("id").setValue(push);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We have recieved your query we will get you soon.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        } );

        tripIssue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String push = FirebaseDatabase.getInstance().getReference().child("History").push().getKey();
                dref.child("Issues").child(push).child("id").setValue(push);
                dref.child("Issues").child(push).child("longitude").setValue(longitude);
                dref.child("Issues").child(push).child("latitude").setValue(latitude);
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("issue").setValue("I have an issue with my trip.");
                dref.child("Notify/Notification").child(push).child("id").setValue(push);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We have recieved your query we will get you soon.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        } );
        appIsuue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String push = FirebaseDatabase.getInstance().getReference().child("History").push().getKey();
                dref.child("Issues").child(push).child("id").setValue(push);
                dref.child("Issues").child(push).child("longitude").setValue(longitude);
                dref.child("Issues").child(push).child("latitude").setValue(latitude);
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("issue").setValue("I have an issue with my app.");
                dref.child("Notify/Notification").child(push).child("id").setValue(push);

                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We have recieved your query we will get you soon.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

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
