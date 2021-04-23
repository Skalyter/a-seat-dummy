package com.ness.aseatdemo.notifications;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Color;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.ness.aseatdemo.App;
import com.ness.aseatdemo.MainActivity;
import com.ness.aseatdemo.R;

public class NotificationService extends Service {

    public static final String TAG = "NotificationService";

    public static final String TAG_MILLIS = "millis";
    public static final String TAG_MESSAGE = "message";

    private String message;
    private NotificationManager notificationManager;

    @Override
    public void onCreate() {
        notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        message = intent.getStringExtra(TAG_MESSAGE);

        Log.i(TAG, "Received start id  " + startId + ": " + intent);

        showNotification();

        return START_NOT_STICKY;
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private void showNotification() {

        //todo fix show notification when app is force closed (kinda fixed?)
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0,
                new Intent(this, MainActivity.class), PendingIntent.FLAG_UPDATE_CURRENT);

        Notification notification = new Notification.Builder(this, App.NOTIFICATION_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_ness_logo)
                .setContentTitle("Seat reservation reminder")
                .setColor(Color.BLUE)
                .setContentText(message)
                .setContentIntent(contentIntent)
                .setAutoCancel(true)
                .setShowWhen(true)
                .build();
        notificationManager.notify(1, notification);
    }
}
