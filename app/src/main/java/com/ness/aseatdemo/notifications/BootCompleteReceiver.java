package com.ness.aseatdemo.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static com.ness.aseatdemo.notifications.NotificationService.TAG;
import static com.ness.aseatdemo.notifications.NotificationService.TAG_MESSAGE;
import static com.ness.aseatdemo.notifications.NotificationService.TAG_MILLIS;

public class BootCompleteReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "boot complete triggered");

        SharedPreferences preferences
                = context.getApplicationContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);

        long millis = preferences.getLong(TAG_MILLIS, -1);
        String message = preferences.getString(TAG_MESSAGE, "null");

        if (millis >= System.currentTimeMillis()) {

            Log.d(TAG, "onReceive bootComplete: all good");

            //this workaround throws an error, so we start the alarmManager straight from the broadcastReceiver
//            Intent serviceIntent = new Intent(context, BackgroundService.class);
//            serviceIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            serviceIntent.putExtra(TAG_MESSAGE, message);
//            serviceIntent.putExtra(TAG_MILLIS, millis);
//
//            context.startService(serviceIntent);

            Intent serviceIntent = new Intent(context, NotificationService.class);
            serviceIntent.putExtra(TAG_MESSAGE, message);
            serviceIntent.putExtra(TAG_MILLIS, millis);

            PendingIntent servicePendingIntent = PendingIntent.getService(context,
                    1, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, servicePendingIntent);

            Log.d(TAG, "startCommand: alarm created");

        } else {
            Log.d(TAG, "onReceive bootComplete: something's not good. Millis: " + millis);
        }

    }
}