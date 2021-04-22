package com.ness.aseatdemo.notifications;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static com.ness.aseatdemo.notifications.NotificationService.TAG;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "boot complete triggered");

        SharedPreferences preferences
                = context.getApplicationContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);
        long timeStamp = preferences.getLong("notification", -1);
        String message = preferences.getString("message", "null");

        if (timeStamp >= System.currentTimeMillis()) {

            Intent serviceIntent = new Intent(context, NotificationService.class);
            intent.putExtra("message", message);

            PendingIntent servicePendingIntent = PendingIntent.getService(context,
                    1, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

            AlarmManager manager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

            manager.set(AlarmManager.RTC_WAKEUP, timeStamp, servicePendingIntent);
            Log.d(TAG, "notification alarm set " + message);

        }

    }
}
