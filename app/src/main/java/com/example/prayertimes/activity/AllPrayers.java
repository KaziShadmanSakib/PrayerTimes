package com.example.prayertimes.activity;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.example.prayertimes.datetime.NowAndNextPrayer;
import com.example.prayertimes.datetime.PrayerTimeInMiliSecond;
import com.example.prayertimes.options.PrefConfig;
import com.example.prayertimes.R;
import com.example.prayertimes.datetime.JasonFetcher;


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
        setSunrise();
        dhuhrNamazId.setText(dhuhrNamazAMPM + " - " + asarNamazAMPM);
        asarNamazId.setText(asarNamazAMPM + " - " + sunsetAMPM);
        setSunset();
        magribNamazId.setText(magribNamazAMPM + " - " + ishaNamazAMPM);
        ishaNamazId.setText(ishaNamazAMPM);

        if(NowAndNextPrayer.getWeekDay()=="Friday"){
            TextView dhuhrNamaz = findViewById(R.id.dhuhrNamaz);
            dhuhrNamaz.setText("Jumu'ah");
        }

    }

    private void setSunrise() {

        int time;
        if(PrefConfig.loadMazhabType(this)==0){
            time = 15;
        }
        else{
            time = 23;
        }

        String s = PrefConfig.loadSunriseTime(this);
        PrayerTimeInMiliSecond prayerTimeInMiliSecond = new PrayerTimeInMiliSecond(this);
        prayerTimeInMiliSecond.toMiliSec();

        int sunrise = prayerTimeInMiliSecond.getSunriseInMili();
        sunrise = sunrise + time*60*1000;
        String sunriseEnd = prayerTimeInMiliSecond.militoHour(sunrise);
        sunriseEnd = prayerTimeInMiliSecond.timeParseToAMPM(sunriseEnd);
        sunriseId.setText(sunriseAMPM + " - " + sunriseEnd);


    }
    private void setSunset() {

        int time;
        if(PrefConfig.loadMazhabType(this)==0){
            time = 15;
        }
        else{
            time = 23;
        }

        String s = PrefConfig.loadSunsetTime(this);
        PrayerTimeInMiliSecond prayerTimeInMiliSecond = new PrayerTimeInMiliSecond(this);
        prayerTimeInMiliSecond.toMiliSec();

        int sunset = prayerTimeInMiliSecond.getSunsetInMili();
        sunset = sunset - time*60*1000;
        String sunsetStart = prayerTimeInMiliSecond.militoHour(sunset);
        sunsetStart = prayerTimeInMiliSecond.timeParseToAMPM(sunsetStart);
        sunsetId.setText(sunsetStart+ " - " +sunsetAMPM  );


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