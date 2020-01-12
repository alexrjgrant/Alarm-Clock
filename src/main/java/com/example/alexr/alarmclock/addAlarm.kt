package com.example.alexr.alarmclock

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.ToggleButton
import kotlinx.android.synthetic.main.add_alarm.*
import org.jetbrains.anko.*
import java.util.*

class addAlarm : AppCompatActivity(),SensorEventListener {
    lateinit var ac: Sensor

    var runSQL: MethodsSQL? = null //SQL Helper
    var isDate = 1
    ////////////////////////////////////////////////////
    //////////////////// ON CREATE /////////////////////
    ////////////////////////////////////////////////////
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.add_alarm)

        val sMgr = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        ac = sMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
        sMgr.registerListener ( this, ac, SensorManager.SENSOR_DELAY_UI )

        runSQL = MethodsSQL(this) //SQL Helper

        val AlarmID: String = intent.getStringExtra("AlarmID") //Get ID from Main if Avalible

        var day = 0
        var month = 0
        var year = 0
        var hour = 0
        var minute = 0

        var vib = 0

        var pickedTime = ""
        var pickedDate = ""

        ////Hide components on load
        rbOnce.isChecked = true
        tvRepeat.setVisibility(View.GONE)
        divider3.setVisibility(View.GONE)
        tbMon.setVisibility(View.GONE)
        tbTue.setVisibility(View.GONE)
        tbWed.setVisibility(View.GONE)
        tbThu.setVisibility(View.GONE)
        tbFri.setVisibility(View.GONE)
        tbSat.setVisibility(View.GONE)
        tbSun.setVisibility(View.GONE)


        /////////////// ON Click Listeners /////////////////
        ////////////////////////////////////////////////////

        //////////////////// Radio Buttons /////////////////////
        rbOnce.setOnClickListener()
        {
            //Alter Visible Elements
            rbRep.isChecked = false
            tvDate.setVisibility(View.VISIBLE)
            tvPickedDate.setVisibility(View.VISIBLE)
            btnSelectDate.setVisibility(View.VISIBLE)
            divider7.setVisibility(View.VISIBLE)

            tvRepeat.setVisibility(View.GONE)
            divider3.setVisibility(View.GONE)
            tbMon.setVisibility(View.GONE)
            tbTue.setVisibility(View.GONE)
            tbWed.setVisibility(View.GONE)
            tbThu.setVisibility(View.GONE)
            tbFri.setVisibility(View.GONE)
            tbSat.setVisibility(View.GONE)
            tbSun.setVisibility(View.GONE)

            isDate = 1
        }
        rbRep.setOnClickListener()
        {
            //Alter Visible Elements
            rbOnce.isChecked = false
            tvDate.setVisibility(View.GONE);
            tvPickedDate.setVisibility(View.GONE);
            btnSelectDate.setVisibility(View.GONE);
            divider7.setVisibility(View.GONE)

            tvRepeat.setVisibility(View.VISIBLE)
            divider3.setVisibility(View.VISIBLE)
            tbMon.setVisibility(View.VISIBLE)
            tbTue.setVisibility(View.VISIBLE)
            tbWed.setVisibility(View.VISIBLE)
            tbThu.setVisibility(View.VISIBLE)
            tbFri.setVisibility(View.VISIBLE)
            tbSat.setVisibility(View.VISIBLE)
            tbSun.setVisibility(View.VISIBLE)

            isDate = 0
        }
