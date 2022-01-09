package com.gdscnuv.bulletin.fragments

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CalendarView
import android.widget.CalendarView.OnDateChangeListener
import android.widget.ListView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.gdscnuv.bulletin.R
import com.gdscnuv.bulletin.adapters.CalenderCardListAdapter
import com.gdscnuv.bulletin.models.RegisteredEvent
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ProfileFragment : Fragment() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_profile, container, false)
        val calendar:CalendarView= rootView.findViewById(R.id.calendarView)
        calendar.date = Date().time

        val listView:ListView = rootView.findViewById(R.id.current_events)
        val allEventsData = dummyData()
        var currentEvents = mutableListOf<RegisteredEvent>();
        var myListAdapter = CalenderCardListAdapter(
            this.context as Activity,
            currentEvents
        )
        listView.adapter = myListAdapter

        calendar.setOnDateChangeListener(OnDateChangeListener { _, year, month, dayOfMonth ->
            val curDate = dayOfMonth.toString()
            val Year = year.toString()
            val Month = (month + 1).toString()
            val date: String = "$curDate/$Month/$Year"
            currentEvents.clear()
            for (event in allEventsData) {
                if (event.date == date) {
                    currentEvents.add(event);
                }
            }
            myListAdapter.notifyDataSetChanged()
        })
        return rootView
    }

    fun dummyData():List<RegisteredEvent>{
        val events = ArrayList<RegisteredEvent>()
        events.add(
            RegisteredEvent(
                id = "123",
                title = "Yasaka Shrine",
                desc = "Kyoto",
                url = "https://source.unsplash.com/Xq1ntWruZQI/600x800",
                date = "9/1/2022"
            )
        )
        events.add(
            RegisteredEvent(
                id = "127",
                title = "fdslkfd",
                desc = "Kyoto",
                url = "https://source.unsplash.com/Xq1ntWruZQI/600x800",
                date = "9/1/2022"
            )
        )
        events.add(
            RegisteredEvent(
                id = "124",
                title = "fdfasYasa",
                desc = "Kyoto",
                url = "https://source.unsplash.com/Xq1ntWruZQI/600x800",
                date = "29/1/2022"
            )
        )
        events.add(
            RegisteredEvent(
                id = "125",
                title = "fadsfdsfd Shrine",
                desc = "Kyoto",
                url = "https://source.unsplash.com/Xq1ntWruZQI/600x800",
                date = "12/1/2022"
            )
        )
        events.add(
            RegisteredEvent(
                id = "126",
                title = "fffffffff",
                desc = "Kyoto",
                url = "https://source.unsplash.com/Xq1ntWruZQI/600x800",
                date = "17/1/2022"
            )
        )
        return events
    }
}