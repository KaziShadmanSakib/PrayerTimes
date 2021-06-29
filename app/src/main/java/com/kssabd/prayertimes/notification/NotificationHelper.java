package com.kssabd.prayertimes.notification;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentResolver;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.kssabd.prayertimes.activity.CalendarDateLog;
import com.kssabd.prayertimes.R;
import com.kssabd.prayertimes.options.PrefConfig;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private final int importance;

    private NotificationManager mManager;
    Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.kalimba);

    public NotificationHelper(Context base) {
        super(base);
        importance = PrefConfig.loadCurrentNotificationType(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }



    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel;
        if(importance == 0){
            channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_LOW);
        }
        else if(importance == 2){
            channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);
        }
        else{
            channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_DEFAULT);
        }


        getManager().createNotificationChannel(channel);
    }

    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return mManager;
    }

    public NotificationCompat.Builder getChannelNotification(String title, String text ) {

        Intent clickedIntent = new Intent(this, CalendarDateLog.class);
        Calendar calendar = Calendar.getInstance();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(calendar.getTime());
        int dateInt = Integer.parseInt(date);
        dateInt +=1;
        date = String.valueOf(dateInt);
        clickedIntent.putExtra("clickedDate",date);
        PendingIntent clickedPendingIntent = PendingIntent.getActivities(this,1, new Intent[]{clickedIntent},PendingIntent.FLAG_UPDATE_CURRENT);

        if(title.equals("Sehri Time")){
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_android)
                    .setAutoCancel(true)
                    .setSound(sound);
        }
        else{
            return new NotificationCompat.Builder(getApplicationContext(), channelID)
                    .setContentTitle(title)
                    .setContentText(text)
                    .setSmallIcon(R.drawable.ic_android)
                    .setAutoCancel(true)
                    .setContentIntent(clickedPendingIntent)
                    .setSound(sound);
        }


    }

}
