package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;

public class HomeActivity extends BaseActivity implements OnMapReadyCallback {
    private static final int REQUEST_LOCATION = 0;
    LocationManager locationManager;
    LocationListener locationListener;

    DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    Double distance1 = 1000000000000000.0;
    String driverId, lonDust, latDust, idDust, lon, lat, id;
    Integer x = 0;
    String username;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        //setContentView(R.layout.activity_home);
        // drawerLayout = (DrawerLayout) findViewById(R.id.drawarLayout);
        locationManager = (LocationManager) getSystemService( Context.LOCATION_SERVICE );

        Intent in = getIntent();
        username = in.getStringExtra( "name" );

//
//        //adding drawar button
//        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
//        drawerLayout.addDrawerListener(drawerToggle);
//        drawerToggle.syncState();
//
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//
//        //navbar item click
//        NavigationView navigationView = (NavigationView) findViewById(R.id.navigationView);
//        navigationView.setNavigationItemSelectedListener(this);
//
//        //header click navbar
//        View headerview = navigationView.getHeaderView(0);
//        imageView = (ImageView) headerview.findViewById(R.id.profile_image);

        //opening map fragment
        SupportMapFragment supportMapFragment = (SupportMapFragment)
                getSupportFragmentManager().findFragmentById( R.id.fragmentMap );
        supportMapFragment.getMapAsync( HomeActivity.this );

    }

    @Override
    protected int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        recreate();
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onMapReady(final GoogleMap googleMap) {


        locationManager = (LocationManager) getSystemService( LOCATION_SERVICE );


        locationListener = new LocationListener() {
            @RequiresApi(api = Build.VERSION_CODES.P)
            @Override
            public void onLocationChanged(final Location location) {
                googleMap.clear();
//no
                //Toast.makeText(HomeActivity.this, "location:" + location.getLatitude(), Toast.LENGTH_SHORT).show();
                //showing on map
                LatLng latLng = new LatLng( location.getLatitude(), location.getLongitude() );

//                FirebaseUser curentUser = FirebaseAuth.getInstance().getCurrentUser();
                if (logout == 0) {
                    String uid = username;
                    reference.child( "Active" ).child( uid ).child( "id" ).setValue( uid );
                    reference.child( "Active" ).child( uid ).child( "longitude" ).setValue( location.getLongitude() );
                    reference.child( "Active" ).child( uid ).child( "latitude" ).setValue( location.getLatitude() );
                    reference.child( "Offline" ).child( username ).setValue( null );

                }

                googleMap.addMarker( new MarkerOptions().position( latLng ).title( "Your location" ).icon( BitmapDescriptorFactory.fromResource( R.drawable.truckmarker ) ) );
                googleMap.animateCamera( CameraUpdateFactory.newLatLngZoom( latLng, 18 ), 4000, null );

                if (latLng != null) {
                    reference.child( "Dast" ).addValueEventListener( new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (DataSnapshot dustbin : dataSnapshot.getChildren()) {
                                    if (dataSnapshot.exists()) {
                                        if (dustbin.child( "status" ).getValue().equals( "full" )) {
                                            lonDust = dustbin.child( "longitude" ).getValue().toString();
                                            latDust = dustbin.child( "latitude" ).getValue().toString();
                                            idDust = dustbin.child( "id" ).getValue().toString();
//                                        googleMap.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(latDust), Double.parseDouble(lonDust))).title("Dustbin"));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.dp)));

                                            reference.child( "Active" ).addValueEventListener( new ValueEventListener() {
                                                @RequiresApi(api = Build.VERSION_CODES.P)
                                                @Override
                                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                    if (dataSnapshot.exists()) {
                                                        for (DataSnapshot user : dataSnapshot.getChildren()) {
                                                            if (x == 0) {
                                                                if (user.child( "longitude" ).getValue().toString() != null) {
                                                                    lon = user.child( "longitude" ).getValue().toString();

                                                                    lat = user.child( "latitude" ).getValue().toString();
                                                                    id = user.child( "id" ).getValue().toString();

                                                                    int Radius = 6371;// radius of earth in Km
                                                                    double latitude = Double.parseDouble( lonDust );
                                                                    double longitude = Double.parseDouble( latDust );
                                                                    double lat2 = Double.parseDouble( lat );
                                                                    double lon2 = Double.parseDouble( lon );
                                                                    // googleMap.addMarker(new MarkerOptions().position(new LatLng(lat2, lon2)).title("Destination"));//.icon(BitmapDescriptorFactory.fromResource(R.drawable.dp)));

                                                                    double dLat = Math.toRadians( lat2 - latitude );
                                                                    double dLon = Math.toRadians( lon2 - longitude );
                                                                    double a = Math.sin( dLat / 2 ) * Math.sin( dLat / 2 )
                                                                            + Math.cos( Math.toRadians( latitude ) )
                                                                            * Math.cos( Math.toRadians( lat2 ) ) * Math.sin( dLon / 2 )
                                                                            * Math.sin( dLon / 2 );
                                                                    double c = 2 * Math.asin( Math.sqrt( a ) );
                                                                    double valueResult = Radius * c;
                                                                    double km = valueResult / 1;
                                                                    DecimalFormat newFormat = new DecimalFormat( "####" );
                                                                    int kmInDec = Integer.valueOf( newFormat.format( km ) );
                                                                    double meter = valueResult % 1000;
                                                                    int meterInDec = Integer.valueOf( newFormat.format( meter ) );
                                                                    Log.i( "Radius Value", "" + valueResult + "   KM  " + kmInDec
                                                                            + " Meter   " + meterInDec );

                                                                    if (distance1 >= km) {
                                                                        distance1 = km;
                                                                        driverId = id;

                                                                    }
                                                                    //Toast.makeText(getApplicationContext(), String.valueOf(valueResult), Toast.LENGTH_LONG).show();


                                                                }
                                                            }
                                                        }
                                                    }
                                                    if (x == 0) {
//                                                        FirebaseUser curentUser = FirebaseAuth.getInstance().getCurrentUser();
                                                        String uid = username;
                                                        if (uid.equals( driverId )) {
//                                                            Toast.makeText(getApplicationContext(), driverId, Toast.LENGTH_LONG).show();
                                                            reference.child( "Dast" ).child( idDust ).child( "status" ).setValue( "pending" );
                                                            x = 1;
                                                            Intent i = new Intent( HomeActivity.this, DustBinLocation.class );
                                                            i.putExtra( "Id", idDust );
                                                            i.putExtra( "longitude", lonDust );
                                                            i.putExtra( "latitude", latDust );
                                                            i.putExtra( "name", userName );


                                                            startActivity( i );
                                                            finish();
                                                            locationManager.removeUpdates( locationListener );

                                                        }
                                                    }
                                                }

                                                @Override
                                                public void onCancelled(@NonNull DatabaseError
                                                                                databaseError) {

                                                }
                                            } );

                                        }
                                    }

                                }


                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    } );
                }

            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {
                //  Toast.makeText(HomeActivity.this, "status change", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderEnabled(String provider) {
                //   Toast.makeText(HomeActivity.this, "Permission", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onProviderDisabled(String provider) {
                Intent i = new Intent( Settings.ACTION_LOCATION_SOURCE_SETTINGS );
                startActivity( i );
                finish();

            }
        }

        ;
        if (ActivityCompat.checkSelfPermission( HomeActivity.this, Manifest.permission.ACCESS_FINE_LOCATION ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission( HomeActivity.this, Manifest.permission.ACCESS_COARSE_LOCATION ) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions( HomeActivity.this, new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION}, REQUEST_LOCATION );
        } else
            locationManager.requestLocationUpdates( "gps", 5000, 0, locationListener );


    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {


    }

    @Override
    public void onBackPressed() {
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder( HomeActivity.this );
        alertDialogBuilder.setTitle( "Logout" ).setMessage( "Are you sure to exit?" )
                .setNegativeButton( "No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                } ).setPositiveButton( "Exit", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        } ).show();
    }
}
