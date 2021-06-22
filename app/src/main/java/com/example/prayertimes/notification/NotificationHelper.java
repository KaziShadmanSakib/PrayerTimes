package com.example.prayertimes.notification;

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

import com.example.prayertimes.activity.CalendarDateLog;
import com.example.prayertimes.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class NotificationHelper extends ContextWrapper {
    public static final String channelID = "channelID";
    public static final String channelName = "Channel Name";
    private static String contentTitle = "prayer";
    private static String contentText = "Have you prayed";

    private NotificationManager mManager;
    Uri sound = Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + getPackageName() + "/" + R.raw.kalimba);

    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            createChannel();
        }
    }

    public static void setContentTitle(String contentTitle) {
         NotificationHelper.contentTitle = contentTitle;

    }

    public static void setContentText(String contentText) {
        NotificationHelper.contentText = contentText;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void createChannel() {
        NotificationChannel channel = new NotificationChannel(channelID, channelName, NotificationManager.IMPORTANCE_HIGH);



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
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMdd");
        String date = simpleDateFormat.format(calendar.getTime());
        int dateInt = Integer.parseInt(date);
        dateInt +=1;
        date = String.valueOf(dateInt);
        clickedIntent.putExtra("clickedDate",date);
        PendingIntent clickedPendingIntent = PendingIntent.getActivities(this,1, new Intent[]{clickedIntent},PendingIntent.FLAG_UPDATE_CURRENT);
        return new NotificationCompat.Builder(getApplicationContext(), channelID)
                .setContentTitle(title)
                .setContentText(text)
                .setSmallIcon(R.drawable.ic_android)
                .setAutoCancel(true)
                .setContentIntent(clickedPendingIntent)
                .setSound(sound);

    }

}
