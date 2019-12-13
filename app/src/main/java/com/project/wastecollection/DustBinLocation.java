package com.project.wastecollection;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DustBinLocation extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMarkerClickListener {
    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    String dustid, latitude, longitude;
    LocationManager locationManager;
    FusedLocationProviderClient fusedLocationProviderClient;
    Double lat2 = 0.0, lon2 = 0.0;
    DatabaseReference dref = FirebaseDatabase.getInstance().getReference();
    String city;
    Button finish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dust_bin_location);
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        supportMapFragment.getMapAsync(DustBinLocation.this);
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        final String uid = currentUser.getUid();
        if (uid != null)
            reference.child("Active").child(uid).setValue(null);
        Intent i = getIntent();
        dustid = i.getStringExtra("Id");
        latitude = i.getStringExtra("latitude");
        longitude = i.getStringExtra("longitude");

        // Toast.makeText(getApplicationContext() , latitude , Toast.LENGTH_LONG).show();

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Dustbin Found").setMessage("You want to collect it?").setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                reference.child("Dast").child(dustid).child("status").setValue("full");

                startActivity(new Intent(DustBinLocation.this,HomeActivity.class));
            }
        }).show();



        finish = (Button) findViewById(R.id.btnFinish);
        finish.setVisibility(View.GONE);

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        getLocation();

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    private void getLocation() {
        //Toast.makeText(getApplicationContext(), "we are back", Toast.LENGTH_SHORT).show();
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (location != null) {
                    lat2 = location.getLatitude();
                    lon2 = location.getLongitude();
                }
            }
        });


        int Radius = 6371;// radius of earth in Km
        final double latitude1 = Double.parseDouble(latitude);
        final double longitude1 = Double.parseDouble(longitude);

        // googleMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lon2)).title("Destination"));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.dp)));

        double dLat = Math.toRadians(lat2 - latitude1);
        double dLon = Math.toRadians(lon2 - longitude1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(latitude1))
                * Math.cos(Math.toRadians(lat2)) * Math.sin(dLon / 2)
                * Math.sin(dLon / 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        double valueResult = Radius * c;
        final double km = valueResult / 1;
        DecimalFormat newFormat = new DecimalFormat("####");
        int kmInDec = Integer.valueOf(newFormat.format(km));
        double meter = valueResult % 1000;
        int meterInDec = Integer.valueOf(newFormat.format(meter));
        Log.i("Radius Value", "" + valueResult + "   KM  " + kmInDec
                + " Meter   " + meterInDec);
        if (km <= 0.05) {
            finish.setVisibility(View.VISIBLE);
        }
        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(getApplicationContext(), String.valueOf(km), Toast.LENGTH_LONG).show();
                FirebaseUser curentUser = FirebaseAuth.getInstance().getCurrentUser();
                final String uid = curentUser.getUid();
                final String push = FirebaseDatabase.getInstance().getReference().child("History").push().getKey();
                dref.child("History").child(push).child("id").setValue(push);
                dref.child("History").child(push).child("user").setValue(uid);
                dref.child("History").child(push).child("dustbin").setValue(dustid);
                Geocoder geoCoder = new Geocoder(DustBinLocation.this, Locale.getDefault());
                StringBuilder builder = new StringBuilder();
                try {
                    List<Address> address = geoCoder.getFromLocation(latitude1, longitude1, 1);
                    int maxLines = address.get(0).getMaxAddressLineIndex();
                    for (int i=0; i<maxLines; i++) {
                        String addressStr = address.get(0).getAddressLine(i);
                        builder.append(addressStr);
                        builder.append(" ");
                    }
                    if (address.size() > 0)
                    {
                        System.out.println(address.get(0).getLocality());
                        System.out.println(address.get(0).getCountryName());
                        //Toast.makeText(getApplicationContext() , address.get(0).getAddressLine(0) , Toast.LENGTH_LONG).show();
                    }
                    String fnialAddress = builder.toString(); //This is the complete address.

                    city = address.get(0).getLocality();
                } catch (IOException e) {
                    // Handle IOException
                } catch (NullPointerException e) {
                    // Handle NullPointerException
                }
                dref.child("History").child(push).child("location").setValue(city);

                String currentDate = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(new Date());
                String currentTime = new SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(new Date());
                dref.child("History").child(push).child("date").setValue(currentDate);
                dref.child("History").child(push).child("time").setValue(currentTime);
                dref.child("Dast").child(dustid).child("status").setValue("empty");

                String uId = curentUser.getUid();
                reference.child("Active").child(uid).child("id").setValue(uId);
                reference.child("Active").child(uid).child("longitude").setValue(lon2);
                reference.child("Active").child(uid).child("latitude").setValue(lat2);
                Intent i = new Intent(DustBinLocation.this , HomeActivity.class);
                finish();
                startActivity(i);
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);


        LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        googleMap.addMarker(new MarkerOptions().position(latLng)).setIcon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.dp)));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 18), 4000, null);


        googleMap.setOnMarkerClickListener(this);
        googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


}
