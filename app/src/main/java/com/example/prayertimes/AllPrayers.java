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
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class AllPrayers extends AppCompatActivity{

    private TextView fazrNamazId, sunriseId, dhuhrNamazId, asarNamazId, sunsetId, magribNamazId, ishaNamazId;
    private String fazrNamazTime, sunriseTime, dhuhrNamazTime, asarNamazTime, sunsetTime, magribNamazTime, ishaNamazTime, imsakTime;
    private TextView cityId, countryId;
    String fajrNamazAMPM, sunriseAMPM, dhuhrNamazAMPM, asarNamazAMPM, sunsetAMPM, magribNamazAMPM, ishaNamazAMPM, imsakTimeAMPM;
    private String city;
    private String country;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prayers);

        /*App bar config */


        getSupportActionBar().setTitle("All Prayers");

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        //use Jason to get Data and save it to preConfig
        JasonFetcher jasonFetcher = new JasonFetcher(this);
        jasonFetcher.getData();


        getTextviewId();
        showDataonTextView();





    }

    private void showDataonTextView() {



        city = PrefConfig.loadCurrentCity(this);
        country = PrefConfig.loadCurrentCountry(this);
        cityId.setText(city);
        countryId.setText(country);

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
    }

    private void getTextviewId() {

        fazrNamazId = findViewById(R.id.fazrNamazId);
        sunriseId = findViewById(R.id.sunriseId);
        dhuhrNamazId = findViewById(R.id.dhuhrNamazId);
        asarNamazId = findViewById(R.id.asarNamazId);
        sunsetId = findViewById(R.id.sunsetId);
        magribNamazId = findViewById(R.id.magribNamazId);
        ishaNamazId = findViewById(R.id.ishaNamazId);
        cityId = findViewById(R.id.cityId);
        countryId = findViewById(R.id.countryId);
    }

}