package com.example.alexr.alarmclock

import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.app.Service.START_NOT_STICKY
import android.content.Context
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat.getExtras
import android.content.Intent
import android.os.IBinder
import android.media.Ringtone
import android.support.v4.app.NotificationCompat
import android.util.Log
import android.widget.RemoteViews


class rtServ : Service() {
    private var ringtone: Ringtone? = null

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        val uri = RingtoneManager.getActualDefaultRingtoneUri(
            applicationContext,
            RingtoneManager.TYPE_RINGTONE
        )

        this.ringtone = RingtoneManager.getRingtone(this, uri)
        ringtone!!.play()

        val aID = intent.getStringExtra("aID")
        //val VIB = intent.getStringExtra("VIB")

        val RV = RemoteViews("com.example.alexr.alarmclock", R.layout.custom_noti)

        val intentSnooze = Intent(this, handleAlarm::class.java)
        intentSnooze.putExtra("fun", "SNOOZE")
        intentSnooze.putExtra("aID", aID)
        //intentSnooze.putExtra("VIB", VIB)
        RV.setOnClickPendingIntent(
            R.id.Snooze,
            PendingIntent.getActivity(this, 0, intentSnooze, 0)
        )

        val intentStop = Intent(this, handleAlarm::class.java)
        intentStop.putExtra("fun", "STOP")
        intentStop.putExtra("aID", aID)
       // intentStop.putExtra("VIB", VIB)
        RV.setOnClickPendingIntent(
            R.id.Stop,
            PendingIntent.getActivity(this, 1, intentStop, 0)
        )
        val notiBuilder = NotificationCompat.Builder(this, "default")
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("#")
            .setContentText("#")
            .setCustomContentView(RV)
            .setAutoCancel(true)
        val intentMain = Intent(this, MainActivity::class.java)
        val pi = PendingIntent.getActivity(this, aID.toInt(), intentMain, PendingIntent.FLAG_UPDATE_CURRENT)
        notiBuilder.setContentIntent(pi)
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(aID.toInt(), notiBuilder.build())
        intentStop.putExtra("aID", aID)
      //  intentStop.putExtra("VIB", VIB)



        return START_NOT_STICKY
    }

    override fun onDestroy() {
        ringtone!!.stop()
    }
}