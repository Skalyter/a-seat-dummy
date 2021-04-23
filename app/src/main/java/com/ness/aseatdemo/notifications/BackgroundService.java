package com.ness.aseatdemo.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import static com.ness.aseatdemo.notifications.NotificationService.TAG;
import static com.ness.aseatdemo.notifications.NotificationService.TAG_MESSAGE;
import static com.ness.aseatdemo.notifications.NotificationService.TAG_MILLIS;

public class BackgroundService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        String message = intent.getStringExtra(TAG_MESSAGE);
        long millis = intent.getLongExtra(TAG_MILLIS, -1);
        if (millis!= -1){
            Log.d(TAG, "onStartCommand backgroundService: all good");
            startCommand(message, millis);
        } else  {
            Log.d(TAG, "onStartCommand backgroundService: something's wrong. Millis: " + millis);
        }
        return START_NOT_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void startCommand(String message, long millis) {

        Intent serviceIntent = new Intent(this, NotificationService.class);
        serviceIntent.putExtra(TAG_MESSAGE, message);

        PendingIntent servicePendingIntent = PendingIntent.getService(this,
                1, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, servicePendingIntent);

        Log.d(TAG, "startCommand: alarm created");

    }
}
