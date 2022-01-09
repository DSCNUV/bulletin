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
}

data class RegisteredEvent(
    val id:String,
    val title:String,
    val desc:String,
    val date:String,
    val url:String,
)