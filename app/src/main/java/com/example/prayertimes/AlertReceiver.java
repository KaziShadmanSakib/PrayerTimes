package com.example.prayertimes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;


import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    private static String contentTitle = "New prayer";
    private static String contentText = "Have you prayed the last prayer?";
    private static final String[] ALL_Prayers = {"Isha","Fajr","Dhuhr","Asar","Magrib","Isha"};



    @Override
    public void onReceive(Context context, Intent intent) {

        int index = PrefConfig.loadCurrentPrayerIndex(context);
        contentTitle = ALL_Prayers[index+1]+" has Started";
        contentText = "Have you prayed "+ALL_Prayers[index]+"?";

        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(contentTitle,contentText);
        notificationHelper.getManager().notify(1, nb.build());
    }


}