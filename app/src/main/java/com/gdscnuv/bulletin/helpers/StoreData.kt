package com.gdscnuv.bulletin.helpers

import android.util.Log
import com.gdscnuv.bulletin.models.User
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
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.toString()}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}