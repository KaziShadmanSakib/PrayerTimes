package com.kssabd.prayertimes.datetime;

import android.content.Context;

import com.kssabd.prayertimes.database.Contact;
import com.kssabd.prayertimes.database.DatabaseHandler;
import com.kssabd.prayertimes.options.PrefConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class NowAndNextPrayer {
    Context context;
    String fajrNamazAMPM, sunriseAMPM, dhuhrNamazAMPM, asarNamazAMPM, sunsetAMPM, magribNamazAMPM, ishaNamazAMPM, imsakTimeAMPM;
     String haveYouPrayed;
     String nowPrayerName;
     String nextPrayerName;
     String nextPrayerTime;
     public static String day = "today";
    private static final String[] ALL_Prayers = {"Isha","Fajr","Dhuhr","Asar","Magrib","Isha"};
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

            setPrayed("Fajr");
            nowPrayerName  = "Now - Fajr";
            nextPrayerName = "Sunrise";
            nextPrayerTime = sunriseAMPM;

        }

        if(currentTime1 >= sunrise && currentTime1 < dhuhrTime){


            setPrayed("Fajr");
            nowPrayerName = "Good Morning";
            nextPrayerName = "Dhuhr";
            if(getWeekDay()=="Friday"){
                nextPrayerName = "Jumu'ah";
            }
            nextPrayerTime = dhuhrNamazAMPM;


        }


        if(currentTime1 >= dhuhrTime && currentTime1 < asarTime){

            setPrayed("Dhuhr");
            nowPrayerName = "Now - Dhuhr";
            if(getWeekDay()=="Friday"){
                nowPrayerName = "Now - Jumu'ah";
            }
            nextPrayerName = "Asar";
            nextPrayerTime = asarNamazAMPM;

        }

        if(currentTime1 >= asarTime && currentTime1 < magribTime){

            setPrayed("Asar");
            nowPrayerName = "Now - Asar";
            nextPrayerName = "Magrib";
            nextPrayerTime = magribNamazAMPM;

        }

        if(currentTime1 >= magribTime && currentTime1 <ishaTime){

            setPrayed("Magrib");
            nowPrayerName = "Now - Magrib";
            nextPrayerName = "Isha";
            nextPrayerTime = ishaNamazAMPM;

        }

        if(currentTime1 >= ishaTime && currentTime1 > midNight){
            setPrayed("Isha");
            nowPrayerName = "Now - Isha";
            nextPrayerName = "Fajr";
            nextPrayerTime = fajrNamazAMPM;
        }

        if(currentTime1 >= midNight && currentTime1 < fazrTime){

            setPrayed("Ishaa");
            nowPrayerName = "Now - Midnight";
            nextPrayerName = "Fajr";
            nextPrayerTime = fajrNamazAMPM;

        }


    }

    private void setPrayed(String prayer) {

        DatabaseHandler databaseHandler = new DatabaseHandler(context);
        Contact contact;
        String s = "Have you prayed "+prayer +"?";
        if(prayer=="Ishaa"){
            contact = databaseHandler.getContact(getLastDate());
            s = "Have you prayed Isha?";

        }
        else{
            contact = databaseHandler.getContact(currentDateSet());

        }
        if(getWeekDay()=="Friday"){
            s = "Have you prayed Jumu'ah?";
        }



        if(contact!=null) {


            if((prayer == "Magrib"&&contact.getMagrib())
                    ||(prayer == "Asar"&&contact.getAsar())
                    ||(prayer == "Dhuhr"&&contact.getDhuhr())
                    ||(prayer == "Fajr"&&contact.getFajr())
                    ||(prayer == "Isha"&&contact.getIsha())
                    ||(prayer == "Ishaa"&&contact.getIsha())){

                s = "You've prayed "+ prayer;
                if(prayer=="Ishaa"){
                    s = "You've prayed isha";
                }


                if(prayer=="Dhuhr"){

                    if(getWeekDay()=="Friday"){
                        s = "You've prayed Jumu'ah";
                    }

                }
            }



        }

        haveYouPrayed = s;

    }
    private String currentDateSet() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(calendar.getTime());
        return  date;

    }
    private String getLastDate(){
        String s = currentDateSet();
        int dateInt = Integer.parseInt(s);
        dateInt--;
        s = String.valueOf(dateInt);
        return s;
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
    public static String getWeekDay(){
        if(day == "today"){
            Date date=new Date();
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            day = new SimpleDateFormat("EEEE").format(date);

        }


        return day;
    }


}
