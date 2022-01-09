package com.gdscnuv.bulletin.helpers

import android.app.Activity
import android.content.Intent
import android.util.Log
import com.gdscnuv.bulletin.MainActivity
import com.gdscnuv.bulletin.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser

import com.google.firebase.auth.GoogleAuthProvider


class FirebaseLogin(private var activity: Activity, private val s:String) {
//    private var firebaseUser:FirebaseUser
    private var loggedIn:Boolean = false
    private lateinit var googleSignInClient: GoogleSignInClient
    private lateinit var firebaseAuth:FirebaseAuth
    //const
    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }
    fun enablegso(): GoogleSignInClient{
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(s)
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(activity, gso)
        return googleSignInClient
    }
    fun firebaseInstance():FirebaseAuth{
        val firebaseAuth_ = FirebaseAuth.getInstance()
        return firebaseAuth_
    }
    fun checkState():Boolean {
        var change:Boolean = false
        checkLoggedIn()
        var month = when(activity.localClassName){
            "MainActivity"-> {
                if(!loggedIn) {
                    return false
                }
                else true
            }
            "HomeActivity" -> {
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
        if(FirebaseAuth.getInstance().currentUser != null){
            loggedIn = true
        }
        return loggedIn
    }

    fun firebaseAuthWithGoogle(idToken: String, activity: Activity, activity2: Class<*>):Boolean {
        var go = true
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth = firebaseInstance()
        Log.e(
            TAG, firebaseAuth.signInWithCredential(credential)
            .isSuccessful.toString())
       firebaseAuth.signInWithCredential(credential)
//            .isSuccessful

            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    userStoreSetup()
                    go = true
                        mainToProf(activity, activity2)
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
                }

            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Firebase auth failed! ${e.message}")
            }
            return go
    }

    private fun userStoreSetup(){
        val photoURL = "https://static.vecteezy.com/system/resources/previews/002/746/072/non_2x/political-elite-flat-color-icon-public-demonstration-speaker-from-government-high-status-of-influence-person-avatar-cartoon-style-clip-art-for-mobile-app-isolated-rgb-illustration-vector.jpg"
        var u:FirebaseUser? = firebaseAuth.currentUser
        Log.d("USER DETAILS: ", u.toString())
        var user = User(u?.uid.toString() , u?.displayName.toString(), u?.email!! ,photoURL, 10, 5, 2001).getUser()
        StoreData().saveNew(user)
        Log.e(TAG, "USER STORED SUCCESSFULLY")
    }

    private fun mainToProf(activity: Activity, activity2: Class<*>){
        val intent = Intent(activity, activity2)
        activity.startActivity(intent)
    }
}