/////////////// Floating Action Button (Save) ///////////////
        fab1.setOnClickListener {

            //If Name Input is Empty Rename
            var name: String = etName.text.toString()
            if (name.isEmpty()) {
                name = "Alarm"
            }

            /////////// If In Repeat Mode ///////////
            if (isDate == 0) {
                if ((onOff(tbMon) + onOff(tbTue) + onOff(tbWed) + onOff(tbThu)
                    + onOff(tbFri) + onOff(tbSat) + onOff(tbSun)) > 0 && !pickedTime.isEmpty())
                {



                    //Insert To DB
                    val id: Long = runSQL!!.insert(
                        name,
                        hour,
                        minute,
                        isDate,
                        year,
                        month,
                        day,
                        vib,
                        onOff(tbMon),
                        onOff(tbTue),
                        onOff(tbWed),
                        onOff(tbThu),
                        onOff(tbFri),
                        onOff(tbSat),
                        onOff(tbSun)
                    )
                    if (!AlarmID.isEmpty()) //If Already exists Delete
                    {
                        runSQL!!.delete(AlarmID.toLong())
                    }

                    //Return to Main
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    alert ( "Select A Day To Repeat / Time" ).show() //If No Repeat Selected
                }
            } else if (isDate == 1) //If Date Mode Activated
            {
                if (dateTimeValidator("$pickedDate$pickedTime") == 1) //If Valid Date/Time
                {
                    //Insert
                    val id: Long = runSQL!!.insert(
                        name,
                        hour,
                        minute,
                        isDate,
                        year,
                        month,
                        day,
                        vib,
                        onOff(tbMon),
                        onOff(tbTue),
                        onOff(tbWed),
                        onOff(tbThu),
                        onOff(tbFri),
                        onOff(tbSat),
                        onOff(tbSun)
                    )
                    //If
                    if (!AlarmID.isEmpty()) //If Already exists Delete
                    {
                        runSQL!!.delete(AlarmID.toLong())


                    }
                    //Return to main
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent)

                } else {
                    alert ( "Please Enter A Valid Date and Time" ).show()
                }
            }
        }

        ////////////// Select Date //////////////
        btnSelectDate.setOnClickListener() {

            val cal = Calendar.getInstance()

            val cDay = cal.get(Calendar.DAY_OF_MONTH)
            val cMonth = cal.get(Calendar.MONTH)
            val cYear = cal.get(Calendar.YEAR)

            val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { _, y, m, d ->

                day = d
                month = m + 1
                year = y

                if (d < 10 && m < 10) { pickedDate = "$year" + "0$month" + "0$day" }
                else if (d < 10)      { pickedDate = "$year" + "0$month" + "$day" }
                else if (m < 10)      { pickedDate = "$year" + "0$month" + "$day" }
                else                  { pickedDate = "$year" + "$month" + "$day"}

                tvPickedDate.text = concatDate(day, month, year)

            }, cYear, cMonth, cDay)

            datePickerDialog.show()
        }

        btnSelectTime.setOnClickListener {
            val cal = Calendar.getInstance()

            val cHour = cal.get(Calendar.HOUR_OF_DAY)
            val cMin = cal.get(Calendar.MINUTE)

            val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { _, h, m ->

                minute = m
                hour = h

                if (h < 10 && m < 10) {
                    pickedTime = "0$h" + "0$m"
                } else if (h < 10) {
                    pickedTime = "0$h$m"
                } else if (m < 10) {
                    pickedTime = "$h" + "0$m"
                } else {
                    pickedTime = "$h$m"
                }
                tvPickedTime.text = concatTime(m, h)

            }, cHour, cMin, true)

            timePickerDialog.show()
        }

        btnVib.setOnClickListener() {
            if (btnVib.isChecked) {
                vib = 1
            } else {
                vib = 0
            }
        }

        if (!AlarmID.isEmpty()) {
            val al = runSQL!!.findAlarm(AlarmID)[0]

            if (al.isDate == 0) {
                rbOnce.isChecked = false

                tvDate.setVisibility(View.GONE);
                tvPickedDate.setVisibility(View.GONE);
                btnSelectDate.setVisibility(View.GONE);
                divider7.setVisibility(View.GONE)

                tvRepeat.setVisibility(View.VISIBLE)
                divider3.setVisibility(View.VISIBLE)
                tbMon.setVisibility(View.VISIBLE)
                tbTue.setVisibility(View.VISIBLE)
                tbWed.setVisibility(View.VISIBLE)
                tbThu.setVisibility(View.VISIBLE)
                tbFri.setVisibility(View.VISIBLE)
                tbSat.setVisibility(View.VISIBLE)
                tbSun.setVisibility(View.VISIBLE)

                isDate = 0

                rbOnce.isChecked = true
            }

            etName.setText(al.name)
            hour = al.hour
            minute = al.min
            tvPickedTime.setText(concatTime(al.min, al.hour))
            vib = al.vib
            day = al.d
            month = al.m
            year = al.y
            tvPickedDate.setText(concatDate(al.d, al.m, al.y))

            if (al.vib == 1) {
                btnVib.isChecked = true
            }
            if (al.mon == 1) {
                tbMon.isChecked = true
            }
            if (al.tue == 1) {
                tbTue.isChecked = true
            }
            if (al.wed == 1) {
                tbWed.isChecked = true
            }
            if (al.thu == 1) {
                tbThu.isChecked = true
            }
            if (al.fri == 1) {
                tbFri.isChecked = true
            }
            if (al.sat == 1) {
                tbSat.isChecked = true
            }
            if (al.sun == 1) {
                tbSun.isChecked = true
            }


        }

    }


    override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {

    }

    override fun onSensorChanged(event: SensorEvent) {
        // Test which sensor has been detected.
        if(event.sensor == ac) {
            //toast(event.values[0].toString())
           if(event.values[0] > 10 || event.values[0] < -10)
           {
               //toast("YES")
               if (rbRep.isChecked)
               {
                   //Alter Visible Elements
                   rbRep.isChecked = false
                   rbOnce.isChecked = true
                   tvDate.setVisibility(View.VISIBLE)
                   tvPickedDate.setVisibility(View.VISIBLE)
                   btnSelectDate.setVisibility(View.VISIBLE)
                   divider7.setVisibility(View.VISIBLE)

                   tvRepeat.setVisibility(View.GONE)
                   divider3.setVisibility(View.GONE)
                   tbMon.setVisibility(View.GONE)
                   tbTue.setVisibility(View.GONE)
                   tbWed.setVisibility(View.GONE)
                   tbThu.setVisibility(View.GONE)
                   tbFri.setVisibility(View.GONE)
                   tbSat.setVisibility(View.GONE)
                   tbSun.setVisibility(View.GONE)

                   isDate = 1
               }
              else
               {
                   //Alter Visible Elements
                   rbOnce.isChecked = false
                   rbRep.isChecked=true
                   tvDate.setVisibility(View.GONE);
                   tvPickedDate.setVisibility(View.GONE);
                   btnSelectDate.setVisibility(View.GONE);
                   divider7.setVisibility(View.GONE)

                   tvRepeat.setVisibility(View.VISIBLE)
                   divider3.setVisibility(View.VISIBLE)
                   tbMon.setVisibility(View.VISIBLE)
                   tbTue.setVisibility(View.VISIBLE)
                   tbWed.setVisibility(View.VISIBLE)
                   tbThu.setVisibility(View.VISIBLE)
                   tbFri.setVisibility(View.VISIBLE)
                   tbSat.setVisibility(View.VISIBLE)
                   tbSun.setVisibility(View.VISIBLE)

                   isDate = 0
               }
           }
        }
    }


    fun dateTimeValidator(dt: String): Int {
        if (dt.length == 12) {
            val cal = Calendar.getInstance()

            var cDay = cal.get(Calendar.DAY_OF_MONTH).toString()
            var cMonth = (cal.get(Calendar.MONTH) + 1).toString()

            val cYear = cal.get(Calendar.YEAR).toString()
            var cHour = cal.get(Calendar.HOUR_OF_DAY).toString()
            var cMin = cal.get(Calendar.MINUTE).toString()
            val c: Long

            if (cDay.length < 2) {
                cDay = "0$cDay"
            }
            if (cMonth.length < 2) {
                cMonth = "0$cMonth"
            }
            if (cHour.length < 2) {
                cHour = "0$cHour"
            }
            if (cMin.length < 2) {
                cMin = "0$cMin"
            }

            c = "$cYear$cMonth$cDay$cHour$cMin".toLong()

            if (dt.toLong() > c) {
                return 1
            }


        }
        return 0
    }

    fun onOff(btn: ToggleButton): Int {
        if (btn.isChecked) {
            return 1
        }
        return 0
    }

    fun concatDate(d: Int, m: Int, y: Int): String {
        var date: String
        if (d < 10 && m < 10) {
            date = "0" + d.toString() + "/0" + m.toString() + "/" + y.toString()
        } else if (m < 10) {
            date = d.toString() + "/0" + m.toString() + "/" + y.toString()
        } else if (d < 10) {
            date = "0" + d.toString() + "/" + m.toString() + "/" + y.toString()
        } else {
            date = d.toString() + "/" + m.toString() + "/" + y.toString()
        }
        return date
    }

    fun concatTime(min: Int, hour: Int): String {
        var tm: String

        if (hour < 10 && min < 10) {
            tm = "0" + hour.toString() + ":0" + min.toString()
        } else if (hour < 10) {
            tm = "0" + hour.toString() + ":" + min.toString()
        } else if (min < 10) {
            tm = hour.toString() + ":0" + min.toString()
        } else {
            tm = hour.toString() + ":" + min.toString()
        }

        return tm
    }
}
