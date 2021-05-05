package com.example.td1_plantes;

import android.app.Application;
import android.app.NotificationManager;
import android.content.Context;
import android.os.Build;

import com.example.td1_plantes.controler.activities.takepicture.TakePictureActivity;
import com.example.td1_plantes.notification.NotificationObject;
import com.example.td1_plantes.notification.NotificationType;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class App extends Application {

    public static NotificationManager notificationManager;

    public static final Map<NotificationObject, Context> notificationsSources = new HashMap<>();

    public static Context DEFAULT_CONTEXT; // to be set in `MainActivity`

    @Override
    public void onCreate() {
        super.onCreate();
        this.createNotificationChannels();
    }

    /**
     * For each notification type, you can choose a method of alert among:
     *      .setImportance(NotificationManager.IMPORTANCE_MIN)
     *                                         IMPORTANCE_LOW
     *                                         IMPORTANCE_DEFAULT
     *                                         IMPORTANCE_HIGH
     */
    private void createNotificationChannels() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // API 26+
            App.notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE); // only once
            Objects.requireNonNull(App.notificationManager);

            NotificationType.TYPE_DEFAULT = new NotificationType("Default notification")
                    .setImportance(NotificationManager.IMPORTANCE_LOW)
                    .setIcon(R.drawable.star)
                    .setDescription("Default");

            NotificationType.TYPE_PICTURE = new NotificationType("Picture taken")
                    .setImportance(NotificationManager.IMPORTANCE_LOW)
                    .setIcon(R.drawable.take_picture_24)
                    .setTarget(TakePictureActivity.class.getName())
                    .setDescription("When taking pictures");

            NotificationType.TYPE_SHARED = new NotificationType("Post")
                    .setImportance(NotificationManager.IMPORTANCE_LOW)
                    .setIcon(R.drawable.star)
                    .setDescription("When sharing pictures with others");

            NotificationType.TYPE_MODIFICATIONS_DETECTED = new NotificationType("Modifications in your post")
                    .setImportance(NotificationManager.IMPORTANCE_DEFAULT)
                    .setIcon(R.drawable.star)
                    .setDescription("When someone make any modification in your post");
            //.setShowBadge(true);


            App.notificationManager.createNotificationChannel(NotificationType.TYPE_DEFAULT.getChannel());
            App.notificationManager.createNotificationChannel(NotificationType.TYPE_PICTURE.getChannel());
            App.notificationManager.createNotificationChannel(NotificationType.TYPE_SHARED.getChannel());
            App.notificationManager.createNotificationChannel(NotificationType.TYPE_MODIFICATIONS_DETECTED.getChannel());
        }
    }
}