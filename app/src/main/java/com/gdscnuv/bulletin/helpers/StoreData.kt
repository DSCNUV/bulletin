package com.gdscnuv.bulletin.helpers

import android.util.Log
import com.gdscnuv.bulletin.models.Event
import com.gdscnuv.bulletin.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase


class StoreData() {
    val TAG = "STATUS"
    var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    companion object {
        private lateinit var user_:HashMap<String, out Any>
    }
    fun saveNew(user:HashMap<String, out Any>){
        user_ = user
        db.collection("users")
            .document(user.get("uid").toString())
            .set(user)
            .addOnSuccessListener { documentReference ->
                Log.i("SAVE: ", "Document was saved!")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }

    fun saveEvents(event: Event){
        val firebaseUser:FirebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseUser.uid
        val addEvent = db.collection("users")
            .document(uid.toString())
            .collection("savedEvents")
            .document(event.id.toString())
            .set(event.getEvent()).isSuccessful
        if(addEvent){
            Log.i(TAG, "SUCCESSFULLY SAVED!")
        }else{
            Log.i(TAG, "FAILED SAVED!")
        }
    }
}