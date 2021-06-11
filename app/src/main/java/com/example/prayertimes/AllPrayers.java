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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.List;
import java.util.Locale;

public class AllPrayers extends AppCompatActivity{

    private static final String TAG = "tag";
    private LocationManager locationManager;
    private FusedLocationProviderClient fusedLocationClient;
    private String cityLocation;
    private String countryLocation;
    private TextView fazrNamazId, sunriseId, dhuhrNamazId, asarNamazId, sunsetId, magribNamazId, ishaNamazId;


    /* Url for fetching data */
    String url;

    // Tag used to cancel the request
    String tag_json_obj = "json_obj_req";

    //Progress dialog
    ProgressDialog pDialog;

    Bundle extras;
    private String city;
    private String country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prayers);

        if (savedInstanceState == null) {
            /*fetching extra data passed with intents in a Bundle type variable*/

            extras = getIntent().getExtras();

            if(extras == null) {

                city = null;

            }

            else {
                /* fetching the string passed with intent using ‘extras’*/

                city = extras.getString("city");
                country = extras.getString("country");

            }

        }

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

        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.show();

        url = "http://api.aladhan.com/v1/timingsByCity?city="+ city +"&country="+ country +"&method=8";

        JsonObjectRequest jsonObjReq = new JsonObjectRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {

                        /* Get data from JSON */

                        String urlJson = response.toString();

                        JsonParser jsonParser = new JsonParser();

                        jsonParser.setUrlString(urlJson);

                        fazrNamazId.setText(jsonParser.fazrTime() + " - " + jsonParser.sunrise());
                        sunriseId.setText(jsonParser.sunrise());
                        dhuhrNamazId.setText(jsonParser.dhuhrTime() + " - " + jsonParser.asarTime());
                        asarNamazId.setText(jsonParser.asarTime() + " - " + jsonParser.sunset());
                        sunsetId.setText(jsonParser.sunset());
                        magribNamazId.setText(jsonParser.magribTime() + " - " + jsonParser.ishaTime());
                        ishaNamazId.setText(jsonParser.ishaTime() + " - " + "00:00");

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

}