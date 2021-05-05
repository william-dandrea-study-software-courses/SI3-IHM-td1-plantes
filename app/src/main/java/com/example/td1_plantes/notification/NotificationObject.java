package com.example.td1_plantes.notification;

import android.app.Activity;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Build;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.RemoteViews;
import android.widget.TextView;

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
    private int imageResource;
    private Bitmap imageBitmap;

    private String lastSetImageType;
    private static final String IMAGE_TYPE_NONE = "";
    private static final String IMAGE_TYPE_RESOURCE = "Resource";
    private static final String IMAGE_TYPE_BITMAP = "Bitmap";

    public NotificationObject() {
        Log.d("NotificationObject.new", "nothing");
        this.id = this.nextIdAvailable();

        this.title = "Untitled";
        this.message = "[Empty]";
        this.type = NotificationObject.DEFAULT_TYPE;
        this.imageResource = -1;
        this.imageBitmap = null;

        this.lastSetImageType = NotificationObject.IMAGE_TYPE_NONE;
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
        this.imageResource = image;
        this.lastSetImageType = NotificationObject.IMAGE_TYPE_RESOURCE;
        return this;
    }
    public NotificationObject setImage(Bitmap image) {
        this.imageBitmap = image;
        this.lastSetImageType = NotificationObject.IMAGE_TYPE_BITMAP;
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





    public void send() {
        this.sendFrom((Activity) App.DEFAULT_CONTEXT);
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


        collapsedView.setTextViewText(R.id.notification_title, this.resizeTitle(this.title));
        collapsedView.setTextColor(R.id.notification_title, NotificationObject.DEFAULT_TEXT_COLOR);
        collapsedView.setTextViewText(R.id.notification_message, this.resizeMessage(this.message));
        collapsedView.setTextColor(R.id.notification_message, NotificationObject.DEFAULT_TEXT_COLOR);
        collapsedView.setImageViewResource(R.id.notification_icon, this.type.getIcon());

        this.setNotificationImage(notification, activity, collapsedView, expandedView);

        // when clicked

        /*// doesn't work anymore :/
        Intent clickIntent = new Intent(activity, NotificationReceiver.class);
        clickIntent.putExtra(NotificationObject.STR, this);
        PendingIntent clickPendingIntent = PendingIntent.getBroadcast(activity, this.id, clickIntent, PendingIntent.FLAG_CANCEL_CURRENT);
        expandedView.setOnClickPendingIntent(R.id.expanded_image, clickPendingIntent); // like setOnClickListener
        */


        App.notificationManager.notify(this.id, notification.build());
    }

    private void setNotificationImage(Notification.Builder notification,
                                      Activity activity,
                                      RemoteViews collapsedView,
                                      RemoteViews expandedView) {

        if (this.lastSetImageType.equals(NotificationObject.IMAGE_TYPE_NONE)) {
            notification.setCustomBigContentView(collapsedView);
            return;
        } else {
            notification.setCustomBigContentView(expandedView);

            expandedView.setTextViewText(R.id.notification_title, this.resizeTitle(this.title));
            expandedView.setTextColor(R.id.notification_title, NotificationObject.DEFAULT_TEXT_COLOR);
            expandedView.setTextViewText(R.id.notification_message, this.resizeMessage(this.message));
            expandedView.setTextColor(R.id.notification_message, NotificationObject.DEFAULT_TEXT_COLOR);
            expandedView.setImageViewResource(R.id.notification_icon, this.type.getIcon());
        }


        if (this.lastSetImageType.equals(NotificationObject.IMAGE_TYPE_RESOURCE) && (this.imageResource != -1)) {
            // Bitmap imgBtm = BitmapFactory.decodeResource(activity.getResources(), this.imageResource);
            // collapsedView.setImageViewBitmap(R.id.collapsed_image, imgBtm);
            // expandedView.setImageViewBitmap(R.id.expanded_image, imgBtm);
            collapsedView.setImageViewResource(R.id.collapsed_image, this.imageResource);
            expandedView.setImageViewResource(R.id.expanded_image, this.imageResource);
        } else if (this.lastSetImageType.equals(NotificationObject.IMAGE_TYPE_BITMAP) && (this.imageBitmap != null)) {
            this.imageBitmap = this.resizeImage(activity, this.imageBitmap);
            Log.d("NotificationObject", "resized image");
            collapsedView.setImageViewBitmap(R.id.collapsed_image, this.imageBitmap);
            expandedView.setImageViewBitmap(R.id.expanded_image, this.imageBitmap);
        }
    }

    private Bitmap resizeImage(Activity activity, Bitmap image) {
        int maxHeight = 256 - 64; // in dp
        maxHeight = this.toPx(activity, maxHeight); // in px
        int maxWidth = this.getScreenWidthPx(activity);
        int width = image.getWidth(); // in px
        int height = image.getHeight(); // in px

        width *= (double) (maxHeight/height);
        height = maxHeight;

        if (width > maxWidth) {
            height *= (double) (maxWidth/width);
        }

        // to be sure
        if (width == 0) {
            width = 1;
        } else if (width > 1) {
            width -= 1;
        }
        if (height == 0) {
            height = 1;
        } else if (height > 1) {
            height -= 1;
        }

        return Bitmap.createScaledBitmap(image, width, height, true);
    }
    private String resizeTitle(String title) {
        if (title.length() > 30) {
            title = title.substring(0, 30) + "...";
        }
        return title;
    }
    private String resizeTitle(Activity activity, RemoteViews view, String title) {
        TextView titleProperties = view.apply(activity, null).findViewById(R.id.notification_title);
        try {
            Objects.requireNonNull(titleProperties);
        } catch (NullPointerException exc) {
            return title;
        }

        int maxSize = this.getScreenWidthPx(activity)
                - this.toPx(activity, titleProperties.getPaddingStart())
                - this.toPx(activity, titleProperties.getPaddingEnd());

        boolean cropped = false;
        while (titleProperties.getPaint().measureText(title) > maxSize) {
            title = title.substring(0, title.length() - 1);
            cropped = true;
        }
        if (cropped) {
            title = title.substring(0, title.length() - 3) + "...";
        }
        return title;
    }
    private String resizeMessage(String message) {
        if (message.length() > 60) {
            message = message.substring(0, 60) + "...";
        }
        return message;
    }
    private String resizeMessage(Activity activity, RemoteViews view, String message) {
        TextView messageProperties = view.apply(activity, null).findViewById(R.id.notification_message);
        try {
            Objects.requireNonNull(messageProperties);
        } catch (NullPointerException exc) {
            return message;
        }

        int maxSize = this.getScreenWidthPx(activity)
                - this.toPx(activity, messageProperties.getPaddingStart())
                - this.toPx(activity, messageProperties.getPaddingEnd());

        boolean cropped = false;
        while (messageProperties.getPaint().measureText(message) > maxSize) {
            message = message.substring(0, message.length() - 1);
            cropped = true;
        }
        if (cropped) {
            message = message.substring(0, message.length() - 3) + "...";
        }
        return message;
    }


    private int getScreenWidthPx(Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        return size.x;
    }

    private int toPx(Activity activity, int dp) {
        return (int) (dp * activity.getResources().getDisplayMetrics().density);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        NotificationObject that = (NotificationObject) o;
        return id == that.id &&
                imageBitmap.equals(that.imageBitmap) &&
                imageResource == that.imageResource &&
                title.equals(that.title) &&
                message.equals(that.message) &&
                type.equals(that.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, message, type, imageResource, imageBitmap);
    }

    @Override
    public String toString() {
        return "NotificationObject{" +
                "id=" + this.id +
                ", title='" + this.title + '\'' +
                ", message='" + this.message + '\'' +
                ", type=" + this.type +
                '}';
    }








    // parcelable requirements

    private NotificationObject(Parcel in) {
        this.id = in.readInt();
        this.title = in.readString();
        this.message = in.readString();
        this.type = in.readParcelable(NotificationType.class.getClassLoader());
        this.imageResource = in.readInt();
        this.imageBitmap = in.readParcelable(Bitmap.class.getClassLoader());
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
        dest.writeInt(this.imageResource);
        dest.writeParcelable(this.imageBitmap, flags);
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
