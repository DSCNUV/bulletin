package com.gdscnuv.bulletin.models

import java.util.*

data class Event(
    val id: Long = counter++,
    val name: String,
    val organizers: String,
    val url: String
) {
    companion object {
        private var counter = 0L
    }
    private val event = hashMapOf(
        "id" to id,
        "name" to name,
        organizers to organizers,
        "url" to url
    )
    fun getEvent():HashMap<String, out Any>{
        return event
    }
}

data class RegisteredEvent(
    val id:String,
    val title:String,
    val desc:String,
    val date:String,
    val url:String,
)
