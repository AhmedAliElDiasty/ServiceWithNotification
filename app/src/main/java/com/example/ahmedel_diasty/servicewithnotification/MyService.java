package com.example.ahmedel_diasty.servicewithnotification;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.AudioTrack;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.media.audiofx.BassBoost;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;

/**
 * Created by Ahmed El-Diasty on 10/03/2018.
 */

public class MyService extends Service {
    int i = 0;
    boolean isRunning = true;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Thread thread = new Thread(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void run() {
                while(isRunning){
                    try {
                        Thread.sleep(2000);
                        createNotification(i++);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();
        return super.onStartCommand(intent, flags, startId);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT_WATCH)
    private void createNotification(int i) {

        Intent intent = new Intent(MyService.this,SecondActivity.class);
        @SuppressLint("WrongConstant") PendingIntent pendingIntent =
                PendingIntent.getActivity(MyService.this,0,intent,Intent.FLAG_ACTIVITY_NEW_TASK);

        Notification.Builder builder = new Notification.Builder(MyService.this) ;
        builder.setContentTitle("My Notification");
        builder.setContentText("Hello this is my First Program with Notification");
        builder.setSmallIcon(R.drawable.ic_launcher_foreground);
//        builder.setColor(android.R.color.holo_orange_dark);
        builder.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION));

        Notification.Action  action = new Notification.Action.Builder(android.R.drawable.ic_media_play,"play",pendingIntent).build();
        builder.addAction(action);
        builder.addAction(android.R.drawable.ic_media_pause,"pause",pendingIntent);
        builder.setContentIntent(pendingIntent);

        Notification notification = builder.build();
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(i,notification);


    }
    @Override
    public void onDestroy(){
        isRunning = false;
    }
}
