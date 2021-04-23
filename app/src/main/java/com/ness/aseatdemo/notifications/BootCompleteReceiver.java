package com.ness.aseatdemo.notifications;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.util.Log;

import static com.ness.aseatdemo.notifications.AlarmTrigger.KEY_MESSAGE;
import static com.ness.aseatdemo.notifications.AlarmTrigger.KEY_MILLIS;
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
            Intent serviceIntent = new Intent(context, BackgroundService.class);
            serviceIntent.putExtra(TAG_MESSAGE, message);
            intent.putExtra(TAG_MILLIS, millis);

            context.startService(serviceIntent);
            context.bindService(serviceIntent, new ServiceConnection() {
                @Override
                public void onServiceConnected(ComponentName name, IBinder service) {

                }

                @Override
                public void onServiceDisconnected(ComponentName name) {

                }
            }, Context.BIND_AUTO_CREATE);

//              or this one which creates the alarm manager straight ahead, without any other service

//            Intent serviceIntent = new Intent(context, NotificationService.class);
//            serviceIntent.putExtra(TAG_MESSAGE, message);
//
//            PendingIntent servicePendingIntent = PendingIntent.getService(context,
//                    1, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);
//
//            AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);
//
//            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, servicePendingIntent);
//
//            Log.d(TAG, "startCommand: alarm created");

        } else {
            Log.d(TAG, "onReceive bootComplete: something's not good. Millis: " + millis);
        }

    }
}
