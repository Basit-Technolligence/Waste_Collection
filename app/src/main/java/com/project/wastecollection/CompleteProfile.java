package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class CompleteProfile extends AppCompatActivity {
    EditText eName,eMobile,eEmail,eAddress,eAge,vechileNo;
    private static final int pick_image = 2;
    Button eConfirm , pback;
//    Spinner spinner;
    ImageView pimage;
    private Uri filepath;
    FirebaseDatabase database;
    String name;
    String check = "update";
    DatabaseReference ref;
    FirebaseStorage storage;
    ProgressDialog pd;
    StorageReference storageReference ;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_complete_profile );

        eName=(EditText)findViewById(R.id.profnames);
        eMobile=(EditText)findViewById(R.id.profmobile);
        eAddress=(EditText)findViewById(R.id.profaddress);
        eAge=(EditText)findViewById(R.id.profage);
        vechileNo=(EditText)findViewById(R.id.vechileNo);
        eConfirm=(Button) findViewById(R.id.profCONFIRM);
        pd=new ProgressDialog(this);
        pd.setMessage("Logging In..... ");

//        spinner=(Spinner) findViewById(R.id.dropdown);
//        final String[] items=new String[]{"1","2","3","4","5","6","7","8"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,android.R.layout.simple_spinner_dropdown_item,items);
//        spinner.setAdapter(adapter);

//        Intent in =getIntent();


        Intent in = getIntent();
        final String update = in.getStringExtra( "update" );
        name = in.getStringExtra( "name" );
//        eName.setText( String.valueOf(  " ") );

        pimage=(ImageView)findViewById(R.id.pimage);
        pimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent=new Intent();
//                intent.setType("images/*");
                Intent intent = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 2);
            }
        });


        database=FirebaseDatabase.getInstance();
        ref = database.getReference("Employee_Profile");
        storage=FirebaseStorage.getInstance();
        storageReference=storage.getReference();
        mAuth = FirebaseAuth.getInstance(  );

//        FirebaseUser currentuser = FirebaseAuth.getInstance().getCurrentUser();
        final String id=name;
        eConfirm.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pd.setMessage("Profile Data Saving....");
                pd.show();
                if (!eName.getText().toString().isEmpty() && !eMobile.getText().toString().isEmpty()&& !eAddress.getText().toString().isEmpty()&& !vechileNo.getText().toString().isEmpty()&& !eAge.getText().toString().isEmpty() && !filepath.getPath().isEmpty() )
                {

                    final String push = FirebaseDatabase.getInstance().getReference().child("Packages").push().getKey();
                    StorageReference fileReference  = storageReference.child("images/"+ push);
                    fileReference.putFile(filepath)
                            .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                    if(filepath!=null) {
                                        profiledata profiledata = new profiledata();
                                        profiledata.setID(id);
                                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                                        while (!uriTask.isSuccessful());
                                        Uri downloadUri = uriTask.getResult();
                                        profiledata.setIMAGEURL(downloadUri.toString());
                                        profiledata.setNAME(eName.getText().toString());
                                        profiledata.setMOBILE(eMobile.getText().toString());
                                        profiledata.setADDRESS(eAddress.getText().toString());
                                        profiledata.setAge(eAge.getText().toString());
                                        profiledata.setVECHILENO(vechileNo.getText().toString());

                                        ref.child(id)
                                                .setValue(profiledata);
                                        pd.dismiss();
                                        Toast.makeText(CompleteProfile.this,"profile successfully saved..",Toast.LENGTH_LONG).show();
                                       if(check.equals( update)) {
                                           Intent in = new Intent( CompleteProfile.this, ProfileActivity.class );
                                           in.putExtra( "name", String.valueOf( name ) );
                                           startActivity(in);
                                           finish();
                                       }
                                       else {
                                           Intent in = new Intent( CompleteProfile.this, LoginActivity.class );
                                           in.putExtra( "name", String.valueOf( name ) );
                                           startActivity(in);
                                           finish();

                                       }
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(getApplicationContext(), "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(getApplicationContext(), "Please enter all Information", Toast.LENGTH_SHORT).show();
                }

            }
        } );

        ref.child( id ).addListenerForSingleValueEvent( new ValueEventListener() {
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
                        if(profiledata.getIMAGEURL().equals( " " )) {
                 //           Picasso.get().load( profiledata.getIMAGEURL() ).into( pimage );
                        }
                        else {
                            Picasso.get().load( profiledata.getIMAGEURL() ).into( pimage );
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );
    }

    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==requestCode&&resultCode==resultCode
                &&data!=null && data.getData()!=null){

            filepath=data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filepath);
                pimage.setImageBitmap(bitmap);


            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent in = new Intent( CompleteProfile.this, HomeActivity.class );
        in.putExtra( "name", String.valueOf( name ) );
        startActivity(in);
        finish();
    }

}
