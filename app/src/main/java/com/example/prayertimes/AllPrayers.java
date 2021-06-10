package com.example.prayertimes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.VoiceInteractor;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

public class AllPrayers extends AppCompatActivity implements LocationListener {

    private static final String TAG = "tag";
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private String cityLocation;
    private String countryLocation;
    private TextView fazrNamazId, sunriseId, dhuhrNamazId, asarNamazId, sunsetId, magribNamazId, ishaNamazId;


    /* Url for fetching data */
    String url = "http://api.aladhan.com/v1/timingsByCity?city=Dubai&country=United%20Arab%20Emirates&method=8";

    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    //Progress dialog
    ProgressDialog pDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prayers);

        /*App bar config */

        getSupportActionBar().setTitle("All Prayers");

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* Get User Specific Location */

        /* Gets Last Location from device */

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        fusedLocationClient.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if(location != null){
                    try {
                        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
                        List<Address> addresses = null;
                        addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        cityLocation = addresses.get(0).getLocality().toString();
                        countryLocation = addresses.get(0).getCountryName().toString();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });


        // Gets the User's Location
        grantPermission();
        getLocation();

        /* Showing all waqts time on current day */

        fazrNamazId = findViewById(R.id.fazrNamazId);
        sunriseId = findViewById(R.id.sunriseId);
        dhuhrNamazId = findViewById(R.id.dhuhrNamazId);
        asarNamazId = findViewById(R.id.asarNamazId);
        sunsetId = findViewById(R.id.sunsetId);
        magribNamazId = findViewById(R.id.magribNamazId);
        ishaNamazId = findViewById(R.id.ishaNamazId);

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        /* Get data from JSON */

                        try {

                            String fajr = response.getJSONArray("data").getJSONObject(0).get("timings").toString();
                            fazrNamazId.setText(fajr);



                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        pDialog.hide();
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Toast.makeText(AllPrayers.this, "Error", Toast.LENGTH_SHORT).show();

                // hide the progress dialog
                pDialog.hide();
            }
        });

// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

    }


    private void getLocation() {
        try {
            locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 500, 5, (LocationListener) this);
        }catch (SecurityException e){
            e.printStackTrace();
        }
    }


    private void grantPermission() {
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION}, 100);
        }
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        try {
            Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
            cityLocation = addresses.get(0).getLocality().toString();
            countryLocation = addresses.get(0).getCountryName().toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}