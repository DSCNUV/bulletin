package com.gdscnuv.bulletin.models

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