package com.gdscnuv.bulletin.adapters

import android.app.Activity
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.gdscnuv.bulletin.R
import com.gdscnuv.bulletin.models.RegisteredEvent
import com.makeramen.roundedimageview.RoundedImageView

class CalenderCardListAdapter(private val context: Activity, private val event:List<RegisteredEvent>)
    : ArrayAdapter<RegisteredEvent>(context, R.layout.calendar_card, event) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        Log.e("!!", event.toString())
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.calendar_card, null, true)

        val titleText = rowView.findViewById(R.id.card_calender_title) as TextView
        val subtitleText = rowView.findViewById(R.id.card_calender_description) as TextView
        val imageView = rowView.findViewById(R.id.card_calender_image) as RoundedImageView

        titleText.text = event[position].title
        subtitleText.text = event[position].desc
        Glide.with(imageView)
            .load(event[position].url)
            .into(imageView)

        return rowView
    }
}