package com.project.wastecollection;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;

public class HomeActivity extends AppCompatActivity implements OnMapReadyCallback,NavigationView.OnNavigationItemSelectedListener{
    private static final int REQUEST_LOCATION = 0;
    LocationManager locationManager;
    private String provider;
    DrawerLayout drawerLayout;
    ImageView imageView;
    ActionBarDrawerToggle drawerToggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        drawerLayout= (DrawerLayout) findViewById(R.id.drawarLayout);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        //adding drawar button
        drawerToggle=new ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //navbar item click
        NavigationView navigationView=(NavigationView) findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        //header click navbar
        View headerview = navigationView.getHeaderView(0);
        imageView=(ImageView) headerview.findViewById(R.id.profile_image);

        //opening map fragment
        SupportMapFragment supportMapFragment =(SupportMapFragment)
                getSupportFragmentManager().findFragmentById(R.id.fragmentMap);
        supportMapFragment.getMapAsync(HomeActivity.this);






    }






    @Override
    public void onMapReady(GoogleMap googleMap) {
        //GetLocation();
        Criteria criteria = new Criteria();
        provider = locationManager.getBestProvider(criteria, false);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,new String[]
                    {Manifest.permission.ACCESS_FINE_LOCATION},REQUEST_LOCATION);
        }
        Location location = locationManager.getLastKnownLocation(provider);
        if (location!=null){
            double lat= location.getLatitude();
            double lon=location.getLongitude();

            //showing on map
            LatLng latLng = new LatLng(lat, lon);
            Toast.makeText(this, " "+lat+" "+lon, Toast.LENGTH_LONG).show();
            googleMap.addMarker(new MarkerOptions().position(latLng).title("Your location"));
            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,18),4000,null);
        }
        else{

            Snackbar.make(drawerLayout, "Please allow location to this app", Snackbar.LENGTH_LONG).show();
        }


    }

    //drawer open close click
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private double GetLocation(){
        //Location locationNetwork=locationManager.getLastKnownLocation(NETW)
        return 2.2;
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
//            case R.id.nav_home: {
//                Snackbar.make(drawerLayout, "Home", Snackbar.LENGTH_LONG).show();
//                break;
//            }
//            case R.id.nav_profile: {
//                Snackbar.make(drawerLayout, "Profile", Snackbar.LENGTH_LONG).show();
//                break;
//            }
//            case R.id.nav_trip: {
//                Snackbar.make(drawerLayout, "Trip", Snackbar.LENGTH_LONG).show();
//                break;
//
//            }
//            case R.id.nav_logout: {
//                Snackbar.make(drawerLayout, "Logout", Snackbar.LENGTH_LONG).show();
//                break;
//
//            }
        }
        return false;
    }
}
