package com.example.prayertimes;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import java.util.Calendar;
import java.util.Date;

public class DoNotification {
    Context _context;
    int[] prayerMiliSec = new int[5];
    int currentMiliSec;

    public DoNotification(Context applicationContext) {
        
        _context = applicationContext;
        
    }

    public void setNotification(){
        
        getData();

        Calendar c = Calendar.getInstance();
        

        if(currentMiliSec>prayerMiliSec[0]&&currentMiliSec<prayerMiliSec[1]){
            startAlarm(c,prayerMiliSec[1]);
            AlertReceiver.setContentTitle("Dhuhr has started");
            AlertReceiver.setContentText("Have you prayed Fajr?");
        }
        if(currentMiliSec>prayerMiliSec[1]&&currentMiliSec<prayerMiliSec[2]){
            startAlarm(c,prayerMiliSec[2]);
            AlertReceiver.setContentTitle("Asar has started");
            AlertReceiver.setContentText("Have you prayed Dhuhr?");
        }
        if(currentMiliSec>prayerMiliSec[2]&&currentMiliSec<prayerMiliSec[3]){
            startAlarm(c,prayerMiliSec[3]);
            AlertReceiver.setContentTitle("Magrib has started");
            AlertReceiver.setContentText("Have you prayed Asar?");
        }
        if(currentMiliSec>prayerMiliSec[3]&&currentMiliSec<prayerMiliSec[4]){
            startAlarm(c,prayerMiliSec[4]);
            AlertReceiver.setContentTitle("Isha has started");
            AlertReceiver.setContentText("Have you prayed Magrib?");
        }
        if(currentMiliSec>prayerMiliSec[4]&&currentMiliSec<24*3600*1000){
            startAlarm(c,prayerMiliSec[0]);
            AlertReceiver.setContentTitle("Fajr has started");
            AlertReceiver.setContentText("Have you prayed Isha?");
        }
        if(currentMiliSec>0&&currentMiliSec<prayerMiliSec[0]){
            startAlarm(c,prayerMiliSec[0]);
            AlertReceiver.setContentTitle("Fajr has started");
            AlertReceiver.setContentText("Have you prayed Isha?");
        }



        

    }

    private void getData() {
        ConvertToMiliSecond convertToMiliSecond = new ConvertToMiliSecond(_context);
        convertToMiliSecond.toMiliSec();

        Date currentTime = Calendar.getInstance().getTime();
        currentMiliSec = convertToMiliSecond.getCurrentTimeInMiliSec(currentTime);
        prayerMiliSec[0]=convertToMiliSecond.getFajrInMili();
        prayerMiliSec[1]=convertToMiliSecond.getDhuhrInMili();
        prayerMiliSec[2]=convertToMiliSecond.getAsarInMili();
        prayerMiliSec[3]=convertToMiliSecond.getMagribInMili();
        prayerMiliSec[4]=convertToMiliSecond.getIshaInMili();

        
    }



    private void startAlarm(Calendar c,int miliSecond) {


        miliSecond = miliSecond/1000;
        int hour = miliSecond/3600;
        int min = (miliSecond%3600)/60;
        c.set(Calendar.HOUR_OF_DAY, hour);
        c.set(Calendar.MINUTE, min);
        c.set(Calendar.SECOND, 0);

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
