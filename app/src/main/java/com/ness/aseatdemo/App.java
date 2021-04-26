package com.ness.aseatdemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.util.Log;

import com.ness.aseatdemo.notifications.BootCompleteReceiver;

import static com.ness.aseatdemo.notifications.NotificationService.TAG;
import static com.ness.aseatdemo.notifications.NotificationService.TAG_MILLIS;

public class App extends Application {
    public static final String NOTIFICATION_CHANNEL_ID = "reminders_channel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();

    }

    private void createNotificationChannel() {
        NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                "Reminders channel", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("This channel is used to receive reminder notifications for your booked seats");

        NotificationManager manager = getSystemService(NotificationManager.class);

        manager.createNotificationChannel(channel);
    }
}
