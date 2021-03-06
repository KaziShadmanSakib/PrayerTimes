package com.kssabd.prayertimes.notification;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import com.kssabd.prayertimes.datetime.PrayerTimeInMiliSecond;
import com.kssabd.prayertimes.options.PrefConfig;

import java.util.Calendar;
import java.util.Date;

public class DoNotification {
    Context _context;
    int[] prayerMiliSec = new int[5];
    int currentMiliSec;
    int sunrise;


    public DoNotification(Context applicationContext) {
        
        _context = applicationContext;
        
    }

    public void setNotification(){
        
        getData();

        Calendar c = Calendar.getInstance();
        for(int i = 0 ; i < 4 ; i++){
            if(currentMiliSec>prayerMiliSec[i]&&currentMiliSec<prayerMiliSec[i+1]){
                PrefConfig.saveCurrentPrayerIndex(_context,i+1);
                startAlarm(c,prayerMiliSec[i+1]);
            }
        }
        //fajr notification
        if((currentMiliSec>prayerMiliSec[4]&&currentMiliSec<24*3600*1000)||(currentMiliSec>0&&currentMiliSec<prayerMiliSec[0])){

            PrefConfig.saveCurrentPrayerIndex(_context,0);
            startAlarm(c,prayerMiliSec[0]);
        }
        //sunrise notification
        if(currentMiliSec>prayerMiliSec[0]&&currentMiliSec<sunrise){
            PrefConfig.saveCurrentPrayerIndex(_context,5);
            startAlarm(c,sunrise);
        }
        //sahri notification
        if(PrefConfig.loadSehriAlarmConfig(_context)==1){
            int time = PrefConfig.loadSehriAlarmtime(_context);
            time = time*60000;
            if((currentMiliSec>prayerMiliSec[4]&&currentMiliSec<24*3600*1000)||(currentMiliSec>0&&currentMiliSec<prayerMiliSec[0]-time)){
                PrefConfig.saveCurrentPrayerIndex(_context,0);
                startAlarm(c,prayerMiliSec[0]-time);
            }
            else if(currentMiliSec>(prayerMiliSec[0]-time)&&currentMiliSec<prayerMiliSec[0]){
                PrefConfig.saveCurrentPrayerIndex(_context,0);
                startAlarm(c,prayerMiliSec[0]);
            }
        }



        

    }

    private void getData() {
        PrayerTimeInMiliSecond prayerTimeToMiliSecond = new PrayerTimeInMiliSecond(_context);
        prayerTimeToMiliSecond.toMiliSec();

        Date currentTime = Calendar.getInstance().getTime();
        currentMiliSec  =prayerTimeToMiliSecond.getCurrentTimeInMiliSec(currentTime);
        prayerMiliSec[0]=prayerTimeToMiliSecond.getFajrInMili();
        prayerMiliSec[1]=prayerTimeToMiliSecond.getDhuhrInMili();
        prayerMiliSec[2]=prayerTimeToMiliSecond.getAsarInMili();
        prayerMiliSec[3]=prayerTimeToMiliSecond.getMagribInMili();
        prayerMiliSec[4]=prayerTimeToMiliSecond.getIshaInMili();
        sunrise = prayerTimeToMiliSecond.getSunriseInMili();

        
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
        PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        if (c.before(Calendar.getInstance())) {
            c.add(Calendar.DATE, 1);
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, c.getTimeInMillis(), pendingIntent);

    }
    public void cancelAlarm() {
        AlarmManager alarmManager = (AlarmManager) _context.getSystemService(Context.ALARM_SERVICE);
        Intent intent = new Intent(_context, AlertReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(_context, 1, intent, 0);
        alarmManager.cancel(pendingIntent);
    }
}
