package jp.noriokun4649.comicmarketmap.notification;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;

import static android.content.Context.NOTIFICATION_SERVICE;

public class Notification {

    private NotificationManager notificationManager;
    private String title;
    private String massage;
    private String submassage;
    private int icon;
    private int id;
    private Context context;
    private String channelId;

    public Notification(final Context context, final String title, final String massage, final String submassage, final int icon, final int id, final String channelId) {
        this.context = context;
        this.title = title;
        this.massage = massage;
        this.submassage = submassage;
        this.icon = icon;
        this.id = id;
        this.channelId = channelId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(final String title) {
        this.title = title;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(final String massage) {
        this.massage = massage;
    }

    public String getSubmassage() {
        return submassage;
    }

    public void setSubmassage(final String submassage) {
        this.submassage = submassage;
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(final int icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void showNotification(int id) {
        NotificationCompat.Builder noti = new NotificationCompat.Builder(context, channelId)
                .setContentTitle(title)
                .setContentText(massage)
                .setSubText(submassage)
                .setSmallIcon(icon);
        notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(id, noti.build());
    }

    public void cancel(int id) {
        notificationManager.cancel(id);
    }
}
