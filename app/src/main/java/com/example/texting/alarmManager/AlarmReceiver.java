package com.example.texting.alarmManager;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.example.texting.EditActivity;
import com.example.texting.notification.NotificationHelper;

public class AlarmReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String title = intent.getExtras().getString("title");
        if (title.equals("")) title = "<Без названия>";
        String text = intent.getExtras().getString("text");
        int id = intent.getExtras().getInt("id");
        NotificationHelper nHelper = new NotificationHelper(context);

        try {
            Intent newIntent = new Intent(context, EditActivity.class);
            PendingIntent pIntent = PendingIntent.getActivity(context, 0, newIntent, 0);
            NotificationCompat.Builder nb = nHelper.getChannelNotification(title, text, pIntent);
            nHelper.getManager().notify(id, nb.build());
        }
        catch (Exception e) {
            Log.d("MyLog", "AlarmReceiver - onReceive");
            Log.d("MyLog", e.toString());
        }
    //    NotificationCompat.Builder nb = NotificationHelper.getChannelNotification();
    }

//    public void setOnetimeTimer(Context context) {
//        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//        Intent intent = new Intent(context, AlarmReceiver.class);
//        intent.putExtra("onetime", Boolean.TRUE);//Задаем параметр интента
//        PendingIntent pi = PendingIntent.getBroadcast(context, 0, intent, 0);
//        am.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), pi);
//    }
}
