package com.belajar.notifyme;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        int notificationId = intent.getExtras().getInt("com.belajar.notifyme");


        Toast.makeText(context,"Pesan Tidak Diterima",Toast.LENGTH_SHORT).show();

        Log.d("TAG","GET"+notificationId);
    }

}
