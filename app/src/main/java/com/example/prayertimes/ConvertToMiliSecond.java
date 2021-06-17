package com.example.prayertimes;


import android.content.Context;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


public class ConvertToMiliSecond {
    Context _context;
    String fazrNamazTime, dhuhrNamazTime, asarNamazTime, magribNamazTime, ishaNamazTime;
    int[] prayerHour = new int[5];
    int[] prayerMin = new int[5];
    int[] prayerMiliSec = new int[5];


    public ConvertToMiliSecond(Context applicationContext) {
        _context = applicationContext;
    }

    public void toMiliSec() {
        getTime();
        getConvertedTime();
    }

    private void getConvertedTime() {
        prayerHour[0] = convertTimeHour(fazrNamazTime);
        prayerHour[1] = convertTimeHour(dhuhrNamazTime);
        prayerHour[2] = convertTimeHour(asarNamazTime);
        prayerHour[3] = convertTimeHour(magribNamazTime);
        prayerHour[4] = convertTimeHour(ishaNamazTime);

        prayerMin[0] = convertTimeMin(fazrNamazTime);
        prayerMin[1] = convertTimeMin(dhuhrNamazTime);
        prayerMin[2] = convertTimeMin(asarNamazTime);
        prayerMin[3] = convertTimeMin(magribNamazTime);
        prayerMin[4] = convertTimeMin(ishaNamazTime);

        for(int i = 0 ; i < 5 ;i++){
            prayerMiliSec[i] = toSeconds(prayerHour[i],prayerMin[i])*1000;
        }
    }

    private Integer convertTimeHour(String timeToParse) {
        String hour = timeToParse.substring(0,2);
        return  Integer.parseInt(hour);

    }
    private Integer convertTimeMin(String timeToParse) {
        String min = timeToParse.substring(3,5);
        return Integer.parseInt(min);
    }
    private Integer toSeconds(Integer hour, Integer min){
        return  hour*3600+min*60;
    }


    private void getTime(){

        fazrNamazTime = PrefConfig.loadFajrTime(_context);
        dhuhrNamazTime = PrefConfig.loadDhuhrTime(_context);
        asarNamazTime = PrefConfig.loadAsarTime(_context);
        magribNamazTime = PrefConfig.loadMagribTime(_context);
        ishaNamazTime = PrefConfig.loadIshaTime(_context);

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
    public Integer getCurrentTimeInMiliSec(Date date){

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
        String currentTimeString = sdf.format(date);
        int currentHour = convertTimeHour(currentTimeString);
        int currentMin = convertTimeMin(currentTimeString);
        int currentSec = toSeconds(currentHour,currentMin);
        return currentSec*1000;

    }

}
