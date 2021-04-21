package com.ness.aseatdemo;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import static com.ness.aseatdemo.notifications.NotifyService.NOTIFICATION_CHANNEL_ID;

public class App extends Application {

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
