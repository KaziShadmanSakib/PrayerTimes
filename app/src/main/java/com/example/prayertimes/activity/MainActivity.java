package com.example.prayertimes.activity;

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
import android.os.CountDownTimer;
import android.os.Handler;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.prayertimes.datetime.NowAndNextPrayer;
import com.example.prayertimes.datetime.PrayerTimeInMiliSecond;
import com.example.prayertimes.notification.DoNotification;
import com.example.prayertimes.activity.dua.Duas;
import com.example.prayertimes.options.PrefConfig;
import com.example.prayertimes.others.QuoteGetter;
import com.example.prayertimes.R;
import com.example.prayertimes.database.DatabaseHandler;
import com.example.prayertimes.datetime.HijriDate;
import com.example.prayertimes.datetime.JasonFetcher;
import com.example.prayertimes.datetime.TimeParser;
import com.example.prayertimes.options.AboutUsOption;
import com.example.prayertimes.options.QiblaDirectionOption;
import com.example.prayertimes.options.SettingsOption;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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

    private TextView cityLocation, sehriTimeId, iftarTimeId, nextPrayerName, nextPrayerTime, haveYouPrayed, nowPrayerName;
    private FusedLocationProviderClient fusedLocationClient;
    private String city = "Seattle";
    private String country = "United States";
    private Boolean isLocationActive = false;
    private String currentTime, imsakTime, sehri, iftar, fajrNamazTime, dhuhrNamazTime, asarNamazTime, magribNamazTime, ishaNamazTime, sunriseTime, sunsetTime;
    private TextView timerId;
    private CountDownTimer countDownTimer;
    private boolean isTimerRunning;
    private boolean isProgressBarRunning;
    private static boolean isFirstTimeQuote = true;
    long startTime;
    int startTime2;
    long timeLeftInMillies = startTime;
    private TextView quoteOfTheDay;


    ProgressBar progressBar;
    CountDownTimer progressBarCountDownTimer;
    int i;
    private boolean isProgressBarRunningFirstTime;

    //abd's variables
    public DatabaseHandler databaseHandler;
    Calendar calendar;
    SimpleDateFormat simpleDateFormat;
    int PERMISSION_ID = 44;
    public static double finalLat = 0.0;
    public static double finalLong = 0.0;
    public static double finalAlti = 0;
    List<Address> addresses;
    Geocoder geocoder;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //after first time installing
        firstTimeFunction();

     
        getDataFrormPreConfig();

        setCurrentTime();
        setDataBase();
        setLocation();
        setBottomNavigation();
        setHijriDate();
        setTextviewid();
        setNotification();
        setTimer();
        setNowAndNext();
        setQuote();
        setSahriIftariCityText();

        setProgressBarTimer();


    }


    /* Progress bar */

    public void setProgressBarTimer(){

        long midNight = 86400000;

        /* Converting String time to Milliseconds */


        TimeParser tP = new TimeParser();


        long isha = tP.timeParserMethod(ishaNamazTime);
        long current = tP.timeParserMethodForCurrentTime(currentTime);
        long magrib = tP.timeParserMethod(magribNamazTime);
        long imsak = tP.timeParserMethod(imsakTime);
        long fazr = tP.timeParserMethod(fajrNamazTime);
        long sunrise = tP.timeParserMethod(sunriseTime);
        long dhuhr = tP.timeParserMethod(dhuhrNamazTime);
        long asar = tP.timeParserMethod(asarNamazTime);
        long sunset = tP.timeParserMethod(sunsetTime);

        progressBar = (ProgressBar) findViewById(R.id.progress_bar);

        i = PrefConfig.loadProgressBar(this);

        startTime2 = PrefConfig.loadStartTimeProgressBar(this);

        progressBar.setProgress((int)i*100/(startTime2/1000));


        if(current >= isha || current >= 0 && current < fazr){

            startTime2 = (int) (midNight - isha) + (int) fazr;

            PrefConfig.saveStartTimeProgressBar(this, startTime2);


            startProgressBar();



        }

        // fazr -> sunrise

        if(current >= fazr && current < sunrise ){

            //startTime2 = (int) sunrise - (int) current;


            startTime2 = (int) sunrise - (int) fazr;

            PrefConfig.saveStartTimeProgressBar(this, startTime2);


            startProgressBar();

        }

        // sunrise -> dhuhr

        if(current >= sunrise && current < dhuhr ){


            //startTime2 = (int) dhuhr - (int) current;


            startTime2 = (int) dhuhr - (int) sunrise;

            PrefConfig.saveStartTimeProgressBar(this, startTime2);



            startProgressBar();

        }

        if(current >= dhuhr && current < asar){


            //startTime2 = (int) asar - (int) current;

            startTime2 = (int) asar - (int) dhuhr;

            PrefConfig.saveStartTimeProgressBar(this, startTime2);


            startProgressBar();

        }

        if(current >= asar && current < magrib){


            //startTime2 = (int) magrib - (int) current;

            startTime2 = (int) magrib - (int) asar;

            PrefConfig.saveStartTimeProgressBar(this, startTime2);


            startProgressBar();

        }

        if(current >= magrib && current < isha){

            //startTime2 = (int) isha - (int)current;

            startTime2 = (int) isha - (int) magrib;

            PrefConfig.saveStartTimeProgressBar(this, startTime2);



            startProgressBar();
        }

    }

    private void startProgressBar(){

        progressBarCountDownTimer = new CountDownTimer(startTime2, 1000)
        {

            public void onTick(long millisUntilFinished)
            {
                i++;
               // Toast.makeText(MainActivity.this, String.valueOf(i), Toast.LENGTH_SHORT).show();
                PrefConfig.saveProgressBar(MainActivity.this, i);
                progressBar.setProgress((int)i*100/(startTime2/1000));
            }

            public void onFinish()
            {
                i = 0;
                progressBar.setProgress(100);
            }
        }.start();


    }

    /* Option bar */

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();

        inflater.inflate(R.menu.menu_options, menu);

        return true;
    }

    /* Getting into the Option bar items */

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id = item.getItemId();

        if(id == R.id.optionMenu_qiblaDirection){

            startActivity(new Intent(MainActivity.this, QiblaDirectionOption.class));

        }

        if(id == R.id.optionMenu_settings){

            startActivity(new Intent(MainActivity.this, SettingsOption.class));

        }

        if(id == R.id.optionMenu_aboutUs){

            startActivity(new Intent(MainActivity.this, AboutUsOption.class));

        }

        return super.onOptionsItemSelected(item);
    }




    private void firstTimeFunction() {
        if(PrefConfig.loadFirstTime(this)=="FirstTime"){


            PrefConfig.saveCurrentCity(this,"Seattle");
            PrefConfig.saveCurrentCountry(this,"United States");

            PrefConfig.saveProgressBar(this, 0);

            PrefConfig.saveStartTimeProgressBar(this, 1000);

            JasonFetcher jasonFetcher = new JasonFetcher(this);
            jasonFetcher.getData();
            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if((PrefConfig.loadFirstTime(this)!="FirstTime")){
                    setNowAndNext();
                }
            }, 5000);



        }
    }




    public void setTimer() {

        long midNight = 86400000;

        /* Converting String time to Milliseconds */

        TimeParser timeParser = new TimeParser();
        long fazrTime = timeParser.timeParserMethod(fajrNamazTime);
        long sunrise = timeParser.timeParserMethod(sunriseTime);
        long dhuhrTime = timeParser.timeParserMethod(dhuhrNamazTime);
        long asarTime = timeParser.timeParserMethod(asarNamazTime);
        long sunset = timeParser.timeParserMethod(sunsetTime);
        long magribTime = timeParser.timeParserMethod(magribNamazTime);
        long ishaTime = timeParser.timeParserMethod(ishaNamazTime);
        long imsak = timeParser.timeParserMethod(imsakTime);
        long currentTime1 = timeParser.timeParserMethodForCurrentTime(currentTime);

        if(currentTime1 >= ishaTime || currentTime1 >= 0 && currentTime1 < fazrTime){


            startTime = (midNight - currentTime1) + fazrTime;
            timeLeftInMillies = startTime;

            timerId = findViewById(R.id.timerId);

            startTimer();



        }

        // fazr -> sunrise

        if(currentTime1 >= fazrTime && currentTime1 < sunrise ){

            startTime = sunrise - currentTime1;
            timeLeftInMillies = startTime;

            timerId = findViewById(R.id.timerId);

            startTimer();

        }

        // sunrise -> dhuhr

        if(currentTime1 >= sunrise && currentTime1 < dhuhrTime ){

            startTime = dhuhrTime - currentTime1;
            timeLeftInMillies = startTime;

            timerId = findViewById(R.id.timerId);

            startTimer();

        }

        if(currentTime1 >= dhuhrTime && currentTime1 < asarTime){

            startTime = asarTime - currentTime1;

            timeLeftInMillies = startTime;

            timerId = findViewById(R.id.timerId);


            startTimer();

        }

        if(currentTime1 >= asarTime && currentTime1 < magribTime){

            startTime = magribTime - currentTime1;

            timeLeftInMillies = startTime;

            timerId = findViewById(R.id.timerId);

            startTimer();

        }

        if(currentTime1 >= magribTime && currentTime1 < ishaTime){

            startTime = ishaTime - currentTime1;

            timeLeftInMillies = startTime;

            timerId = findViewById(R.id.timerId);

            startTimer();
        }

    }


    private void startTimer() {

        countDownTimer = new CountDownTimer(timeLeftInMillies, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillies = millisUntilFinished;
                updateCountDownText();

            }

            @Override
            public void onFinish() {

                isTimerRunning = false;

            }
        }.start();

        isTimerRunning = true;

    }

    private void updateCountDownText() {

        int hours   = (int) ((timeLeftInMillies / (1000*60*60)) % 24);
        int minutes = (int) ((timeLeftInMillies / (1000*60)) % 60);
        int seconds = (int) (timeLeftInMillies / 1000) % 60 ;

        String timeLeftFormatted = String.format(Locale.getDefault(),"%02d:%02d:%02d", hours, minutes, seconds);

        timerId.setText(timeLeftFormatted);

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
                        finalAlti = location.getAltitude();

                        PrefConfig.saveLongitude(getApplicationContext(), (float) finalLong);
                        PrefConfig.saveLatitude(getApplicationContext(), (float) finalLat);
                        PrefConfig.saveAltitude(getApplicationContext(), (float) finalAlti);

                        new Handler(Looper.getMainLooper()).postDelayed(() -> MainActivity.this.convertLocation(finalLat, finalLong), 1000);


                    }
                });
            } else {
                Toast.makeText(this, "Please turn on" + " your location", Toast.LENGTH_LONG).show();

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
            finalAlti = mLastLocation.getAltitude();

            PrefConfig.saveLongitude(getApplicationContext(), (float) finalLong);
            PrefConfig.saveLatitude(getApplicationContext(), (float) finalLat);
            PrefConfig.saveAltitude(getApplicationContext(), (float) finalAlti);

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

    private void convertLocation(double lat,double lon){
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


    private void getDataFrormPreConfig() {

        sehri = PrefConfig.loadImsakTime(this);
        iftar = PrefConfig.loadMagribTime(this);
        fajrNamazTime = PrefConfig.loadFajrTime(this);
        sunriseTime = PrefConfig.loadSunriseTime(this);
        dhuhrNamazTime = PrefConfig.loadDhuhrTime(this);
        asarNamazTime = PrefConfig.loadAsarTime(this);
        sunsetTime = PrefConfig.loadSunsetTime(this);
        magribNamazTime = PrefConfig.loadMagribTime(this);
        ishaNamazTime = PrefConfig.loadIshaTime(this);
        currentTime = PrefConfig.loadCurrentTime(this);
        imsakTime = PrefConfig.loadImsakTime(this);
    }

    //update Location
    public void locationUpdate(View v){
        convertLocation(finalLat,finalLong);
    }

    public void openAllPrayers(View v) {

        Intent intent = new Intent(this, AllPrayers.class);

        startActivity(intent);

    }

    //abd's update Database
    @SuppressLint("SimpleDateFormat")
    public void toClaendarDateLog(View v) {
        NowAndNextPrayer nowAndNextPrayer = new NowAndNextPrayer(this);
        nowAndNextPrayer.setNowAndNext();

        String mystring = nowAndNextPrayer.getHaveYouPrayed();
        String arr[] = mystring.split(" ", 2);
        String firstWord = arr[0];
        String lastWord = mystring.substring(mystring.lastIndexOf(" ")+1);

        if(firstWord.equals("Have")){
            calendar = Calendar.getInstance();
            simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
            String date = simpleDateFormat.format(calendar.getTime());
            Intent i = new Intent(this, CalendarDateLog.class);

            Date currentTimeDate = Calendar.getInstance().getTime();
            PrayerTimeInMiliSecond prayerTimeInMiliSecond = new PrayerTimeInMiliSecond(this);
            prayerTimeInMiliSecond.toMiliSec();
            int currentTime = prayerTimeInMiliSecond.getCurrentTimeInMiliSec(currentTimeDate);
            int fajr = prayerTimeInMiliSecond.getFajrInMili();


            if (currentTime <= 0 || currentTime >= fajr) {
                int dateInt = Integer.parseInt(date);
                dateInt += 1;
                date = String.valueOf(dateInt);
            }
            i.putExtra("clickedDate",date);
            startActivity(i);


        }







    }

    private void setBottomNavigation() {
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
        /*App bar config */

        Objects.requireNonNull(getSupportActionBar()).setTitle("Home");
    }

    private void setLocation() {
        geocoder = new Geocoder(this, Locale.getDefault());
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        if(PrefConfig.loadLocationType(this)==0){



            if(finalLat != 0 && finalLong != 0){
                convertLocation(finalLat,finalLong);
            }
            getLastLocation();
        }

    }

    private void setDataBase(){
        databaseHandler = new DatabaseHandler(this);
        new Thread(() -> databaseHandler.getContact("20210101")).start();
    }

    private void setHijriDate() {
        HijriDate hijriDate = new HijriDate(this);
        TextView hijriDateTextview = (TextView) findViewById(R.id.hijriDate);
        hijriDateTextview.setText(hijriDate.hijriUpdate());
    }

    private void setTextviewid() {
        cityLocation = (TextView) findViewById(R.id.cityLocation);
        quoteOfTheDay = (TextView) findViewById(R.id.quote);
        nowPrayerName = findViewById(R.id.nowPrayerName);
        nextPrayerTime = findViewById(R.id.nextPrayerTime);
        nextPrayerName = findViewById(R.id.nextPrayerName);
        haveYouPrayed = findViewById(R.id.haveYouPrayed);
        sehriTimeId = (TextView) findViewById(R.id.sehriTimeId);
        iftarTimeId = (TextView) findViewById(R.id.iftarTimeId);
    }

    private void setNotification() {
        DoNotification doNotification = new DoNotification(getApplicationContext());
        if(PrefConfig.loadNotificationIndex(this)==1){
            doNotification.setNotification();
        }

        else{
            doNotification.cancelAlarm();
        }
    }

    private void setSahriIftariCityText() {
        String sehriAMPM = "00:00";
        String iftarAMPM = "00:00";
        int extraMin = PrefConfig.loadExtraMinutesCount(this);
        if(PrefConfig.loadExtraTimePref(this)==0) extraMin = 0;



        PrayerTimeInMiliSecond prayerTimeInMiliSecond = new PrayerTimeInMiliSecond(this);
        prayerTimeInMiliSecond.toMiliSec();


        int sehri = prayerTimeInMiliSecond.getFajrInMili();
        sehri = sehri - extraMin*60*1000;
        String sehriTime = prayerTimeInMiliSecond.militoHour(sehri);
        sehriTime = prayerTimeInMiliSecond.timeParseToAMPM(sehriTime);
        sehriAMPM = sehriTime;


        int iftari = prayerTimeInMiliSecond.getMagribInMili();
        iftari = iftari + extraMin*60*1000;
        String iftariTime = prayerTimeInMiliSecond.militoHour(iftari);
        iftariTime = prayerTimeInMiliSecond.timeParseToAMPM(iftariTime);
        iftarAMPM = iftariTime;



        city = PrefConfig.loadCurrentCity(this);
        cityLocation.setText(city);
        sehriTimeId.setText(sehriAMPM);
        iftarTimeId.setText(iftarAMPM);
    }

    private void setQuote() {

        TimeParser timeParser = new TimeParser();
        long currentTime1 = timeParser.timeParserMethodForCurrentTime(currentTime);
        QuoteGetter quoteGetter = new QuoteGetter(this, currentTime1);
        quoteOfTheDay.setText(quoteGetter.quoteOfTheDayFunction());

    }

    private void setCurrentTime() {
        Calendar calendar1 = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
        currentTime = simpleDateFormat1.format(calendar1.getTime());
        PrefConfig.saveCurrentTime(getApplicationContext(), currentTime);
    }
    private void setNowAndNext() {

        NowAndNextPrayer nowAndNextPrayer = new NowAndNextPrayer(this);
        nowAndNextPrayer.setNowAndNext();
        haveYouPrayed.setText(nowAndNextPrayer.getHaveYouPrayed());
        nowPrayerName.setText(nowAndNextPrayer.getNowPrayerName());
        nextPrayerName.setText(nowAndNextPrayer.getNextPrayerName());
        nextPrayerTime.setText(nowAndNextPrayer.getNextPrayerTime());

    }


}