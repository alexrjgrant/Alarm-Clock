package com.example.alexr.alarmclock

import android.app.Service
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager

import android.os.IBinder


class accServ : Service(),SensorEventListener {
    lateinit var accel: Sensor
    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {


        val sMgr = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accel = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sMgr.registerListener ( this, accel, SensorManager.SENSOR_DELAY_UI )
        return START_NOT_STICKY
    }
    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(ev: SensorEvent) {
        // Test which sensor has been detected.
        if(ev.sensor == accel) {
            if(ev.values[0] > 3 || ev.values[0] >0)
            {
                val intent = Intent(this, addAlarm::class.java)
                startActivity(intent)
            }
        }
    }

    override fun onDestroy() {
      //call handle
    }
}