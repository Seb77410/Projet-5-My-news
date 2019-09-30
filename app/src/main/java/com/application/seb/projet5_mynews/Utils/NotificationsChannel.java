package com.application.seb.projet5_mynews.Utils;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

public class NotificationsChannel extends Application {


    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel channel1 = new NotificationChannel(
                    MyConstants.CHANNEL_1_ID,
                    MyConstants.CHANNEL_1_NAME,
                    NotificationManager.IMPORTANCE_HIGH
            );
            channel1.setDescription(MyConstants.CHANNEL_1_DESCRIPTION);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel1);
        }
    }
}
