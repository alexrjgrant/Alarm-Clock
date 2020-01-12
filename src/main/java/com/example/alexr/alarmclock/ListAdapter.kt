package com.example.alexr.alarmclock

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.*
import kotlinx.android.synthetic.main.alarm.view.*

class MyListAdapter(
    private val context: Activity,
    private val name: MutableList<String>,
    private val time: MutableList<String>,
    private val date: MutableList<String>,
    private val days: MutableList<String>,
    private val id: MutableList<String>
) : ArrayAdapter<String>(context, R.layout.alarm, id) {

    override fun getView(pos: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.alarm, null, true)

        rowView.tvName.text = name[pos]
        rowView.tvTime.text = time[pos]
        rowView.tvDate.text = date[pos]
        rowView.tvDays.text = days[pos]

        return rowView
    }
}

