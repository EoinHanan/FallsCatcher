package com.example.eoinh.fallscatcherv3;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

/**
 * Created by EoinH on 09/04/2018.
 */

public class NotificationManager {
    private AlarmManager alarmManager;
    private Intent intent;
    private PendingIntent pendingIntent;



    public NotificationManager(Context context){
        intent = new Intent (context,NotificationReceiver.class);
        pendingIntent = PendingIntent.getBroadcast( context,0, intent, 0);

    }

    public void turnOnNotification(long time){
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,  time, alarmManager.INTERVAL_DAY, pendingIntent);
    }

    public void turnOffNotification(){
        alarmManager.cancel(pendingIntent);
    }

}
