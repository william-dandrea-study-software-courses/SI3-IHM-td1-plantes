package com.example.td1_plantes.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.widget.RemoteViews;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.td1_plantes.App;
import com.example.td1_plantes.R;

import java.util.Objects;


/**
 * You can create a notification used this pattern (with any order in the method calls)
 *
 *         NotificationObject notification = new NotificationObject()
 *                     .setTitle(String)
 *                     .setMessage(String)
 *                     .setType(NotificationType)
 *                     .setImage(int from R.drawable);
 *         notification.sendFrom(this (being to context from where you sent the notification));
 */
@RequiresApi(api = Build.VERSION_CODES.O)
public class NotificationObject implements Parcelable {

    static final String STR = "notification";
    private static int nextAvailableId = 0;

    private static final int DEFAULT_TEXT_COLOR = Color.WHITE;
    private static final NotificationType DEFAULT_TYPE = NotificationType.TYPE_DEFAULT;

    private final int id;
    private String title;
    private String message;
    private NotificationType type;
    private int image;


    public NotificationObject() {
        this.id = this.nextIdAvailable();

        this.title = "Untitled";
        this.message = "[Empty]";
        this.type = NotificationObject.DEFAULT_TYPE;
        this.image = -1;
    }
    int getId() {
        return this.id;
    }
    public NotificationObject setTitle(String title) {
        this.title = title;
        return this;
    }
    public NotificationObject setMessage(String message) {
        this.message = message;
        return this;
    }
    public NotificationObject setType(NotificationType type) {
        this.type = type;
        return this;
    }
    public NotificationObject setImage(int image) {
        this.image = image;
        return this;
    }

    private int nextIdAvailable() {
        NotificationObject.nextAvailableId++;
        return NotificationObject.nextAvailableId - 1; // this id is available, and... not anymore, so ++ for the next one
    }

    String getTarget() {
        return this.type.getTarget();
    }
    NotificationType getType() {
        return this.type;
    }


    public void sendFrom(Activity activity) {
        App.notificationsSources.put(this, activity);



        RemoteViews collapsedView = new RemoteViews(activity.getPackageName(), R.layout.notification_collapsed);
        RemoteViews expandedView = new RemoteViews(activity.getPackageName(), R.layout.notification_expanded);


        Notification.Builder notification = new Notification.Builder(activity.getApplicationContext(), this.type.get())
                .setCustomContentView(collapsedView)
                .setCustomHeadsUpContentView(collapsedView)
                .setChannelId(this.type.get())
                .setColorized(true)

                // a bit useless now
                .setContentTitle(this.title)
                .setContentText(this.message)
                .setColor(Color.BLACK)
                .setSmallIcon(this.type.getIcon());


        collapsedView.setTextViewText(R.id.notification_title, this.title);
        collapsedView.setTextColor(R.id.notification_title, NotificationObject.DEFAULT_TEXT_COLOR);
        collapsedView.setTextViewText(R.id.notification_message, this.message);
        collapsedView.setTextColor(R.id.notification_message, NotificationObject.DEFAULT_TEXT_COLOR);
        collapsedView.setImageViewResource(R.id.notification_icon, this.type.getIcon());

        if (this.image != -1) {
            notification.setCustomBigContentView(expandedView);

            expandedView.setTextViewText(R.id.notification_title, this.title);
            expandedView.setTextColor(R.id.notification_title, NotificationObject.DEFAULT_TEXT_COLOR);
            expandedView.setTextViewText(R.id.notification_message, this.message);
            expandedView.setTextColor(R.id.notification_message, NotificationObject.DEFAULT_TEXT_COLOR);
            expandedView.setImageViewResource(R.id.notification_icon, this.type.getIcon());

            collapsedView.setImageViewResource(R.id.collapsed_image, this.image);
            expandedView.setImageViewResource(R.id.expanded_image, this.image);
        } else {
            notification.setCustomBigContentView(collapsedView);
        }



        // when clicked
        Intent clickIntent = new Intent(activity, NotificationReceiver.class);
        clickIntent.putExtra(NotificationObject.STR, this);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(activity, this.id, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        expandedView.setOnClickPendingIntent(R.id.expanded_image, clickPendingIntent); // like setOnClickListener



        App.notificationManager.notify(this.id, notification.build());
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationObject that = (NotificationObject) o;
        return id == that.id &&
                image == that.image &&
                title.equals(that.title) &&
                message.equals(that.message) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, message, type, image);
    }

    @Override
    public String toString() {
        return "NotificationObject{" +
                "id=" + this.id +
                ", title='" + this.title + '\'' +
                ", message='" + this.message + '\'' +
                ", type=" + this.type +
                ", image=" + this.image +
                '}';
    }








    // parcelable requirements

    private NotificationObject(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.message = in.readString();
        this.type = in.readParcelable(NotificationType.class.getClassLoader());
        this.image = in.readInt();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.title);
        dest.writeString(this.message);
        dest.writeParcelable(this.type, flags);
        dest.writeInt(this.image);
    }
    public static final Parcelable.Creator<NotificationObject> CREATOR = new Parcelable.Creator<NotificationObject>() {
        @Override
        public NotificationObject createFromParcel(Parcel source) {
            return new NotificationObject(source);
        }
        @Override
        public NotificationObject[] newArray(int size) {
            return new NotificationObject[size];
        }
    };
}
