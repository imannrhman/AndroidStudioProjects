package com.belajar.notifyme;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    private Button button_notify;
    private Button button_update ;
    private Button button_cancel;
    private static final String PRIMARY_CHANNEL_ID  = "primary_notification_channel";
    private NotificationManager mNotifyManager;
    private static final int NOTIFICATION_ID = 0 ;
    private static final String ACTION_UPDATE_NOTIFICATION = "com.belajar.notifyme.ACTION_UPDATE_NOTIFICATION";
    private NotificationReceiver mReceiver = new NotificationReceiver();
    private Boolean statusNotification = false;

    public void createNotificationChannel(){
        mNotifyManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(
                    PRIMARY_CHANNEL_ID
                    ,"Iman Notification"
                    ,NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Iman");
            mNotifyManager.createNotificationChannel(notificationChannel);

        }
    }


    public void sendNotification(){
            Intent updateIntent = new Intent(ACTION_UPDATE_NOTIFICATION);
            PendingIntent updatePendingIntent = PendingIntent.getBroadcast(this,NOTIFICATION_ID,updateIntent,PendingIntent.FLAG_ONE_SHOT);

            NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

            notifyBuilder.addAction(R.drawable.ic_update,"Update Notification",updatePendingIntent);

            mNotifyManager.notify(NOTIFICATION_ID,notifyBuilder.build());


        setNotificationButtonState(false,true,true);


    }

    private NotificationCompat.Builder getNotificationBuilder(){
        Intent notificationIntent = new Intent(this,MyBroadcastReceiver.class);
        notificationIntent.putExtra("com.belajar.notifyme",NOTIFICATION_ID);
        notificationIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(this,NOTIFICATION_ID,notificationIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(this,PRIMARY_CHANNEL_ID)
                .setContentTitle("Kamu Memiliki Notifikasi")
                .setContentText("Ini Contoh Notifikasi")
                .setContentIntent(notificationPendingIntent)
                .setSmallIcon(R.drawable.ic_android)
                .setContentIntent(notificationPendingIntent)
                .setAutoCancel(true)
                .setDeleteIntent(createOnDissmissedIntent(this,NOTIFICATION_ID))
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setVibrate(new long[] {1000,1000,1000,1000,1000,1000})
                .setLights(Color.RED,500,100)
                .setDefaults(NotificationCompat.DEFAULT_ALL);


        return  notifyBuilder;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_notify = findViewById(R.id.notify);
        button_notify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendNotification();


            }
        });
        button_update = findViewById(R.id.update);
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateNotification();
            }
        });
        button_cancel = findViewById(R.id.cancel);
        button_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelNotification();
            }
        });


        registerReceiver(mReceiver,new IntentFilter(ACTION_UPDATE_NOTIFICATION));

        setNotificationButtonState(true,false,false);
        createNotificationChannel();
    }

    @Override
    protected void onDestroy() {
        unregisterReceiver(mReceiver);
        super.onDestroy();
    }

    private void cancelNotification() {
        mNotifyManager.cancel(NOTIFICATION_ID);
        setNotificationButtonState(true,false,false);
    }

    private void updateNotification() {

        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(),R.drawable.mascot_1);

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
        .bigPicture(androidImage)
        .setBigContentTitle("Notifikasi Baru!1!1!")).setSmallIcon(R.drawable.ic_update);
        mNotifyManager.notify(NOTIFICATION_ID,notifyBuilder.build());
        setNotificationButtonState(false,false,true);

    }

    void setNotificationButtonState(Boolean isNotifyEnabled,Boolean isUpdateEnabed,Boolean isCancelEnabled){
        button_notify.setEnabled(isNotifyEnabled);
        button_update.setEnabled(isUpdateEnabed);
        button_cancel.setEnabled(isCancelEnabled);
    }
    public class NotificationReceiver extends BroadcastReceiver{

        @Override
        public void onReceive(Context context, Intent intent) {
            updateNotification();
        }

    }

    private PendingIntent createOnDissmissedIntent(Context context, int notificationId){
        Intent intent = new Intent(context,MyBroadcastReceiver.class);
        intent.putExtra("com.belajar.notifyme",notificationId);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(context.getApplicationContext(),notificationId,intent,PendingIntent.FLAG_ONE_SHOT);
        return pendingIntent;
    }
}
