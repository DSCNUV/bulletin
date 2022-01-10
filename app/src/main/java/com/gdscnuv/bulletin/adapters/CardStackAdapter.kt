package com.gdscnuv.bulletin.adapters


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.gdscnuv.bulletin.models.Event
import com.gdscnuv.bulletin.R

class CardStackAdapter(
    private var events: List<Event> = emptyList()
) : RecyclerView.Adapter<CardStackAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return ViewHolder(inflater.inflate(R.layout.swipe_card, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val spot = events[position]
        holder.name.text = spot.name
        holder.organizers.text = spot.organizers
        holder.desc.text = "Organizers: " +spot.desc
        holder.date.text = "Date: "+spot.date
        Glide.with(holder.image)
            .load(spot.url)
            .into(holder.image)
        holder.itemView.setOnClickListener { v ->
            Toast.makeText(v.context, spot.name, Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount(): Int {
        return events.size
    }

    fun setEvent(events: List<Event>) {
        this.events = events
    }

    fun getEvents(): List<Event> {
        return events
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.card_event_name)
        var organizers: TextView = view.findViewById(R.id.card_event_organizer)
        val desc:TextView = view.findViewById(R.id.card_event_desc)
        val date:TextView = view.findViewById(R.id.card_event_date)
        var image: ImageView = view.findViewById(R.id.item_image)
    }

}