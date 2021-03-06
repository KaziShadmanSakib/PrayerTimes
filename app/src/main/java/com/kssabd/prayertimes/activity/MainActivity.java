/* Version: 1.0.1
* Friday, July 02, 2021
*
* Created by -
*   Kazi Shadman Sakib
*   Abdullah Ibne Masud */

package com.kssabd.prayertimes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.kssabd.prayertimes.datetime.NowAndNextPrayer;
import com.kssabd.prayertimes.datetime.PrayerTimeInMiliSecond;
import com.kssabd.prayertimes.notification.DoNotification;
import com.kssabd.prayertimes.activity.dua.Duas;
import com.kssabd.prayertimes.options.PrefConfig;
import com.kssabd.prayertimes.others.QuoteGetter;
import com.kssabd.prayertimes.R;
import com.kssabd.prayertimes.database.DatabaseHandler;
import com.kssabd.prayertimes.datetime.HijriDate;
import com.kssabd.prayertimes.datetime.JasonFetcher;
import com.kssabd.prayertimes.datetime.TimeParser;
import com.kssabd.prayertimes.options.AboutUsOption;
import com.kssabd.prayertimes.options.QiblaDirectionOption;
import com.kssabd.prayertimes.options.SettingsOption;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
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
    private Boolean isLocationActive = false;
    private String currentTimeString, imsakTime, sehri, iftar, fajrNamazTime, dhuhrNamazTime, asarNamazTime, magribNamazTime, ishaNamazTime, sunriseTime, sunsetTime;
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
    static String edittedLocation = "Dhaka,Bangladesh";
    static boolean isLocationChanged = false;




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
        setnewLocation();


    }

    private void setnewLocation() {
        if(isLocationChanged){

            isLocationChanged = false;


            List<String> locationList = Arrays.asList(edittedLocation.split("\\s*,\\s*"));



            JasonFetcher jasonFetcher = new JasonFetcher(this);

            jasonFetcher.getTempData(locationList.get(0),locationList.get(1));

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                if(jasonFetcher.isNoError()){
                    PrefConfig.saveCurrentCity(this,locationList.get(0));
                    PrefConfig.saveCurrentCountry(this,locationList.get(1));
                    setSahriIftariCityText();
                    setTimer();
                }
                else {
                    Toast toast = Toast.makeText(this,"Invalid Country or City",Toast.LENGTH_SHORT);
                    toast.show();
                }
            }, 1500);









        }
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
        setCurrentTime();
        long currentTime = timeParser.timeParserMethodForCurrentTime(PrefConfig.loadCurrentTime(this));

        if(currentTime >= ishaTime || currentTime >= 0 && currentTime < fazrTime){

            if(currentTime>=ishaTime){
                startTime = midNight - currentTime +fazrTime;
            }
            else{
                startTime = 0-currentTime + fazrTime;
            }

            timeLeftInMillies = startTime;
            startTime2 = (int) (midNight - ishaTime) + (int) fazrTime;

            if(startTime2!=PrefConfig.loadStartTimeProgressBar(this))
                PrefConfig.saveStartTimeProgressBar(this, startTime2);

            startTimer();



        }

        // fazr -> sunrise

        else if(currentTime >= fazrTime && currentTime < sunrise ){

            startTime = sunrise - currentTime;
            timeLeftInMillies = startTime;
            startTime2 = (int) sunrise - (int) fazrTime;

            if(startTime2!=PrefConfig.loadStartTimeProgressBar(this))
                PrefConfig.saveStartTimeProgressBar(this, startTime2);



            startTimer();

        }

        // sunrise -> dhuhr

        else if(currentTime >= sunrise && currentTime < dhuhrTime ){

            startTime = dhuhrTime - currentTime;
            timeLeftInMillies = startTime;

            startTime2 = (int) dhuhrTime - (int) sunrise;

            if(startTime2!=PrefConfig.loadStartTimeProgressBar(this))
                PrefConfig.saveStartTimeProgressBar(this, startTime2);



            startTimer();

        }

        else if(currentTime >= dhuhrTime && currentTime < asarTime){

            startTime = asarTime - currentTime;

            timeLeftInMillies = startTime;
            startTime2 = (int) asarTime - (int) dhuhrTime;

            if(startTime2!=PrefConfig.loadStartTimeProgressBar(this))
                PrefConfig.saveStartTimeProgressBar(this, startTime2);




            startTimer();

        }

        else if(currentTime >= asarTime && currentTime < magribTime){

            startTime = magribTime - currentTime;

            timeLeftInMillies = startTime;
            startTime2 = (int) magribTime - (int) asarTime;

            if(startTime2!=PrefConfig.loadStartTimeProgressBar(this))
                PrefConfig.saveStartTimeProgressBar(this, startTime2);



            startTimer();

        }

        else if(currentTime >= magribTime && currentTime < ishaTime){

            startTime = ishaTime - currentTime;

            timeLeftInMillies = startTime;
            startTime2 = (int) ishaTime - (int) magribTime;

            if(startTime2!=PrefConfig.loadStartTimeProgressBar(this))
                PrefConfig.saveStartTimeProgressBar(this, startTime2);



            startTimer();
        }

    }


    private void startTimer() {


        countDownTimer = new CountDownTimer(timeLeftInMillies, 1000) {

            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillies = millisUntilFinished;
                updateCountDownText();
                int fullTime = PrefConfig.loadStartTimeProgressBar(getApplicationContext());
                double barvalue = (fullTime-millisUntilFinished)*100/fullTime;
                progressBar.setProgress((int)barvalue);

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

            new Handler(Looper.getMainLooper()).postDelayed(() -> {
                convertLocation(finalLat, finalLong);
            }, 1000);

        }
    };

    // method to check for permissions
    private boolean checkPermissions() {
        return ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ;


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
        if(PrefConfig.loadLocationType(this)==0){
            try {

                if(lat != 0 && lon != 0){
                    addresses = geocoder.getFromLocation(lat ,lon, 1);

                }

                if(addresses.size()>0){
                    city = addresses.get(0).getLocality();
                    String country = addresses.get(0).getCountryName();

                    /* Saves the location to PrefConfig (SharedPreferences) */

                    PrefConfig.saveCurrentCity(getApplicationContext(), city);
                    PrefConfig.saveCurrentCountry(getApplicationContext(), country);
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
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
        currentTimeString = PrefConfig.loadCurrentTime(this);
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
            if(item.getItemId()==R.id.log){
                startActivity(new Intent(getApplicationContext(), LogActivity.class));
                overridePendingTransition(0, 0);
                return true;
            }
            else if(item.getItemId()==R.id.home){
                return true;
            }
            else if(item.getItemId()==R.id.duas){
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
        progressBar = (ProgressBar) findViewById(R.id.progress_bar);
        timerId = findViewById(R.id.timerId);
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

        city = city.substring(0, 1).toUpperCase() + city.substring(1);
        cityLocation.setText(city);
        sehriTimeId.setText(sehriAMPM);
        iftarTimeId.setText(iftarAMPM);
    }

    private void setQuote() {

        TimeParser timeParser = new TimeParser();
        setCurrentTime();
        long currentTime = timeParser.timeParserMethodForCurrentTime(PrefConfig.loadCurrentTime(this));
        QuoteGetter quoteGetter = new QuoteGetter(this, currentTime);
        quoteOfTheDay.setText(quoteGetter.quoteOfTheDayFunction());

    }

    private void setCurrentTime() {
        Calendar calendar1 = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("HH:mm:ss");
        currentTimeString = simpleDateFormat1.format(calendar1.getTime());
        PrefConfig.saveCurrentTime(getApplicationContext(), currentTimeString);
    }
    private void setNowAndNext() {

        NowAndNextPrayer nowAndNextPrayer = new NowAndNextPrayer(this);
        nowAndNextPrayer.setNowAndNext();
        haveYouPrayed.setText(nowAndNextPrayer.getHaveYouPrayed());
        nowPrayerName.setText(nowAndNextPrayer.getNowPrayerName());
        nextPrayerName.setText(nowAndNextPrayer.getNextPrayerName());
        nextPrayerTime.setText(nowAndNextPrayer.getNextPrayerTime());

    }
    public void changeLocation(View v){
        if(PrefConfig.loadLocationType(getApplicationContext())==1){
            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            final EditText edittext = new EditText(getApplicationContext());
            edittext.setGravity(1);

            alert.setMessage("Format : city,country\nExample : Dhaka,Bangladesh");
            alert.setTitle("Set Location");


            alert.setView(edittext);

            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {


                    edittedLocation = edittext.getText().toString();

                    List<String> locationList = Arrays.asList(edittedLocation.split("\\s*,\\s*"));
                    if(locationList.size()==2){
                        isLocationChanged = true;
                        finish();
                        startActivity(getIntent());
                    }
                    else{
                        Toast toast = Toast.makeText(getApplicationContext(),"Invalid Country or City",Toast.LENGTH_SHORT);
                        toast.show();
                    }


                }
            });

            alert.setNegativeButton("Cancle", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int whichButton) {

                }
            });

            alert.show();
        }








    }



}