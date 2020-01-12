package com.example.alexr.alarmclock

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class MethodsSQL(ctx: Context) : SQLiteOpenHelper(ctx, "AlarmDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {

        db.execSQL("CREATE TABLE IF NOT EXISTS Alarms ( Id INTEGER PRIMARY KEY," +
                " name VARCHAR(255), hour INTEGER(2), min INTEGER(2), isDate INTEGER(1), y INTEGER(2), m INTEGER(2), d INTEGER(2), vib INTEGER(1)," +
                "mon INTEGER(1), tue INTEGER(1), wed INTEGER(1), thu INTEGER(1), fri INTEGER(1), sat INTEGER(1), sun INTEGER(1) )")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS Alarms")
        onCreate(db)
    }

    fun GetAlarms(): List<Alarm> {
        val alarms = mutableListOf<Alarm>()
        val db = getReadableDatabase()
        val cursor = db.rawQuery(
            "SELECT * FROM Alarms",null
        )

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                val a = Alarm(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("hour")),
                    cursor.getInt(cursor.getColumnIndex("min")),
                    cursor.getInt(cursor.getColumnIndex("isDate")),
                    cursor.getInt(cursor.getColumnIndex("y")),
                    cursor.getInt(cursor.getColumnIndex("m")),
                    cursor.getInt(cursor.getColumnIndex("d")),
                    cursor.getInt(cursor.getColumnIndex("vib")),
                    cursor.getInt(cursor.getColumnIndex("mon")),
                    cursor.getInt(cursor.getColumnIndex("tue")),
                    cursor.getInt(cursor.getColumnIndex("wed")),
                    cursor.getInt(cursor.getColumnIndex("thu")),
                    cursor.getInt(cursor.getColumnIndex("fri")),
                    cursor.getInt(cursor.getColumnIndex("sat")),
                    cursor.getInt(cursor.getColumnIndex("sun"))
                )

                alarms.add(a)
                cursor.moveToNext()
            }
        }
        cursor.close()

        return alarms

    }
    fun findAlarm(id : String): List<Alarm> {
        val alarms = mutableListOf<Alarm>()
        val db = getReadableDatabase()
        val cursor = db.rawQuery(
            "SELECT * FROM Alarms WHERE Id = ?",
            arrayOf<String>(id)
        )

        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                val a = Alarm(
                    cursor.getInt(cursor.getColumnIndex("Id")),
                    cursor.getString(cursor.getColumnIndex("name")),
                    cursor.getInt(cursor.getColumnIndex("hour")),
                    cursor.getInt(cursor.getColumnIndex("min")),
                    cursor.getInt(cursor.getColumnIndex("isDate")),
                    cursor.getInt(cursor.getColumnIndex("y")),
                    cursor.getInt(cursor.getColumnIndex("m")),
                    cursor.getInt(cursor.getColumnIndex("d")),
                    cursor.getInt(cursor.getColumnIndex("vib")),
                    cursor.getInt(cursor.getColumnIndex("mon")),
                    cursor.getInt(cursor.getColumnIndex("tue")),
                    cursor.getInt(cursor.getColumnIndex("wed")),
                    cursor.getInt(cursor.getColumnIndex("thu")),
                    cursor.getInt(cursor.getColumnIndex("fri")),
                    cursor.getInt(cursor.getColumnIndex("sat")),
                    cursor.getInt(cursor.getColumnIndex("sun"))
                )

                alarms.add(a)
                cursor.moveToNext()
            }
        }
        cursor.close()

        return alarms

    }
    fun insert(name: String, hour: Int, min: Int, isDate: Int,
               y: Int, m: Int,d: Int,
               vib: Int,
               mon: Int, tue: Int, wed: Int,
               thu: Int, fri: Int, sat: Int, sun: Int): Long {

        val db = getWritableDatabase()
        val stmt = db.compileStatement(
            "INSERT INTO Alarms(name,hour,min,isDate,y,m,d,vib,mon,tue,wed,thu,fri,sat,sun) " +
                "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)")
        stmt.bindString(1, name)
        stmt.bindLong(2, hour.toLong())
        stmt.bindLong(3, min.toLong())
        stmt.bindLong(4, isDate.toLong())
        stmt.bindLong(5, y.toLong())
        stmt.bindLong(6, m.toLong())
        stmt.bindLong(7, d.toLong())
        stmt.bindLong(8, vib.toLong())
        stmt.bindLong(9, mon.toLong())
        stmt.bindLong(10, tue.toLong())
        stmt.bindLong(11, wed.toLong())
        stmt.bindLong(12, thu.toLong())
        stmt.bindLong(13, fri.toLong())
        stmt.bindLong(14, sat.toLong())
        stmt.bindLong(15, sun.toLong())

        val id = stmt.executeInsert()
        return id
    }
    fun delete(id: Long): Int {
        val db = getWritableDatabase()
        val stmt = db.compileStatement("DELETE FROM Alarms WHERE Id=?")
        stmt.bindString(1, id.toString())
        val nAffectedRows = stmt.executeUpdateDelete()
        return nAffectedRows
    }

}