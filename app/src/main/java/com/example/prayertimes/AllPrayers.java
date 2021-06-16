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
import android.media.Ringtone;
import android.media.RingtoneManager;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AllPrayers extends AppCompatActivity{

    private static final String TAG = "tag";
    private static Boolean isTrue = false;
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private TextView fazrNamazId, sunriseId, dhuhrNamazId, asarNamazId, sunsetId, magribNamazId, ishaNamazId;
    private String fazrNamazTime, sunriseTime, dhuhrNamazTime, asarNamazTime, sunsetTime, magribNamazTime, ishaNamazTime, imsakTime;
    private TextView cityId, countryId;

    /* Url for fetching data */
    String url;

    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    //Progress dialog
    //ProgressDialog pDialog;

    private String city;
    private String country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prayers);

        city = PrefConfig.loadCurrentCity(this);
        country = PrefConfig.loadCurrentCountry(this);

        //Toast.makeText(this, city, Toast.LENGTH_SHORT).show();
        //Toast.makeText(this, country,Toast.LENGTH_LONG).show();

        /*App bar config */

        getSupportActionBar().setTitle("All Prayers");

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /* Showing all waqts time on current day */

        fazrNamazId = findViewById(R.id.fazrNamazId);
        sunriseId = findViewById(R.id.sunriseId);
        dhuhrNamazId = findViewById(R.id.dhuhrNamazId);
        asarNamazId = findViewById(R.id.asarNamazId);
        sunsetId = findViewById(R.id.sunsetId);
        magribNamazId = findViewById(R.id.magribNamazId);
        ishaNamazId = findViewById(R.id.ishaNamazId);
        cityId = findViewById(R.id.cityId);
        countryId = findViewById(R.id.countryId);

        cityId.setText(city);
        countryId.setText(country);

        /*pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();*/

        url = "http://api.aladhan.com/v1/timingsByCity?city="+ city +"&country="+ country +"&method=1&school=1";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        /* Get data from JSON */

                        String urlJson = response.toString();

                        JsonParser jsonParser = new JsonParser();

                        jsonParser.setUrlString(urlJson);


                        fazrNamazTime = jsonParser.fazrTime();
                        sunriseTime = jsonParser.sunrise();
                        dhuhrNamazTime = jsonParser.dhuhrTime();
                        asarNamazTime = jsonParser.asarTime();
                        sunsetTime = jsonParser.sunset();
                        magribNamazTime = jsonParser.magribTime();
                        ishaNamazTime = jsonParser.ishaTime();
                        imsakTime = jsonParser.imsakTime();

                        /* Saving all the waqts in PrefConfig (SharedPreferences) */

                        PrefConfig.saveFajrTime(getApplicationContext(), fazrNamazTime);
                        PrefConfig.saveSunriseTime(getApplicationContext(), sunriseTime);
                        PrefConfig.saveDhuhrTime(getApplicationContext(), dhuhrNamazTime);
                        PrefConfig.saveAsarTime(getApplicationContext(), asarNamazTime);
                        PrefConfig.saveSunsetTime(getApplicationContext(), sunsetTime);
                        PrefConfig.saveMagribTime(getApplicationContext(), magribNamazTime);
                        PrefConfig.saveIshaTime(getApplicationContext(), ishaNamazTime);
                        PrefConfig.saveImsakTime(getApplicationContext(), imsakTime);

                        //pDialog.hide();

                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());

                Toast.makeText(AllPrayers.this, "Please turn on location or internet to be always updated", Toast.LENGTH_SHORT).show();

                // hide the progress dialog
                //pDialog.hide();
            }
        });


// Adding request to request queue
        AppController.getInstance().addToRequestQueue(jsonObjReq, tag_json_obj);

        /* Setting all waqts time */

        fazrNamazTime = PrefConfig.loadFajrTime(this);
        sunriseTime = PrefConfig.loadSunriseTime(this);
        dhuhrNamazTime = PrefConfig.loadDhuhrTime(this);
        asarNamazTime = PrefConfig.loadAsarTime(this);
        sunsetTime = PrefConfig.loadSunsetTime(this);
        magribNamazTime = PrefConfig.loadMagribTime(this);
        ishaNamazTime = PrefConfig.loadIshaTime(this);

        fazrNamazId.setText(fazrNamazTime + " - " + sunriseTime);
        sunriseId.setText(sunriseTime);
        dhuhrNamazId.setText(dhuhrNamazTime + " - " + asarNamazTime);
        asarNamazId.setText(asarNamazTime + " - " + sunsetTime);
        sunsetId.setText(sunsetTime);
        magribNamazId.setText(magribNamazTime + " - " + ishaNamazTime);
        ishaNamazId.setText(ishaNamazTime);


        /* Set Alert for all waqts */






    }

}