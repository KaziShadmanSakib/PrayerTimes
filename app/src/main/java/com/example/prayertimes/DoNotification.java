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
            NotificationHelper.setContentTitle("Dhuhr has started");
            NotificationHelper.setContentText("Have you prayed Fajr?");
            startAlarm(c,prayerMiliSec[1]);
        }
        if(currentMiliSec>prayerMiliSec[1]&&currentMiliSec<prayerMiliSec[2]){
            NotificationHelper.setContentTitle("Asar has started");
            NotificationHelper.setContentText("Have you prayed Dhuhr?");
            startAlarm(c,prayerMiliSec[2]);
        }
        if(currentMiliSec>prayerMiliSec[2]&&currentMiliSec<prayerMiliSec[3]){
            NotificationHelper.setContentTitle("Magrib has started");
            NotificationHelper.setContentText("Have you prayed Asae?");
            startAlarm(c,prayerMiliSec[3]);
        }
        if(currentMiliSec>prayerMiliSec[3]&&currentMiliSec<prayerMiliSec[4]){
            NotificationHelper.setContentTitle("Isha has started");
            NotificationHelper.setContentText("Have you prayed Magrib?");
            startAlarm(c,prayerMiliSec[4]);
        }
        if(currentMiliSec>prayerMiliSec[4]&&currentMiliSec<24*3600*1000){
            NotificationHelper.setContentTitle("Fajr has started");
            NotificationHelper.setContentText("Have you prayed Isha?");
            startAlarm(c,prayerMiliSec[0]);
        }
        if(currentMiliSec>0&&currentMiliSec<prayerMiliSec[0]){
            NotificationHelper.setContentTitle("Fajr has started");
            NotificationHelper.setContentText("Have you prayed Isha?");
            startAlarm(c,prayerMiliSec[0]);
        }



        

    }

    private void getData() {
        PrayerTimeInMiliSecond prayerTimeToMiliSecond = new PrayerTimeInMiliSecond(_context);
        prayerTimeToMiliSecond.toMiliSec();

        Date currentTime = Calendar.getInstance().getTime();
        currentMiliSec = prayerTimeToMiliSecond.getCurrentTimeInMiliSec(currentTime);
        prayerMiliSec[0]=prayerTimeToMiliSecond.getFajrInMili();
        prayerMiliSec[1]=prayerTimeToMiliSecond.getDhuhrInMili();
        prayerMiliSec[2]=prayerTimeToMiliSecond.getAsarInMili();
        prayerMiliSec[3]=prayerTimeToMiliSecond.getMagribInMili();
        prayerMiliSec[4]=prayerTimeToMiliSecond.getIshaInMili();

        
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
