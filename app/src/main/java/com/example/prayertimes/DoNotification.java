package com.example.prayertimes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DoNotification {
    Context _context;
    String fazrNamazTime, dhuhrNamazTime, asarNamazTime, magribNamazTime, ishaNamazTime;
    int[] prayerHour = new int[5];
    int[] prayerMin = new int[5];
    int[] prayerSec = new int[5];
    

    public DoNotification(Context applicationContext) {
        _context = applicationContext;
    }

    public void setNotification(){
        getTime();
        getConvertedTime();

        Date currentTime = Calendar.getInstance().getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm");
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, 04);
        c.set(Calendar.MINUTE, 14);
        c.set(Calendar.SECOND,00 );
        startAlarm(c);

        String currentTimeString = sdf.format(currentTime);
        int currentHour = convertTimeHour(currentTimeString);
        int currentMin = convertTimeMin(currentTimeString);
        int currentSec = toSeconds(currentHour,currentMin);

        if(currentSec>prayerSec[0]&&currentSec<prayerSec[1]){
            //runNotification(prayerHour[1],prayerMin[1]);
        }
        if(currentSec>prayerSec[1]&&currentSec<prayerSec[2]){
            runNotification(prayerHour[2],prayerMin[2]);
        }
        if(currentSec>prayerSec[2]&&currentSec<prayerSec[3]){
            runNotification(prayerHour[3],prayerMin[3]);
        }
        if(currentSec>prayerSec[3]&&currentSec<prayerSec[4]){
            runNotification(prayerHour[4],prayerMin[4]);
        }
        if(currentSec>prayerSec[4]&&currentSec<24*3600){
            runNotification(prayerHour[0],prayerMin[0]);
        }
        if(currentSec>0&&currentSec<prayerSec[0]){
            runNotification(prayerHour[0],prayerMin[0]);
        }


        

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
            prayerSec[i] = toSeconds(prayerHour[i],prayerMin[i]);
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
    public void runNotification(int hour,int min){
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 00);
        startAlarm(c);
    }
    private void startAlarm(Calendar c) {
        AlarmManager alarmManager =  (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);

        Intent intent = new Intent(_context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, 1, intent, 0);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }
    private void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(_context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
