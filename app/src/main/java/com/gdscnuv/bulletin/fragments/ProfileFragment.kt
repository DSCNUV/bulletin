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
import com.gdscnuv.bulletin.models.Event
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*
import kotlin.collections.ArrayList


class ProfileFragment : Fragment() {
    lateinit var allEvents:ArrayList<Event>
    lateinit var listView:ListView
    lateinit var calendar:CalendarView
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_profile, container, false)
        calendar = rootView.findViewById(R.id.calendarView)
        calendar.date = Date().time

        listView = rootView.findViewById(R.id.current_events)
        getEvents(true)

        return rootView
    }

    private fun updateUI_(){
        val TAG = "UPDATEUI_"
        val allEventsData = allEvents
        Log.v(TAG, allEventsData.toString())
        var currentEvents = mutableListOf<Event>();
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
    }
    private fun updateUI(doc:MutableMap<String, Any>){
        val TAG = "UPDATEUI"
        val event_ = Event(doc.get("id")!!, doc.get("name").toString(), doc.get("desc").toString(),doc.get("organizers").toString(), doc.get("date").toString(), doc.get("url").toString())
        allEvents.add(event_)
    }
    fun getEvents(a:Boolean) {
        val TAG = "events"
        var db: FirebaseFirestore = FirebaseFirestore.getInstance()

        val xev = db.collection("users").document(getUid_()).collection("savedEvents")
        xev.addSnapshotListener { snapshot, e ->
            if (e != null) {
                Log.w(TAG, "Listen failed.", e)
                return@addSnapshotListener
            }
            if (snapshot != null && !snapshot.documentChanges.isEmpty()) {
                    allEvents = ArrayList<Event>()
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