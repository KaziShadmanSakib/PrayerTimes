package com.example.prayertimes.datetime;


import android.content.Context;

import com.example.prayertimes.others.PrefConfig;

import java.text.SimpleDateFormat;
import java.util.Date;


public class PrayerTimeInMiliSecond extends TimeParser {
    Context _context;
    String fazrNamazTime, dhuhrNamazTime, asarNamazTime, magribNamazTime, ishaNamazTime,sunriseTime,sunsetTime,imsakTime;
    int[] prayerMiliSec = new int[8];


    public PrayerTimeInMiliSecond(Context applicationContext) {
        _context = applicationContext;
    }

    public void toMiliSec() {
        getTime();
        getConvertedTime();
    }

    private void getConvertedTime() {


        prayerMiliSec[0] = (int) timeParserMethod(fazrNamazTime);
        prayerMiliSec[1] = (int) timeParserMethod(dhuhrNamazTime);
        prayerMiliSec[2] = (int) timeParserMethod(asarNamazTime);
        prayerMiliSec[3] = (int) timeParserMethod(magribNamazTime);
        prayerMiliSec[4] = (int) timeParserMethod(ishaNamazTime);
        prayerMiliSec[5] = (int) timeParserMethod(sunriseTime);
        prayerMiliSec[6] = (int) timeParserMethod(sunsetTime);
        prayerMiliSec[7] = (int) timeParserMethod(imsakTime);




    }


    private void getTime(){

        fazrNamazTime = PrefConfig.loadFajrTime(_context);
        dhuhrNamazTime = PrefConfig.loadDhuhrTime(_context);
        asarNamazTime = PrefConfig.loadAsarTime(_context);
        magribNamazTime = PrefConfig.loadMagribTime(_context);
        ishaNamazTime = PrefConfig.loadIshaTime(_context);
        sunriseTime = PrefConfig.loadSunriseTime(_context);
        sunsetTime = PrefConfig.loadSunsetTime(_context);
        imsakTime = PrefConfig.loadImsakTime(_context);

    }
    public Integer getFajrInMili(){
        return prayerMiliSec[0];
    }
    public Integer getDhuhrInMili(){
        return prayerMiliSec[1];
    }
    public Integer getAsarInMili(){
        return prayerMiliSec[2];
    }
    public Integer getMagribInMili(){
        return prayerMiliSec[3];
    }
    public Integer getIshaInMili(){
        return prayerMiliSec[4];
    }
    public Integer getSunriseInMili(){
        return prayerMiliSec[5];
    }
    public Integer getSunsetInMili(){
        return prayerMiliSec[6];
    }
    public Integer getImsakInMili(){
        return prayerMiliSec[7];
    }

    public Integer getCurrentTimeInMiliSec(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentTimeString = sdf.format(date);
        int currentSec = (int) timeParserMethodForCurrentTime(currentTimeString);
        return currentSec;

    }

}
