package com.example.eoinh.fallscatcherv3;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;

/**
 * Created by EoinH on 09/04/2018.
 */

public class NotificationReceiver extends BroadcastReceiver {
    private PendingIntent pendingIntent;
    @Override
    public void onReceive(Context context, Intent intent) {
        MediaPlayer mediaPlayer = MediaPlayer.create(context, Settings.System.DEFAULT_NOTIFICATION_URI);
        mediaPlayer.start();

        NotificationCompat.Builder notification = new NotificationCompat.Builder(context, "Channel id");

        notification.setSmallIcon(R.drawable.ic_launcher_foreground);
        notification.setWhen(System.currentTimeMillis());
        notification.setContentTitle("Have you suffered a fall today?");
        notification.setContentText("Tap the notification to register fall");
        Intent newIntent = new Intent(context, MainActivity.class);
        pendingIntent = PendingIntent.getActivity(context, 0 , newIntent , 0);
        notification.setContentIntent(pendingIntent);

        notification.setAutoCancel(true);
    }
}
