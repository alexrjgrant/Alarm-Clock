package com.example.alexr.alarmclock

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.R.string.cancel
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.media.RingtoneManager
import java.util.*


class handleAlarm : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aID: String = intent.getStringExtra("aID")

        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val func: String = intent.getStringExtra("fun")
       // val VIB: String = intent.getStringExtra("VIB")

        val rm = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE)
        val rt = RingtoneManager.getRingtone(applicationContext, rm)


        val intent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getBroadcast(applicationContext, aID.toInt(), intent, PendingIntent.FLAG_CANCEL_CURRENT)
        am.cancel(pendingIntent)


        Log.d("f",func)
        val stopIntent = Intent(this, rtServ::class.java)
        this.stopService(stopIntent)

        if (func =="STOP") {
            val intent = Intent(this, MainActivity::class.java)
            val pendingIntent = PendingIntent.getBroadcast(applicationContext, aID.toInt(), intent, PendingIntent.FLAG_CANCEL_CURRENT)
            am.cancel(pendingIntent)



        }

        if (func == "SNOOZE")
        {
            val intent = Intent(this, BR::class.java)

            intent.putExtra("aID", aID)
            //intent.putExtra("VIB",VIB)
            val pi = PendingIntent.getBroadcast(applicationContext, aID.toInt(), intent, PendingIntent.FLAG_UPDATE_CURRENT)
            am.set(AlarmManager.RTC_WAKEUP, java.lang.System.currentTimeMillis()+540000, pi)

        }





//        val intent2 = Intent(this, MainActivity::class.java)
//        startActivity(intent2)
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancel(aID.toInt())
        notificationManager.cancelAll()

        val intentM = Intent(this, MainActivity::class.java)
        startActivity(intentM)
       // Log.d("ha", VIB)

    }

}