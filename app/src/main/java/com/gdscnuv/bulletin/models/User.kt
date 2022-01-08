package com.gdscnuv.bulletin.models

data class User(private val uid:String,
    private val name:String,
    private val email: String,
    private val photoURl: String,
    private val eventsAttended: Int,
    private val upcomingEventsRegistered: Int,
    private val born: Int)
                {
                    private val user = hashMapOf(
                        "uid" to uid,
                        "name" to name,
                        "email" to email,
                        "photoURL" to photoURl,
                        "eventsAttended" to eventsAttended,
                        "upcomingEventsRegistered" to upcomingEventsRegistered,
                        "born" to born
                    )
                    fun getUser():HashMap<String, out Any>{
                        return user
                    }
                }
