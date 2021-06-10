package com.example.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.time4j.SystemClock;
import net.time4j.android.ApplicationStarter;
import net.time4j.calendar.HijriCalendar;
import net.time4j.calendar.astro.GeoLocation;
import net.time4j.engine.StartOfDay;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Looper;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import net.time4j.SystemClock;
import net.time4j.android.ApplicationStarter;
import net.time4j.calendar.HijriCalendar;
import net.time4j.calendar.astro.GeoLocation;
import net.time4j.engine.StartOfDay;
import net.time4j.format.expert.ChronoFormatter;
import net.time4j.format.expert.PatternType;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity {

    private TextView cityLocation;
    private LocationManager locationManager;
    private Button allPrayers;
    private FusedLocationProviderClient fusedLocationClient;


    //abd's variables
    public DatabaseHandler databaseHandler;
    private Button calenderButton;
    private int index = 0;
    public SQLiteDatabase prayerDB;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;

    int PERMISSION_ID = 44;
    public double finalLat = 0;
    public double finalLong = 0;

    List<Address> addresses;
    TextView textView;
    Geocoder geocoder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //create database
        databaseHandler = new DatabaseHandler(this);

        /* Initialize and Assign Variable for Bottom Navigation */

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);

        /* Set Home Selected */

        bottomNavigationView.setSelectedItemId(R.id.home);

        /* Perform Item Selected Listener */

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
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
            }
        });

        /* Location */

        Geocoder gcd = new Geocoder(this, Locale.getDefault());

        geocoder = new Geocoder(this, Locale.getDefault());
        textView = (TextView)findViewById(R.id.cityLocation);

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        cityLocation = (TextView) findViewById(R.id.cityLocation);



        getLastLocation();


        /* All Prayers Button */
        allPrayers = (Button) findViewById(R.id.allPrayers);
        allPrayers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openAllPrayers();
            }
        });

        /* Hijri Date */

        ApplicationStarter.initialize(this, true);

        ChronoFormatter<HijriCalendar> hijriFormat =
                ChronoFormatter.setUp(HijriCalendar.family(), Locale.ENGLISH)
                        .addEnglishOrdinal(HijriCalendar.DAY_OF_MONTH)
                        .addPattern(" MMMM yyyy", PatternType.CLDR)
                        .build()
                        .withCalendarVariant(HijriCalendar.VARIANT_UMALQURA);

// conversion from gregorian to hijri-umalqura valid at noon
// (not really valid in the evening when next islamic day starts)
        HijriCalendar today =
                SystemClock.inLocalView().today().transform(
                        HijriCalendar.class,
                        HijriCalendar.VARIANT_UMALQURA
                );
        System.out.println(hijriFormat.format(today)); // 22nd Rajab 1438

// taking into account the specific start of day for Hijri calendar
        HijriCalendar todayExact =
                SystemClock.inLocalView().now(
                        HijriCalendar.family(),
                        HijriCalendar.VARIANT_UMALQURA,
                        StartOfDay.EVENING // simple approximation => 18:00
                ).toDate();
        System.out.println(hijriFormat.format(todayExact)); // 22nd Rajab 1438 (23rd after 18:00)

        TextView hijriDate = (TextView) findViewById(R.id.hijriDate);
        hijriDate.setText(hijriFormat.format(todayExact));

        /*App bar config */

        getSupportActionBar().setTitle("Home");

    }

    public void convertLocation(double latitute,double longitute){
        try {

            if(latitute != 0 && longitute != 0){
                addresses = geocoder.getFromLocation(latitute ,longitute, 1); // Here 1 represent max location result to returned, by documents it recommended 1 to 5

            }

            if(addresses.size()>0){
                String city = addresses.get(0).getLocality();
                String country = addresses.get(0).getCountryName();

                textView.setText(city+"    " +country);
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
                fusedLocationClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if (location == null) {
                            requestNewLocationData();
                        } else {


                            finalLat = location.getLatitude();
                            finalLong = location.getLongitude();

                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    convertLocation(finalLat,finalLong);;
                                }
                            }, 1000);


                        }
                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location...", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
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

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    convertLocation(finalLat,finalLong);;
                }
            }, 1000);

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

            databaseHandler.updateCell(date, index);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}