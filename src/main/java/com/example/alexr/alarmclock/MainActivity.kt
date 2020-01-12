package com.example.alexr.alarmclock
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast
import java.util.*

var runSQL: MethodsSQL? = null

class MainActivity : AppCompatActivity() {



    val nm = mutableListOf<String>()
    val tm = mutableListOf<String>()
    val dt = mutableListOf<String>()
    val dy = mutableListOf<String>()
    val id = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        runSQL = MethodsSQL(this)

        fab.setOnClickListener(){
            val intent = Intent(this, addAlarm::class.java)
                    intent.putExtra("AlarmID", "")
                    startActivity(intent)
}

//        b.setOnClickListener {}

            for(i in runSQL!!.GetAlarms().indices)
            {
                nm += runSQL!!.GetAlarms()[i].name

               var a = runSQL!!.GetAlarms()[i]


                tm += concatTime(a.min,a.hour)

                if(a.isDate == 1) {

                    dt+=concatDate(a.d,a.m,a.y)
                }
                else if (a.isDate == 0)
                {
                    dt += "Repeat"
                }

                var e = ""

                if(a.mon == 1)
                {
                    e += "Mon "
                }
                if(a.tue == 1)
                {
                    e += "Tue "
                }
                if(a.wed == 1)
                {
                    e += "Wed "
                }
                if(a.thu == 1)
                {
                    e += "Thu "
                }
                if(a.fri == 1)
                {
                    e += "Fri "
                }
                if(a.sat == 1)
                {
                    e += "Sat "
                }
                if(a.sun == 1)
                {
                    e += "Sun "
                }

                dy += e


                id += a.ID.toString()


            }






//        a.setOnClickListener{
//
//
//
//        }



        val myListAdapter = MyListAdapter(this,nm,tm,dt,dy,id)
        listView.adapter = myListAdapter

        listView.setOnItemClickListener{adapterView, view, pos, _ ->
            val alID = adapterView.getItemAtPosition(pos)

                showPopup(view,alID.toString().toLong())
        }







        val am = getSystemService(Context.ALARM_SERVICE) as AlarmManager

//        var date = Date()
//        val c = Calendar.getInstance()
//
//        c.setTime(date)
//
//
//        c.set(Calendar.HOUR_OF_DAY,1)
//        c.set(Calendar.MINUTE ,20)
//        c.set(Calendar.SECOND,0)
//
//        var aID = 1
//
////        if(c.timeInMillis > java.lang.System.currentTimeMillis()) {
//
//            val i = Intent(this@MainActivity, BR::class.java)
//            i.putExtra("aID", aID.toString())
//            var pi = PendingIntent.getBroadcast(this@MainActivity, aID, i, 0)
//            am.set(AlarmManager.RTC_WAKEUP,  java.lang.System.currentTimeMillis()+5000, pi)
//        }




