package com.example.td1_plantes.notification;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.td1_plantes.App;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NotificationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        NotificationObject notif = intent.getParcelableExtra(NotificationObject.STR);

        Log.d("NotificationReceiver", intent.getExtras().toString());

        App.notificationManager.cancel(notif.getId());

        if (notif.getTarget() != null) {
            Context source = App.notificationsSources.get(notif);

            String errorMsg;
            try {
                Class<?> target = Class.forName(notif.getTarget());
                source.startActivity(new Intent(source.getApplicationContext(), target));
            } catch (ClassNotFoundException exc) {
                errorMsg = "ClassNotFoundException caught for notification type " + notif.getType() + "\n"
                        + "Cannot start activity " + notif.getTarget();
                Log.d("NotificationReceiver", errorMsg);
            }
        }

        App.notificationsSources.remove(notif);
    }
}
