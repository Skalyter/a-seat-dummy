package com.ness.aseatdemo.notifications;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;

import com.ness.aseatdemo.App;
import com.ness.aseatdemo.MainActivity;
import com.ness.aseatdemo.R;

import java.util.Calendar;

import static android.content.Context.NOTIFICATION_SERVICE;
import static com.ness.aseatdemo.notifications.NotificationService.TAG;

public class AlarmTrigger {

    public static final String KEY_MILLIS="millis";
    public static final String KEY_MESSAGE="message";

    public static void createNotification(Context context, String message, long millis) {
        Log.d(TAG, "createNotification: ");

        Intent serviceIntent = new Intent(context, NotificationService.class);
        serviceIntent.putExtra(KEY_MESSAGE, message);

        PendingIntent servicePendingIntent = PendingIntent.getService(context,
                1, serviceIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, millis, servicePendingIntent);

        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(millis);
        Log.d(TAG, String.format("notification set for %d:%d:%d", cal.get(Calendar.HOUR), cal.get(Calendar.MINUTE), cal.get(Calendar.SECOND)));

        addNotificationTimeToSharedPref(context, millis, message);
    }


    private static void addNotificationTimeToSharedPref(Context context, long millis, String message) {
        SharedPreferences sharedPreferences
                = context.getApplicationContext().getSharedPreferences("notifications", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(KEY_MILLIS, millis);
        editor.putString(KEY_MESSAGE, message);
        editor.apply();
        Log.d(TAG, "addNotificationTimeToSharedPref:" + message);
    }


}
