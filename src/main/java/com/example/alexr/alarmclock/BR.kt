
package com.example.alexr.alarmclock

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

import android.os.Vibrator

import android.util.Log


class BR:BroadcastReceiver() {

    override fun onReceive(context:Context, intent:Intent) {
        val a = intent.getStringExtra("aID")
       // val VIB = intent.getStringExtra("VIB")

        val startIntent = Intent(context, rtServ::class.java)
        startIntent.putExtra("aID", a)
        //startIntent.putExtra("VIB", VIB)
        context.startService(startIntent)

//if(VIB=="1")
//{
        val vib = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vib.vibrate(5000)
Log.d("VIB","vib")
//}



        val aID = Integer.parseInt(a)
    }


}





//package com.example.alexr.alarmclock;
//
//import android.app.*;
//import android.content.BroadcastReceiver;
//import android.content.Context;
//import android.content.Intent;
//import android.media.Ringtone;
//import android.media.RingtoneManager;
//import android.net.Uri;
//import android.os.Vibrator;
//import android.support.v4.app.NotificationCompat;
//import android.widget.RemoteViews;
//
//public class BR extends BroadcastReceiver {
//
//
//    public void onReceive(Context context,Intent intent){
//        Vibrator vib = (Vibrator)context.getSystemService(Context.VIBRATOR_SERVICE);
//        vib.vibrate(2000);
//
//        Uri rm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE);
//        Ringtone rt = RingtoneManager.getRingtone(context,rm);
//
//        rt.play();
//
//        String a = intent.getStringExtra("aID");
//        int aID = Integer.parseInt(a);
//
//
//        RemoteViews RV = new RemoteViews("com.example.alexr.alarmclock", R.layout.custom_noti);
//
//        Intent in = new Intent(context, handleAlarm.class);
//        in.putExtra("fun", "SNOOZE");
//        in.putExtra("aID", a);
//        RV.setOnClickPendingIntent(R.id.Snooze,PendingIntent.getActivity(context, 0, in, PendingIntent.FLAG_UPDATE_CURRENT));
//
//        Intent in2 = new Intent(context, handleAlarm.class);
//        in2.putExtra("fun", "STOP");
//        in2.putExtra("aID", a);
//        RV.setOnClickPendingIntent(R.id.Stop,PendingIntent.getActivity(context, 1, in2, PendingIntent.FLAG_UPDATE_CURRENT));
//
//        NotificationCompat.Builder notiBuilder = new NotificationCompat.Builder(context, "default")
//            .setSmallIcon(R.mipmap.ic_launcher)
//            .setContentTitle("#")
//            .setContentText("#")
//            .setCustomContentView(RV)
//            .setAutoCancel(true) ;
//
//        Intent intent2 = new Intent(context, MainActivity.class);
//        PendingIntent pi = PendingIntent.getActivity(context, 0, intent2, PendingIntent.FLAG_UPDATE_CURRENT);
//        notiBuilder.setContentIntent(pi);
//
//        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
//        notificationManager.notify(aID, notiBuilder.build());
//
//    }
//
//}
