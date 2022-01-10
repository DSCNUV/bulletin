package com.gdscnuv.bulletin.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.LinearInterpolator
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.gdscnuv.bulletin.R
import com.gdscnuv.bulletin.adapters.CardStackAdapter
import com.gdscnuv.bulletin.helpers.SpotDiffCallback
import com.gdscnuv.bulletin.helpers.StoreData
import com.gdscnuv.bulletin.models.Event
import com.yuyakaido.android.cardstackview.*
import java.util.concurrent.TimeUnit

class EventsFragment : Fragment(), CardStackListener {
    private lateinit var cardStackView:CardStackView
    private lateinit var manager:CardStackLayoutManager
    private lateinit var adapter:CardStackAdapter
    private lateinit var events:ArrayList<Event>
    val x = StoreData()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView:View = inflater.inflate(R.layout.fragment_events, container, false)
        cardStackView = rootView.findViewById<CardStackView>(R.id.card_stack_view)
        manager = CardStackLayoutManager(activity, this)
        adapter = CardStackAdapter(createEvents())

        setupCardStackView()
        return rootView;
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) { }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        val TAG = "Swiper"
        Log.v(TAG, "${adapter.itemCount - manager.topPosition}")
        if (adapter.itemCount - manager.topPosition == 1) {
            if(direction.name == "Left") {
                manager.topPosition -= 1
            }
            else if(direction.name == "Right"){
                paginate()
            }
        }
        else{
            if(direction.name == "Right"){
                StoreData().saveEvents(adapter.getEvents()[manager.topPosition-1])
                Toast.makeText(this.context, "Right Swiped!", Toast.LENGTH_SHORT).show()
            }
            else if(direction.name == "Left") {
            }
        }
    }

    override fun onCardRewound() { }

    override fun onCardCanceled() { }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.card_event_name)
//        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.card_event_name)
//        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
    }

    private fun setupCardStackView() {
        initialize()
    }

    private fun initialize() {
        manager.setStackFrom(StackFrom.None)
        manager.setVisibleCount(3)
        manager.setTranslationInterval(8.0f)
        manager.setScaleInterval(0.95f)
        manager.setSwipeThreshold(0.3f)
        manager.setMaxDegree(20.0f)
        manager.setDirections(Direction.HORIZONTAL)
        manager.setCanScrollHorizontal(true)
        manager.setCanScrollVertical(true)
        manager.setSwipeableMethod(SwipeableMethod.AutomaticAndManual)
        manager.setOverlayInterpolator(LinearInterpolator())
        cardStackView.layoutManager = manager
        cardStackView.adapter = adapter
        cardStackView.itemAnimator.apply {
            if (this is DefaultItemAnimator) {
                supportsChangeAnimations = false
            }
        }
    }

    private fun paginate() {
        val old = adapter.getEvents()
        val new = old.plus(createEvents())
        val callback = SpotDiffCallback(old, new)
        val result = DiffUtil.calculateDiff(callback)
        adapter.setEvent(new)
        result.dispatchUpdatesTo(adapter)
    }

    private fun createEvents(): List<Event> {
        events = ArrayList<Event>()
        events.add(Event(name = "Android Study Jam", desc = "Start you Android journey by learning Kotlin today!", organizers = "GDSC", date = "9/1/2022",url = "https://source.unsplash.com/ourQHRTE2IM/600x800"))
        events.add(Event(name = "HacktoberFest NUV", desc = "Let get open-sourcing", organizers = "GDSCNUV, HackClub NUV, MozNUV", date = "22/1/2022", url = "https://source.unsplash.com/DOu3JJ3eLQc/600x800"))
        events.add(Event(name = "NUVdeep", desc = "Celebrate the festival of lights!", organizers = "CSE", date = "11/1/2022", url = "https://source.unsplash.com/249DzAuJTqQ/600x800"))
        events.add(Event(name = "30 Days of Google Cloud", desc = "Learn to work with Google Cloud", organizers= "GDSC", date = "8/1/2022", url = "https://source.unsplash.com/SYTO3xs06fU/600x800"))
        events.add(Event(name = "Vibin", desc = "Networking & feedback session", organizers = "Hack Club NUV", date = "13/1/2022", url = "https://source.unsplash.com/wnf_LJiJG0E/600x800"))
        events.add(Event(name = "Capture the Flag", desc = "Getting started with cyber security", organizers = "CDS", date = "16/1/2022", url = "https://source.unsplash.com/HhNe16wgVFg/600x800"))
        events.add(Event(name = "Open mic", desc = "A evening to showcase your hidden talents", organizers = "Cultural Committee", date = "20/1/2022", url = "https://source.unsplash.com/GkWP64truqg/600x800"))
        events.add(Event(name = "BBA Bazigar", desc = "Learn to be an entrepreneur", organizers= "BBA", date = "18/1/2022", url = "https://source.unsplash.com/Hb6uWq0i4MI/600x800"))
        events.add(Event(name = "Flutter bootcamp", desc = "Start learning flutter", organizers = "GDSC", date = "22/1/2022", url = "https://source.unsplash.com/QBpZGqEMsKg/600x800"))
        events.add(Event(name = "Break the ice", desc = "Networking & high tea", organizers= "GDSC", date = "31/1/2022", url = "https://source.unsplash.com/mqoLpeeYBic/600x800"))
        events.add(Event(name = "Food festival", desc = "Taste the variety", organizers = "TEAM Bulletin", date = "4/1/2022", url="https://source.unsplash.com/ptf91j8SFiM/600x800"))
        events.add(Event(name = "End", desc = "Last slide", organizers = "TEAM Bulletin", date = "NA", url="https://cdn.discordapp.com/attachments/928708312223084594/930037486233739274/last_slide.png"))
        return events
    }
}