package com.example.td1_plantes.notification;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.RequiresApi;

import com.example.td1_plantes.MainActivity;

import java.util.Objects;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NotificationType implements Parcelable {


    public static NotificationType TYPE_DEFAULT;
    public static NotificationType TYPE_PICTURE;
    public static NotificationType TYPE_SHARED;
    public static NotificationType TYPE_MODIFICATIONS_DETECTED;




    private NotificationChannel innerNotificationChannel; // because NotificationChannel is final, apparently
    private int importance;
    private final String name;
    private int icon;
    private String targetContext;


    public NotificationType(String name) {
        this.innerNotificationChannel = null;
        this.importance = NotificationManager.IMPORTANCE_LOW;
        this.name = name;
        this.icon = -1;
        this.targetContext = MainActivity.class.getName();
        this.build();
    }

    private void build() {
        this.innerNotificationChannel = new NotificationChannel(this.name, this.name, this.importance);
    }

    public NotificationType setImportance(int importance) {
        this.importance = importance;
        this.build();
        return this;
    }
    public NotificationType setIcon(int icon) {
        this.icon = icon;
        return this;
    }
    public NotificationType setTarget(String target) {
        this.targetContext = target;
        return this;
    }
    public NotificationType setDescription(String description) {
        this.innerNotificationChannel.setDescription(description);
        return this;
    }

    public NotificationChannel getChannel() {
        return this.innerNotificationChannel;
    }
    String get() {
        return this.name;
    }
    int getIcon() {
        return this.icon;
    }
    String getTarget() {
        return this.targetContext;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationType that = (NotificationType) o;
        return importance == that.importance &&
                icon == that.icon &&
                name.equals(that.name) &&
                targetContext.equals(that.targetContext);
    }

    @Override
    public int hashCode() {
        return Objects.hash(importance, name, icon, targetContext);
    }

    @Override
    public String toString() {
        return "NotificationType{" +
                "importance=" + this.importance +
                ", name='" + this.name + '\'' +
                ", icon=" + this.icon +
                ", target class=" + this.targetContext +
                '}';
    }








    // parcelable requirements

    private NotificationType(Parcel in) {
        this.innerNotificationChannel = in.readParcelable(NotificationChannel.class.getClassLoader());
        this.importance = in.readInt();
        this.name = in.readString();
        this.icon = in.readInt();
        this.targetContext = in.readString();
    }
    @Override
    public int describeContents() {
        return 0;
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.innerNotificationChannel, flags);
        dest.writeInt(this.importance);
        dest.writeString(this.name);
        dest.writeInt(this.icon);
        dest.writeString(this.targetContext);
    }
    static final Parcelable.Creator<NotificationType> CREATOR = new Parcelable.Creator<NotificationType>() {
        @Override
        public NotificationType createFromParcel(Parcel source) {
            return new NotificationType(source);
        }
        @Override
        public NotificationType[] newArray(int size) {
            return new NotificationType[size];
        }
    };
}
