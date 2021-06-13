package com.example.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.time4j.SystemClock;
import net.time4j.android.ApplicationStarter;
import net.time4j.calendar.HijriCalendar;
import net.time4j.engine.StartOfDay;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

import android.annotation.SuppressLint;
import android.os.Looper;
import android.widget.Toast;

import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;





public class MainActivity extends AppCompatActivity {

    private TextView cityLocation, sehriTimeId, iftarTimeId;
    private Button allPrayers;
    private FusedLocationProviderClient fusedLocationClient;
    private String city;
    private String country;
    private Boolean isLocationActive = false;


    //abd's variables
    public DatabaseHandler databaseHandler;
    private int index = 4;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    int PERMISSION_ID = 44;
    public static double finalLat = 0;
    public static double finalLong = 0;

    List<Address> addresses;
    Geocoder geocoder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create database
        databaseHandler = new DatabaseHandler(this);


        /* Bottom Navigation */
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.home);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.log:
                    startActivity(new Intent(getApplicationContext(), LogActivity.class));
                    overridePendingTransition(0, 0);
                    return true;
                case R.id.home:
                    return true;
                case R.id.duas:
                    startActivity(new Intent(getApplicationContext(), Duas.class));
                    overridePendingTransition(0, 0);
                    return true;
            }
            return false;
        });

        /* Location */
        geocoder = new Geocoder(this, Locale.getDefault());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        cityLocation = (TextView) findViewById(R.id.cityLocation);
        if(finalLat != 0 && finalLong != 0){
            convertLocation(finalLat,finalLong);
        }
        getLastLocation();



        /* All Prayers Button */
        allPrayers = (Button) findViewById(R.id.allPrayers);
        allPrayers.setOnClickListener(v -> openAllPrayers());

        /* Setting Location to TextView Location */

        city = PrefConfig.loadCurrentCity(this);
        cityLocation.setText(city);


        /* Hijri date is appeared using this function */
        hijriUpdate();


        /* Getting Sehri Time and Iftar Time */

        /*sehriTimeId = (TextView) findViewById(R.id.sehriTimeId);
        iftarTimeId = (TextView) findViewById(R.id.iftarTimeId);

        String sehri = PrefConfig.loadImsakTime(this);
        String iftar = PrefConfig.loadMagribTime(this);

        sehriTimeId.setText(sehri);
        iftarTimeId.setText(iftar);*/

    }

    public void convertLocation(double lat,double lon){
        try {

            if(lat != 0 && lon != 0){
                addresses = geocoder.getFromLocation(lat ,lon, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            }

            if(addresses.size()>0){
                city = addresses.get(0).getLocality();
                country = addresses.get(0).getCountryName();

                /* Saves the location to PrefConfig (SharedPreferences) */

                PrefConfig.saveCurrentCity(getApplicationContext(), city);
                PrefConfig.saveCurrentCountry(getApplicationContext(), country);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void openAllPrayers() {

        Intent intent = new Intent(this, AllPrayers.class);

        startActivity(intent);

    }


    @SuppressLint("MissingPermission")
    private void getLastLocation() {
        // check if permissions are given
        if (checkPermissions()) {

            // check if location is enabled
            if (isLocationEnabled()) {

                // getting last
                // location from
                // FusedLocationClient
                // object
                fusedLocationClient.getLastLocation().addOnCompleteListener(task -> {
                    Location location = task.getResult();
                    if (location == null) {
                        requestNewLocationData();
                    } else {


                        finalLat = location.getLatitude();
                        finalLong = location.getLongitude();

                        new Handler(Looper.getMainLooper()).postDelayed(() -> MainActivity.this.convertLocation(finalLat, finalLong), 1000);


                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();

                if(!isLocationActive){
                    isLocationActive = true;
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle("Please Enable Location Service")
                            .setCancelable(false)
                            .setPositiveButton("Enable", (dialog, which) -> {

                                /* If GPS is disabled this will redirect us to Location Settings */

                                startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS));

                            }).setNegativeButton("Cancel", null)
                            .show();
                }


            }
        } else {
            // if permissions aren't available,
            // request for permissions
            requestPermissions();
        }
    }

    @SuppressLint("MissingPermission")
    private void requestNewLocationData() {

        // Initializing LocationRequest
        // object with appropriate methods
        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setInterval(5);
        mLocationRequest.setFastestInterval(0);
        mLocationRequest.setNumUpdates(1);

        // setting LocationRequest
        // on FusedLocationClient
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        fusedLocationClient.requestLocationUpdates(mLocationRequest, mLocationCallback, Looper.myLooper());
    }

    private LocationCallback mLocationCallback = new LocationCallback() {

        @Override
        public void onLocationResult(LocationResult locationResult) {
            Location mLastLocation = locationResult.getLastLocation();

            finalLat = mLastLocation.getLatitude();
            finalLong = mLastLocation.getLongitude();

            new Handler(Looper.getMainLooper()).postDelayed(() -> convertLocation(finalLat,finalLong), 1000);

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ;
        //&&ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED;


    }

    // method to request for permissions
    private void requestPermissions() {
        ActivityCompat.requestPermissions(this, new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION}, PERMISSION_ID);
    }

    // method to check
    // if location is enabled
    private boolean isLocationEnabled() {
        LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    // If everything is alright then
    @Override
    public void
    onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_ID) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getLastLocation();
            }
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (checkPermissions()) {
            getLastLocation();
        }
    }



    //update Location
    public void locationUpdate(View v){
        convertLocation(finalLat,finalLong);
    }

    //hijri update function
    private void hijriUpdate(){
        /* Hijri Date */

        ApplicationStarter.initialize(this, true);

        ChronoFormatter<HijriCalendar> hijriFormat =
                ChronoFormatter.setUp(HijriCalendar.family(), Locale.ENGLISH)
                        .addEnglishOrdinal(HijriCalendar.DAY_OF_MONTH)
                        .addPattern(" MMMM yyyy", PatternType.CLDR)
                        .build()
                        .withCalendarVariant(HijriCalendar.VARIANT_UMALQURA);
        // conversion from gregorian to hijri valid at noon
        // (not really valid in the evening when next islamic day starts)
        HijriCalendar today =
                SystemClock.inLocalView().today().transform(
                        HijriCalendar.class,
                        HijriCalendar.VARIANT_UMALQURA
                );

        // taking into account the specific start of day for Hijri calendar
        HijriCalendar todayExact =
                SystemClock.inLocalView().now(
                        HijriCalendar.family(),
                        HijriCalendar.VARIANT_UMALQURA,
                        StartOfDay.MIDNIGHT // simple approximation => 18:00
                ).toDate();


        TextView hijriDate = (TextView) findViewById(R.id.hijriDate);
        hijriDate.setText(hijriFormat.format(todayExact));

        /*App bar config */

        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
    }


    //abd's update Database

    public void updateDB(View v) {



        calendar = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(calendar.getTime());


        if (databaseHandler.getContact(date)._date == null) {
            try {
                Contact contact = new Contact(date, false, false, false, false, false);
                databaseHandler.addContact(contact);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Toast toast=Toast.makeText(getApplicationContext(),date,Toast.LENGTH_SHORT);
            toast.show();

            databaseHandler.updateCell(date, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}