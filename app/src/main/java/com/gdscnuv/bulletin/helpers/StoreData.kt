package com.gdscnuv.bulletin.helpers

import android.util.Log
import com.gdscnuv.bulletin.models.Event
import com.gdscnuv.bulletin.models.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.Delayed


class StoreData() {
    val TAG = "STATUS"
    var db:FirebaseFirestore = FirebaseFirestore.getInstance()
    var x:Map<String, Any>? = null
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

    private fun getUid_():String{
        val firebaseUser:FirebaseAuth = FirebaseAuth.getInstance()
        val uid = firebaseUser.uid
        return uid.toString()
    }
    fun getUser_(){
//        var x:Map<String, Any>? = null
        val snap = db.collection("users").document(getUid_())
        snap.get().addOnSuccessListener {
            documentSnapShot ->
                val y = documentSnapShot.data
                getUser__(y)
//                Log.i(TAG, x.toString()!!)
        }
    }
    private fun getUser____():Map<String, Any>?{
        return x
    }
    fun getUser__(y:Map<String, Any>?){
        x = y
        val z = x
        Log.d(TAG, "x: ${x}, z: ${z}")
//        var x:Map<String, Any>? = null
//        val snap = db.collection("users").document(getUid_())
//        snap.get().addOnSuccessListener {
//                documentSnapShot ->
//            x = documentSnapShot.data!!
////                Log.i(TAG, x.toString()!!)
//        }
//        return x
    }
    fun getUser___():Map<String, Any>?{
        return x
    }
}