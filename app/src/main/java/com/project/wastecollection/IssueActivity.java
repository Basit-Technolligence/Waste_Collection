package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class IssueActivity extends BaseActivity {
    Button accident,itemFound,vechicleProblem,tripIssue,appIsuue;
    String i1,i2,i3,i4,i5;
    String latitude, longitude,uid,phoneno,userName;

    FusedLocationProviderClient fusedLocationProviderClient;

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

        Intent in =getIntent();
        uid = in.getStringExtra( "name" );

        dref.child( "Employee_Profile" ).child( uid ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                phoneno = dataSnapshot.child( "mobile" ).getValue().toString();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
//        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(Location location) {
//                if (location != null) {
//                    latitude = String.valueOf(location.getLatitude());
//                    longitude = String.valueOf(location.getLongitude());
//                }
//            }
//        });

        accident.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final    String phoneNo=phoneno;
                final String push = FirebaseDatabase.getInstance().getReference().child("Issues").push().getKey();
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("PhoneNo").setValue(phoneNo);
                dref.child("Issues").child(push).child("issue").setValue("I got an accident.");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                dref.child("Issues").child(push).child("date").setValue(currentDate);
                dref.child("Issues").child(push).child("time").setValue(currentTime);




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
                final    String phoneNo=phoneno;
                final String push = FirebaseDatabase.getInstance().getReference().child("Issues").push().getKey();
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("PhoneNo").setValue(phoneNo);
                dref.child("Issues").child(push).child("issue").setValue("I have a Problem With my Vehicle.");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                dref.child("Issues").child(push).child("date").setValue(currentDate);
                dref.child("Issues").child(push).child("time").setValue(currentTime);




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
                final    String phoneNo=phoneno;
                final String push = FirebaseDatabase.getInstance().getReference().child("Issues").push().getKey();
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("PhoneNo").setValue(phoneNo);
                dref.child("Issues").child(push).child("issue").setValue("I have an issue regarding Map route.");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                dref.child("Issues").child(push).child("date").setValue(currentDate);
                dref.child("Issues").child(push).child("time").setValue(currentTime);




                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We are sending help for you.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        } );

        tripIssue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final    String phoneNo=phoneno;
                final String push = FirebaseDatabase.getInstance().getReference().child("Issues").push().getKey();
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("PhoneNo").setValue(phoneNo);
                dref.child("Issues").child(push).child("issue").setValue("I have found an item.");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                dref.child("Issues").child(push).child("date").setValue(currentDate);
                dref.child("Issues").child(push).child("time").setValue(currentTime);




                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We are sending help for you.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).show();

            }
        } );
        appIsuue.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final    String phoneNo=phoneno;
                final String push = FirebaseDatabase.getInstance().getReference().child("Issues").push().getKey();
                dref.child("Issues").child(push).child("driverid").setValue(uid);
                dref.child("Issues").child(push).child("PhoneNo").setValue(phoneNo);
                dref.child("Issues").child(push).child("issue").setValue("I have an issue with my app.");
                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                dref.child("Issues").child(push).child("date").setValue(currentDate);
                dref.child("Issues").child(push).child("time").setValue(currentTime);




                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(IssueActivity.this);
                alertDialogBuilder.setTitle("Issue Submitted").setMessage("We are trying to resolve your issue.").setPositiveButton("OK", new DialogInterface.OnClickListener() {
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
        Intent in = new Intent( IssueActivity.this, HomeActivity.class );
        in.putExtra( "name", String.valueOf( uid ) );
        startActivity(in);
        finish();
    }
}
