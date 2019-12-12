package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

public class ProfileActivity extends AppCompatActivity {

    TextView eName,eMobile,eAddress,eAge,vechileNo;
    Button pupdate , pback;
    String update="update";
    ImageView eImage;
    String name;
    private Uri filepath;
    FirebaseDatabase database;
    DatabaseReference ref;
    FirebaseStorage storage;
    StorageReference storageReference ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        getSupportActionBar().setTitle("Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        eImage=(ImageView)findViewById(R.id.txtImg);
        eName=(TextView) findViewById(R.id.txtName);
        eMobile=(TextView)findViewById(R.id.txtPhoneNo);
        eAge=(TextView)findViewById(R.id.txtAge);
        eAddress=(TextView)findViewById(R.id.txtAddress);
        vechileNo = (TextView)findViewById(R.id.txtVechileNo);

        pupdate=(Button) findViewById(R.id.profCONFIRM);
        pback=(Button) findViewById(R.id.back);



        database=FirebaseDatabase.getInstance();
        ref = database.getReference("Employee_Profile");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();

        final String currentuser = FirebaseAuth.getInstance().getCurrentUser().getUid();


        ref.child( currentuser ).addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    profiledata profiledata = dataSnapshot.getValue(profiledata.class);
                    if(profiledata != null) {
                        eName.setText(profiledata.getNAME());
                        eMobile.setText(profiledata.getMOBILE());
                        eAddress.setText(profiledata.getADDRESS());
                        eAge.setText(profiledata.getAge());
                        vechileNo.setText(profiledata.getVECHILENO());
                        Picasso.get().load(profiledata.getIMAGEURL()).into(eImage);


                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
        pupdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent( ProfileActivity.this , CompleteProfile.class );
                i.putExtra( "update", String.valueOf( update ) );
                startActivity( i );
            }
        });
    }
}
