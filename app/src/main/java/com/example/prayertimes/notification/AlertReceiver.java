package com.example.prayertimes.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;


import androidx.core.app.NotificationCompat;

import com.example.prayertimes.options.PrefConfig;

public class AlertReceiver extends BroadcastReceiver {
    private static String contentTitle = "New prayer";
    private static String contentText = "Have you prayed the last prayer?";
    private static final String[] ALL_Prayers = {"Isha","Fajr","Dhuhr","Asar","Magrib","Isha"};



    @Override
    public void onReceive(Context context, Intent intent) {

        int index = PrefConfig.loadCurrentPrayerIndex(context);
        if(index<5) {
            contentTitle = ALL_Prayers[index + 1] + " has Started";
            contentText = "Have you prayed " + ALL_Prayers[index] + "?";
        }
        else{
            contentTitle = "Sunrise Time";
            contentText = "Fajr waqt has finished. Good morning";
        }
        if(PrefConfig.loadSehriAlarmConfig(context)==1){
            contentTitle = "Sehri Time";
            contentText = "Have you done your sehri";
        }

        long[] vibrate = { 0, 100, 200, 300 };


        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(contentTitle,contentText);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        nb.setSound(alarmSound);
        nb.setVibrate(vibrate);
        notificationHelper.getManager().notify(1, nb.build());
    }


}