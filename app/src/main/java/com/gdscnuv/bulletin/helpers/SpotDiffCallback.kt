package com.gdscnuv.bulletin.helpers

import androidx.recyclerview.widget.DiffUtil
import com.gdscnuv.bulletin.models.Event

class SpotDiffCallback(
    private val old: List<Event>,
    private val new: List<Event>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return old.size
    }

    override fun getNewListSize(): Int {
        return new.size
    }

    override fun areItemsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition].id == new[newPosition].id
    }

    override fun areContentsTheSame(oldPosition: Int, newPosition: Int): Boolean {
        return old[oldPosition] == new[newPosition]
    }

}