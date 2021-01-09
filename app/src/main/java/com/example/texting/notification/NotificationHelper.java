package com.example.texting.notification;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;

import androidx.core.app.NotificationCompat;

import com.example.texting.R;

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "channel 1";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "channel 2";
    public static int count = 0;
    private  NotificationManager nManager;


    public NotificationHelper(Context base) {
        super(base);
        count++;
        createChannels();
    }

    public void createChannels() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel1 = new NotificationChannel(channel1ID, channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
            channel1.enableLights(true);
            channel1.enableVibration(true);
            channel1.setLightColor(R.color.colorPrimary);
            channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

            getManager().createNotificationChannel(channel1);
        }
    }

    public NotificationManager getManager() {
        if(nManager == null) {
            nManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        }
        return nManager;
    }

//    public NotificationCompat.Builder getChannelNotification(String title, String message) {
//        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
//                .setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.ic_notification);
//    }

//    public NotificationCompat.Builder getChannelNotification(String title, String message) {
//        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
//                .setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.ic_notification);
//    }

    public NotificationCompat.Builder getChannelNotification(String title, String message, PendingIntent pIntent) {
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle(title).setContentText(message).setSmallIcon(R.drawable.ic_notification).setAutoCancel(true);
//                .setContentIntent(pIntent)
    }
}
