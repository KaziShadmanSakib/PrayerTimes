package com.example.prayertimes;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;


import androidx.core.app.NotificationCompat;

public class AlertReceiver extends BroadcastReceiver {
    private static String contentTitle = "prayer";
    private static String contentText = "Have you prayed";



    @Override
    public void onReceive(Context context, Intent intent) {


        NotificationHelper notificationHelper = new NotificationHelper(context);
        NotificationCompat.Builder nb = notificationHelper.getChannelNotification(contentTitle,contentText);
        notificationHelper.getManager().notify(1, nb.build());
    }

    public static void setContentText(String contentText) {
        AlertReceiver.contentText = contentText;
    }

    public static void setContentTitle(String contentTitle) {
        AlertReceiver.contentTitle = contentTitle;
    }
}