package com.gdscnuv.bulletin.fragments

import android.app.Activity
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.gdscnuv.bulletin.R
import com.gdscnuv.bulletin.adapters.CalenderCardListAdapter
import com.gdscnuv.bulletin.models.Event
import com.github.sundeepk.compactcalendarview.CompactCalendarView
import com.github.sundeepk.compactcalendarview.CompactCalendarView.CompactCalendarViewListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

@RequiresApi(Build.VERSION_CODES.O)
class ProfileFragment : Fragment() {
    var allEvents:ArrayList<Event> = ArrayList<Event>()
    lateinit var listView:ListView
    lateinit var  calendarView: CompactCalendarView
    lateinit var calendarMonth:TextView
    val dateFormatForMonth = SimpleDateFormat("MMM - yyyy", Locale.getDefault())

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_profile, container, false)
        calendarView = rootView.findViewById(R.id.calender_view)
        calendarMonth = rootView.findViewById(R.id.calender_view_title)


        calendarMonth.text = dateFormatForMonth.format(calendarView.firstDayOfCurrentMonth)
        calendarView.setFirstDayOfWeek(Calendar.MONDAY)
        calendarView.shouldSelectFirstDayOfMonthOnScroll(false)

        listView = rootView.findViewById(R.id.current_events)
        getEvents(true)

        return rootView
    }

    private fun updateUI_(){
        val TAG = "UPDATEUI_"
        val allEventsData = allEvents
        Log.v(TAG, allEventsData.toString())
        var currentEvents = mutableListOf<Event>();

        val events: MutableList<com.github.sundeepk.compactcalendarview.domain.Event>? = calendarView.getEvents(Date())
        if(events!!.size >0){
            for(x in events){
                for(y in allEvents){
                    if(x.data == y.name)
                        currentEvents.add(y)
                }
            }
        }

        var myListAdapter = CalenderCardListAdapter(
            this.context as Activity,
            currentEvents
        )
        listView.adapter = myListAdapter

        calendarView.setListener(object : CompactCalendarViewListener {
            override fun onDayClick(dateClicked: Date?) {
                val events: MutableList<com.github.sundeepk.compactcalendarview.domain.Event>? = calendarView.getEvents(dateClicked)
                if(events!!.size >0){
                    currentEvents.clear()
                    for(x in events){
                        for(y in allEvents){
                            if(x.data == y.name)
                                currentEvents.add(y)
                        }
                    }
                }else{
                    currentEvents.clear()
                }
                myListAdapter.notifyDataSetChanged()
            }
            override fun onMonthScroll(firstDayOfNewMonth: Date?) {
                calendarMonth.text = dateFormatForMonth.format(firstDayOfNewMonth)
            }
        })
    }

    private fun updateUI(doc: MutableMap<String, Any>){
        val TAG = "UPDATEUI"
        val event_ = Event(
            doc.get("id")!!, doc.get("name").toString(), doc.get("desc").toString(), doc.get(
                "organizers"
            ).toString(), doc.get("date").toString(), doc.get("url").toString()
        )
        allEvents.add(event_)
        Log.e("date:", event_.date)
        var l = LocalDate.parse(event_.date, DateTimeFormatter.ofPattern("dd/MM/yyyy"))
        val ev1 = com.github.sundeepk.compactcalendarview.domain.Event(
            Color.rgb(245, 208, 207),
            l.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli(),
            event_.name
        )
        calendarView.addEvent(ev1)
    }

    fun getEvents(a: Boolean) {
        val TAG = "events"
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val xev = db.collection("users").document(getUid_()).collection("savedEvents")
        xev.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && !snapshot.documentChanges.isEmpty()) {
                    allEvents.clear()
                    calendarView.removeAllEvents()
                    for (doc in snapshot.documents) {
                        updateUI(doc.data!!)
                    }
                    if (a)
                        updateUI_()
                Log.d(TAG, "Current documents: ${snapshot.documents}")
            } else {
                Log.d(TAG, "Current data: null")
            }
        }
    }

    private fun getUid_():String{
        val firebaseUser: FirebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseUser.uid
        return uid.toString()
    }
}