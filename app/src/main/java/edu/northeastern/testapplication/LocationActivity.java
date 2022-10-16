package edu.northeastern.testapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;

public class LocationActivity extends AppCompatActivity {

    FusedLocationProviderClient mFusedLocationClient;
    TextView latitudeTextView, longitudeTextView, distanceTextView;
    int PERMISSION_ID = 44;
    private double lastLatitude;
    private double lastLongitude;
    private double distance = 0.;
    private boolean startCalDistance = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        latitudeTextView = (TextView) findViewById(R.id.latitudeText);
        longitudeTextView = (TextView) findViewById(R.id.longitudeText);
        distanceTextView = (TextView) findViewById(R.id.distance);
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        getLocation();
    }

    public void resetDistanceOnClick(View view) {
        distance = 0.;
        refreshDistance();
    }

    private void refreshDistance() {
        distanceTextView.setText("travel distance: " + String.valueOf(distance));
    }

    private void getLocation() {
        if (checkPermissions()) {
            if (isLocationEnabled()) {
                requestNewLocationData();
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        } else {
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(1);

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLatitude();

            latitudeTextView.setText("Latitude: " + latitude);
            longitudeTextView.setText("Longitude: " + longitude);
            if (startCalDistance) {
                distance += getDistance(lastLatitude, latitude, lastLongitude, longitude);
                refreshDistance();
            } else {
                startCalDistance = true;
            }
            lastLatitude = latitude;
            lastLongitude = longitude;
        }
    };

    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLocation();
        }
    }


    private double getDistance(double lat1, double lat2, double lon1,
                               double lon2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters
        distance = Math.pow(distance, 2);
        return Math.sqrt(distance);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        distance = savedInstanceState.getDouble("distance", 0.);
        startCalDistance = savedInstanceState.getBoolean("startCalDistance", false);
        lastLatitude = savedInstanceState.getDouble("lastLatitude");
        lastLongitude = savedInstanceState.getDouble("lastLongitude");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putDouble("distance", distance);
        outState.putBoolean("startCalDistance", startCalDistance);
        outState.putDouble("lastLatitude", lastLatitude);
        outState.putDouble("lastLongitude", lastLongitude);
        super.onSaveInstanceState(outState);
    }
}