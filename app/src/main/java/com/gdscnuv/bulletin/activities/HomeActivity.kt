package com.gdscnuv.bulletin.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.LinearInterpolator
import android.widget.TextView
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DiffUtil
import com.gdscnuv.bulletin.adapters.CardStackAdapter
import com.gdscnuv.bulletin.helpers.SpotDiffCallback
import com.gdscnuv.bulletin.models.Event
import com.gdscnuv.bulletin.R
import com.yuyakaido.android.cardstackview.*

class HomeActivity : AppCompatActivity(), CardStackListener {
    private val cardStackView by lazy { findViewById<CardStackView>(R.id.card_stack_view) }
    private val manager by lazy { CardStackLayoutManager(this, this) }
    private val adapter by lazy { CardStackAdapter(createEvents()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setupCardStackView()
    }

    override fun onCardDragging(direction: Direction?, ratio: Float) { }

    override fun onCardSwiped(direction: Direction) {
        Log.d("CardStackView", "onCardSwiped: p = ${manager.topPosition}, d = $direction")
        if (manager.topPosition == adapter.itemCount - 5) {
            paginate()
        }
    }

    override fun onCardRewound() { }

    override fun onCardCanceled() { }

    override fun onCardAppeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.card_event_name)
        Log.d("CardStackView", "onCardAppeared: ($position) ${textView.text}")
    }

    override fun onCardDisappeared(view: View, position: Int) {
        val textView = view.findViewById<TextView>(R.id.card_event_name)
        Log.d("CardStackView", "onCardDisappeared: ($position) ${textView.text}")
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
        val events = ArrayList<Event>()
        events.add(Event(name = "Yasaka Shrine", organizers = "Kyoto", url = "https://source.unsplash.com/Xq1ntWruZQI/600x800"))
        events.add(Event(name = "Fushimi Inari Shrine", organizers = "Kyoto", url = "https://source.unsplash.com/NYyCqdBOKwc/600x800"))
        events.add(Event(name = "Bamboo Forest", organizers = "Kyoto", url = "https://source.unsplash.com/buF62ewDLcQ/600x800"))
        events.add(Event(name = "Brooklyn Bridge", organizers= "New York", url = "https://source.unsplash.com/THozNzxEP3g/600x800"))
        events.add(Event(name = "Empire State Building", organizers = "New York", url = "https://source.unsplash.com/USrZRcRS2Lw/600x800"))
        events.add(Event(name = "The statue of Liberty", organizers = "New York", url = "https://source.unsplash.com/PeFk7fzxTdk/600x800"))
        events.add(Event(name = "Louvre Museum", organizers = "Paris", url = "https://source.unsplash.com/LrMWHKqilUw/600x800"))
        events.add(Event(name = "Eiffel Tower", organizers= "Paris", url = "https://source.unsplash.com/HN-5Z6AmxrM/600x800"))
        events.add(Event(name = "Big Ben",organizers = "London", url = "https://source.unsplash.com/CdVAUADdqEc/600x800"))
        events.add(Event(name = "Great Wall of China", organizers= "China", url = "https://source.unsplash.com/AWh9C-QjhE4/600x800"))
        return events
    }
}