        for(i in runSQL!!.GetAlarms().indices)
        {

            val a = runSQL!!.GetAlarms()[i]

            if (a.isDate == 1) {

                val date = Date()
                val c = Calendar.getInstance()
                c.setTime(date)

                c.set(Calendar.YEAR, a.y)
                c.set(Calendar.MONTH, a.m - 1)
                c.set(Calendar.DAY_OF_MONTH, a.d)
                c.set(Calendar.HOUR_OF_DAY, a.hour)
                c.set(Calendar.MINUTE, a.min)
                c.set(Calendar.SECOND, 0)

                if (c.timeInMillis > java.lang.System.currentTimeMillis())
                {
                    val intentA = Intent(this@MainActivity, BR::class.java)
                    intentA.putExtra("aID", a.ID.toString())
                   // intentA.putExtra("VIB", a.vib.toString())
                    val pi = PendingIntent.getBroadcast(this@MainActivity, a.ID, intentA, PendingIntent.FLAG_UPDATE_CURRENT)
                    am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                }

                Log.d("TI", c.timeInMillis.toString())
            }
            if(a.isDate == 0 )
            {
                val date = Date()

                val c = Calendar.getInstance()
                c.setTime(date)

                c.set(Calendar.YEAR, a.y)
                c.set(Calendar.MONTH, a.m - 1)
                c.set(Calendar.DAY_OF_MONTH, a.d)
                c.set(Calendar.HOUR_OF_DAY, a.hour)
                c.set(Calendar.MINUTE, a.min)
                c.set(Calendar.SECOND, 0)

                if(a.sun == 1)
                {
                    val selDay = 1
                    val dow = c.get(Calendar.DAY_OF_WEEK)
                    var addDays = 0

                    if (dow > selDay)
                    {
                        addDays = 7-( dow - selDay)
                    }
                    else if (selDay > dow)
                    {
                        addDays = selDay - dow
                    }
                    else if(selDay == dow)
                    {
                        if (c.timeInMillis < java.lang.System.currentTimeMillis())
                        {
                            addDays = 7
                        }
                    }
                    c.add(Calendar.DAY_OF_MONTH,addDays)

                    if (c.timeInMillis > java.lang.System.currentTimeMillis()) {

                        val intentSun = Intent(this@MainActivity, BR::class.java)
                        intentSun.putExtra("aID", a.ID.toString()+"1")
                        val pi = PendingIntent.getBroadcast(this@MainActivity, (a.ID.toString()+"1").toInt(), intentSun, 0)
                        am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                    }
                }
                if(a.mon == 1)
                {
                    val selDay = 2 //(2 = MON)
                    val dow = c.get(Calendar.DAY_OF_WEEK)
                    var addDays = 0

                    if (dow > selDay)
                    {
                        addDays = 7-( dow - selDay)
                    }
                    else if (selDay > dow)
                    {
                        addDays = selDay - dow
                    }
                    else if(selDay == dow)
                    {
                        if (c.timeInMillis < java.lang.System.currentTimeMillis())
                        {
                            addDays = 7
                        }
                    }
                    c.add(Calendar.DAY_OF_MONTH,addDays)

                    if (c.timeInMillis > java.lang.System.currentTimeMillis()) {

                        val intentMon = Intent(this@MainActivity, BR::class.java)
                        intentMon.putExtra("aID", a.ID.toString()+"2")
                        val pi = PendingIntent.getBroadcast(this@MainActivity, (a.ID.toString()+"2").toInt(), intentMon, 0)
                        am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                    }
                }
                if(a.tue == 1)
                {
                    val selDay = 3 //(2 = MON)
                    val dow = c.get(Calendar.DAY_OF_WEEK)
                    var addDays = 0

                    if (dow > selDay)
                    {
                        addDays = 7-( dow - selDay)
                    }
                    else if (selDay > dow)
                    {
                        addDays = selDay - dow
                    }
                    else if(selDay == dow)
                    {
                        if (c.timeInMillis < java.lang.System.currentTimeMillis())
                        {
                            addDays = 7
                        }
                    }
                    c.add(Calendar.DAY_OF_MONTH,addDays)

                    if (c.timeInMillis > java.lang.System.currentTimeMillis()) {

                        val intentTue = Intent(this@MainActivity, BR::class.java)
                        intentTue.putExtra("aID", a.ID.toString()+"3")
                        val pi = PendingIntent.getBroadcast(this@MainActivity, (a.ID.toString()+"3").toInt(), intentTue, 0)
                        am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                    }
                }
                if(a.wed == 1)
                {
                    val selDay = 4 //(2 = MON)
                    val dow = c.get(Calendar.DAY_OF_WEEK)
                    var addDays = 0

                    if (dow > selDay)
                    {
                        addDays = 7-( dow - selDay)
                    }
                    else if (selDay > dow)
                    {
                        addDays = selDay - dow
                    }
                    else if(selDay == dow)
                    {
                        if (c.timeInMillis < java.lang.System.currentTimeMillis())
                        {
                            addDays = 7
                        }
                    }
                    c.add(Calendar.DAY_OF_MONTH,addDays)

                    if (c.timeInMillis > java.lang.System.currentTimeMillis()) {

                        val intentWed = Intent(this@MainActivity, BR::class.java)
                        intentWed.putExtra("aID", a.ID.toString()+"4")
                        val pi = PendingIntent.getBroadcast(this@MainActivity, (a.ID.toString()+"4").toInt(), intentWed, 0)
                        am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                    }
                }
                if(a.thu == 1)
                {
                    val selDay = 5 //(2 = MON)
                    val dow = c.get(Calendar.DAY_OF_WEEK)
                    var addDays = 0

                    if (dow > selDay)
                    {
                        addDays = 7-( dow - selDay)
                    }
                    else if (selDay > dow)
                    {
                        addDays = selDay - dow
                    }
                    else if(selDay == dow)
                    {
                        if (c.timeInMillis < java.lang.System.currentTimeMillis())
                        {
                            addDays = 7
                        }
                    }
                    c.add(Calendar.DAY_OF_MONTH,addDays)

                    if (c.timeInMillis > java.lang.System.currentTimeMillis()) {

                        val intentThu = Intent(this@MainActivity, BR::class.java)
                        intentThu.putExtra("aID", a.ID.toString()+"5")
                        val pi = PendingIntent.getBroadcast(this@MainActivity, (a.ID.toString()+"5").toInt(), intentThu, 0)
                        am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                    }
                }
                if(a.fri == 1)
                {
                    val selDay = 6 //(2 = MON)
                    val dow = c.get(Calendar.DAY_OF_WEEK)
                    var addDays = 0

                    if (dow > selDay)
                    {
                        addDays = 7-( dow - selDay)
                    }
                    else if (selDay > dow)
                    {
                        addDays = selDay - dow
                    }
                    else if(selDay == dow)
                    {
                        if (c.timeInMillis < java.lang.System.currentTimeMillis())
                        {
                            addDays = 7
                        }
                    }
                    c.add(Calendar.DAY_OF_MONTH,addDays)

                    if (c.timeInMillis > java.lang.System.currentTimeMillis()) {

                        val intentFri = Intent(this@MainActivity, BR::class.java)
                        intentFri.putExtra("aID", a.ID.toString()+"6")
                        val pi = PendingIntent.getBroadcast(this@MainActivity, (a.ID.toString()+"6").toInt(), intentFri, 0)
                        am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                    }
                }
                if(a.sat == 1)
                {
                    val selDay = 7 //(2 = MON)
                    val dow = c.get(Calendar.DAY_OF_WEEK)
                    var addDays = 0

                    if (dow > selDay)
                    {
                        addDays = 7-( dow - selDay)
                    }
                    else if (selDay > dow)
                    {
                        addDays = selDay - dow
                    }
                    else if(selDay == dow)
                    {
                        if (c.timeInMillis < java.lang.System.currentTimeMillis())
                        {
                            addDays = 7
                        }
                    }
                    c.add(Calendar.DAY_OF_MONTH,addDays)

                    if (c.timeInMillis > java.lang.System.currentTimeMillis()) {

                        val intentSat = Intent(this@MainActivity, BR::class.java)
                        intentSat.putExtra("aID", a.ID.toString()+"7")
                        val pi = PendingIntent.getBroadcast(this@MainActivity, (a.ID.toString()+"7").toInt(), intentSat, 0)
                        am.set(AlarmManager.RTC_WAKEUP, c.timeInMillis, pi)
                    }
                }

            }
        }



    }

    fun showPopup(view: View, alarmID: Long) {
        val popup: PopupMenu?
        popup = PopupMenu(this, view)
        popup.inflate(R.menu.menu)

        popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

            when (item!!.itemId) {
                R.id.del -> {

                    val n = runSQL!!.delete(alarmID)

                    if(n > 0)
                    {
                        toast("Deletion Successful")
                        finish();startActivity(getIntent())
                    }
                    else
                    {
                        toast("Error")
                    }
                    toast(alarmID.toString())

                }
                R.id.ed -> {

                    val intent = Intent(this, addAlarm::class.java)
                    intent.putExtra("AlarmID", alarmID.toString())
                    startActivity(intent)
                }

            }

            true
        })

        popup.show()
    }
fun concatDate(d:Int,m:Int,y:Int):String
{
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
    fun concatTime(min:Int,hour:Int):String{
        var tm : String

        if (hour < 10 && min < 10)
        {tm ="0"+ hour.toString() + ":0" + min.toString()}
        else if (hour < 10)
        {tm = "0"+ hour.toString() + ":" + min.toString()}
        else if (min < 10)
        {tm = hour.toString() + ":0" + min.toString()}
        else
        {tm = hour.toString() + ":" + min.toString()}

        return tm
    }





}