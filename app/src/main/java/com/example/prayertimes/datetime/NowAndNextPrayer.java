package com.example.prayertimes.datetime;

import android.content.Context;

import com.example.prayertimes.others.PrefConfig;

import java.util.Calendar;
import java.util.Date;

public class NowAndNextPrayer {
    Context context;
    String fajrNamazAMPM, sunriseAMPM, dhuhrNamazAMPM, asarNamazAMPM, sunsetAMPM, magribNamazAMPM, ishaNamazAMPM, imsakTimeAMPM;
     String haveYouPrayed;
     String nowPrayerName;
     String nextPrayerName;
     String nextPrayerTime;
    public NowAndNextPrayer(Context c){
        context = c;
    }
    public void setNowAndNext() {

        long midNight = 0;

        /* Converting String time to Milliseconds */

        PrayerTimeInMiliSecond prayerTimeToMiliSecond = new PrayerTimeInMiliSecond(context);
        prayerTimeToMiliSecond.toMiliSec();

        Date currentTime = Calendar.getInstance().getTime();
        long currentTime1 =prayerTimeToMiliSecond.getCurrentTimeInMiliSec(currentTime);
        long fazrTime = prayerTimeToMiliSecond.getFajrInMili();
        long sunrise = prayerTimeToMiliSecond.getSunriseInMili();
        long dhuhrTime = prayerTimeToMiliSecond.getDhuhrInMili();
        long asarTime = prayerTimeToMiliSecond.getAsarInMili();
        long sunset = prayerTimeToMiliSecond.getSunsetInMili();
        long magribTime = prayerTimeToMiliSecond.getMagribInMili();
        long ishaTime = prayerTimeToMiliSecond.getIshaInMili();
        long imsak = prayerTimeToMiliSecond.getImsakInMili();




        fajrNamazAMPM = PrefConfig.loadFajrTimeAMPM(context);
        sunriseAMPM = PrefConfig.loadSunriseTimeAMPM(context);
        dhuhrNamazAMPM = PrefConfig.loadDhuhrTimeAMPM(context);
        asarNamazAMPM = PrefConfig.loadAsarTimeAMPM(context);
        sunsetAMPM = PrefConfig.loadSunsetTimeAMPM(context);
        magribNamazAMPM = PrefConfig.loadMagribTimeAMPM(context);
        ishaNamazAMPM = PrefConfig.loadIshaTimeAMPM(context);


        if(currentTime1 >= fazrTime && currentTime1 < sunrise){

            haveYouPrayed  = "Get ready for the next Prayer";
            nowPrayerName  = "Now - Fajr";
            nextPrayerName = "Sunrise";
            nextPrayerTime = sunriseAMPM;

        }

        if(currentTime1 >= sunrise && currentTime1 < dhuhrTime){

            haveYouPrayed = "Have you prayed Fajr?";
            nowPrayerName = "Good Morning";
            nextPrayerName = "Dhuhr";
            nextPrayerTime = dhuhrNamazAMPM;


        }


        if(currentTime1 >= dhuhrTime && currentTime1 < asarTime){

            haveYouPrayed = "Have you prayed Fajr?";
            nowPrayerName = "Now - Dhuhr";
            nextPrayerName = "Asar";
            nextPrayerTime = asarNamazAMPM;

        }

        if(currentTime1 >= asarTime && currentTime1 < magribTime){

            haveYouPrayed = "Have you prayed Dhuhr?";
            nowPrayerName = "Now - Asar";
            nextPrayerName = "Magrib";
            nextPrayerTime = magribNamazAMPM;

        }

        if(currentTime1 >= magribTime && currentTime1 <ishaTime){

            haveYouPrayed = "Have you prayed Asar?";
            nowPrayerName = "Now - Magrib";
            nextPrayerName = "Isha";
            nextPrayerTime = ishaNamazAMPM;

        }

        if(currentTime1 >= ishaTime && currentTime1 > midNight){
            haveYouPrayed = "Have you prayed Magrib?";
            nowPrayerName = "Now - Isha";
            nextPrayerName = "Fajr";
            nextPrayerTime = fajrNamazAMPM;
        }

        if(currentTime1 >= midNight && currentTime1 < fazrTime){

            haveYouPrayed = "Have you prayed Isha?";
            nowPrayerName = "Now - Midnight";
            nextPrayerName = "Fajr";
            nextPrayerTime = fajrNamazAMPM;

        }


    }

    public String getHaveYouPrayed(){
        return haveYouPrayed;
    }
    public String getNowPrayerName(){
        return nowPrayerName;
    }
    public String getNextPrayerName(){
        return nextPrayerName;
    }
    public String getNextPrayerTime(){
        return nextPrayerTime;
    }


}
