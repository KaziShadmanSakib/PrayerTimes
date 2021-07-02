package com.kssabd.prayertimes.activity;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.widget.TextView;

import com.kssabd.prayertimes.datetime.NowAndNextPrayer;
import com.kssabd.prayertimes.datetime.PrayerTimeInMiliSecond;
import com.kssabd.prayertimes.options.PrefConfig;
import com.kssabd.prayertimes.R;
import com.kssabd.prayertimes.datetime.JasonFetcher;


public class AllPrayers extends AppCompatActivity{

    private TextView fazrNamazId, sunriseId, dhuhrNamazId, asarNamazId, sunsetId, magribNamazId, ishaNamazId;
    private TextView fazrNamazEndId, sunriseEndId, dhuhrNamazEndId, asarNamazEndId, sunsetEndId, magribNamazEndId, ishaNamazEndId;
    String fajrNamazAMPM, sunriseAMPM, dhuhrNamazAMPM, asarNamazAMPM, sunsetAMPM, magribNamazAMPM, ishaNamazAMPM, imsakTimeAMPEndM;
    private TextView cityId, countryId;
    private String city;
    private String country;

    boolean connected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_prayers);




        /*App bar config */

        getSupportActionBar().setTitle("All Prayers");

        /* Back Button */

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        connectedOrNot();

        if(connected){

            //use Jason to get Data and save it to preConfig
            JasonFetcher jasonFetcher = new JasonFetcher(this);
            jasonFetcher.getData();

        }

        else{

            fajrNamazAMPM = PrefConfig.loadFajrTimeAMPM(this);
            sunriseAMPM = PrefConfig.loadSunriseTimeAMPM(this);
            dhuhrNamazAMPM = PrefConfig.loadDhuhrTimeAMPM(this);
            asarNamazAMPM = PrefConfig.loadAsarTimeAMPM(this);
            sunsetAMPM = PrefConfig.loadSunsetTimeAMPM(this);
            magribNamazAMPM = PrefConfig.loadMagribTimeAMPM(this);
            ishaNamazAMPM = PrefConfig.loadIshaTimeAMPM(this);
            city = PrefConfig.loadCurrentCity(this);
            country = PrefConfig.loadCurrentCountry(this);

        }




        getTextviewId();
        showDataonTextView();


    }

    private void connectedOrNot(){

        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
            //we are connected to a network
            connected = true;
        }
        else
            connected = false;

    }

    private void showDataonTextView() {



        city = PrefConfig.loadCurrentCity(this);
        country = PrefConfig.loadCurrentCountry(this);
        city = city.substring(0, 1).toUpperCase() + city.substring(1);
        cityId.setText(city);
        country = country.substring(0, 1).toUpperCase() + country.substring(1);
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


        fazrNamazId.setText(fajrNamazAMPM );
        fazrNamazEndId.setText(sunriseAMPM);
        setSunrise();
        dhuhrNamazId.setText(dhuhrNamazAMPM);
        dhuhrNamazEndId.setText(asarNamazAMPM);
        asarNamazId.setText(asarNamazAMPM);
        asarNamazEndId.setText(sunsetAMPM);
        setSunset();
        magribNamazId.setText(magribNamazAMPM);
        magribNamazEndId.setText(ishaNamazAMPM);
        ishaNamazId.setText(ishaNamazAMPM);

        if(NowAndNextPrayer.getWeekDay()=="Friday"){
            TextView dhuhrNamaz = findViewById(R.id.dhuhr);
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
        sunriseId.setText(sunriseAMPM);
        sunriseEndId.setText(sunriseEnd);


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
        sunsetId.setText(sunsetStart);
        sunsetEndId.setText(sunsetAMPM);


    }

    private void getTextviewId() {

        fazrNamazId = findViewById(R.id.fazrNamazId);
        sunriseId = findViewById(R.id.sunriseId);
        dhuhrNamazId = findViewById(R.id.dhuhrNamazId);
        asarNamazId = findViewById(R.id.asarNamazId);
        sunsetId = findViewById(R.id.sunsetId);
        magribNamazId = findViewById(R.id.magribNamazId);
        ishaNamazId = findViewById(R.id.ishaNamazId);
        fazrNamazEndId = findViewById(R.id.fazrNamazEndId);
        sunriseEndId = findViewById(R.id.sunriseEndId);
        dhuhrNamazEndId = findViewById(R.id.dhuhrNamazEndId);
        asarNamazEndId = findViewById(R.id.asarNamazEndId);
        sunsetEndId = findViewById(R.id.sunsetEndId);
        magribNamazEndId = findViewById(R.id.magribNamazEndId);
        ishaNamazEndId = findViewById(R.id.ishaNamazEndId);

        cityId = findViewById(R.id.cityId);
        countryId = findViewById(R.id.countryId);
    }

}