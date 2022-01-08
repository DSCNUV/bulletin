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
}