package com.gdscnuv.bulletin

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class ProfileActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)
        val signOutButton: Button = findViewById(R.id.signOut)
        signOutButton.setOnClickListener(View.OnClickListener {
            Firebase.auth.signOut()
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        })
        val inst = firebaseAuth(this@ProfileActivity, "asd").checkState()
        if(inst) startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        Log.d("WE NEED ACTIVITY: ", inst.toString())
    }

    override fun onResume() {
        super.onResume()
        var inst = false
        inst = firebaseAuth(this@ProfileActivity, "asd").checkState()
        if(inst) {
            Log.d("CHANGED", "THE STATE WILL CHANGE NOW!")
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        }
        Log.d("THIS HAS BEEN", "RESUMED")
    }

//    private fun checkUser() {
//        val firebaseUser = MainActivity().firebaseAuth.currentUser
//        if(firebaseUser == null) {
//            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
//            finish()
//        }
//        TODO("Not yet implemented")
//    }
}