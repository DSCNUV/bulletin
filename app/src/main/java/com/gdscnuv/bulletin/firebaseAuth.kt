package com.gdscnuv.bulletin

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.gdscnuv.bulletin.R.string

import android.content.Intent
import android.provider.Settings.Global.getString
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import com.google.firebase.auth.FirebaseAuth.AuthStateListener
import com.google.firebase.auth.FirebaseUser


class firebaseAuth(private var activity: Activity, private val s:String) {
    private lateinit var firebaseUser:FirebaseUser
    private var loggedIn:Boolean = false
    //const
    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }
    fun enablegso(): GoogleSignInOptions{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(s)
            .requestEmail()
            .build()
        return gso
    }
    fun checkState():Boolean {
        var change:Boolean = false
        checkLoggedIn()
        var month = when(activity.localClassName){
            "MainActivity"-> {
                if(!loggedIn) {
                    return true
                }
                else false
            }
            "ProfileActivity" -> {
                if(loggedIn) {
                    return false
                }
                else true
            }
            else -> {
                return false
            }
        }
        return change
    }
    fun checkActivity():String{
        return activity.localClassName
    }
    fun checkLoggedIn():Boolean {
        var loggedIn: Boolean = false
        firebaseUser = FirebaseAuth.getInstance().currentUser!!
        if(firebaseUser != null){
            loggedIn = true
        }
        return loggedIn
    }
}