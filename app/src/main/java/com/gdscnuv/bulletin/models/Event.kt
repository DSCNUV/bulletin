package com.gdscnuv.bulletin.models

import java.util.*

data class Event(
    val id: Any= counter++,
    val name: String,
    val desc:String,
    val organizers: String,
    val date:String,
    val url: String
) {
    companion object {
        private var counter = 0L
    }
    private val event = hashMapOf(
        "id" to id,
        "name" to name,
        "organizers" to organizers,
        "date" to date,
        "url" to url
    )
    fun getEvent():HashMap<String, out Any>{
        return event
    }
}
