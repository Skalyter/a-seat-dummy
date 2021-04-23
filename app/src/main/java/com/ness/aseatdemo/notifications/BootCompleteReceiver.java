package com.ness.aseatdemo.notifications;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;

import static com.ness.aseatdemo.notifications.AlarmTrigger.KEY_MESSAGE;
import static com.ness.aseatdemo.notifications.AlarmTrigger.KEY_MILLIS;
import static com.ness.aseatdemo.notifications.NotificationService.TAG;

public class BootCompleteReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.d(TAG, "boot complete triggered");

        SharedPreferences preferences
                = context.getApplicationContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);

        long millis = preferences.getLong(KEY_MILLIS, -1);
        String message = preferences.getString(KEY_MESSAGE, "null");

        Intent intentService = new Intent(context, NotificationService.class);
        intentService.putExtra(KEY_MESSAGE, message);
        intentService.putExtra(KEY_MILLIS, millis);

        context.startService(intentService);
//        AlarmTrigger.createNotificationFromBroadcast(context, message, millis);
//        if (millis >= System.currentTimeMillis()) { }

    }
}
