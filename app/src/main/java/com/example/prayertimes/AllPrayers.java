package com.example.prayertimes;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class AllPrayers extends AppCompatActivity{

    private TextView fazrNamazId, sunriseId, dhuhrNamazId, asarNamazId, sunsetId, magribNamazId, ishaNamazId;
    String fajrNamazAMPM, sunriseAMPM, dhuhrNamazAMPM, asarNamazAMPM, sunsetAMPM, magribNamazAMPM, ishaNamazAMPM, imsakTimeAMPM;
    private TextView cityId, countryId;
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

        /* Setting all waqts time /

        / Time in 24hr format /

        fazrNamazTime = PrefConfig.loadFajrTime(this);
        sunriseTime = PrefConfig.loadSunriseTime(this);
        dhuhrNamazTime = PrefConfig.loadDhuhrTime(this);
        asarNamazTime = PrefConfig.loadAsarTime(this);
        sunsetTime = PrefConfig.loadSunsetTime(this);
        magribNamazTime = PrefConfig.loadMagribTime(this);
        ishaNamazTime = PrefConfig.loadIshaTime(this);

        / Time in 12hr format */


        fajrNamazAMPM = PrefConfig.loadFajrTimeAMPM(this);
        sunriseAMPM = PrefConfig.loadSunriseTimeAMPM(this);
        dhuhrNamazAMPM = PrefConfig.loadDhuhrTimeAMPM(this);
        asarNamazAMPM = PrefConfig.loadAsarTimeAMPM(this);
        sunsetAMPM = PrefConfig.loadSunsetTimeAMPM(this);
        magribNamazAMPM = PrefConfig.loadMagribTimeAMPM(this);
        ishaNamazAMPM = PrefConfig.loadIshaTimeAMPM(this);


        fazrNamazId.setText(fajrNamazAMPM + " - " + sunriseAMPM);
        sunriseId.setText(sunriseAMPM);
        dhuhrNamazId.setText(dhuhrNamazAMPM + " - " + asarNamazAMPM);
        asarNamazId.setText(asarNamazAMPM + " - " + sunsetAMPM);
        sunsetId.setText(sunsetAMPM);
        magribNamazId.setText(magribNamazAMPM + " - " + ishaNamazAMPM);
        ishaNamazId.setText(ishaNamazAMPM);

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