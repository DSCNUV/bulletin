package com.gdscnuv.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.gdscnuv.bulletin.activities.HomeActivity
import com.gdscnuv.bulletin.databinding.ActivityMainBinding
import com.gdscnuv.bulletin.helpers.FirebaseLogin
import com.gdscnuv.bulletin.helpers.StoreData
import com.gdscnuv.bulletin.models.User
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var firebaseAuth: FirebaseAuth
    lateinit var inst: FirebaseLogin

    //const
    private companion object{
        private const val RC_SIGN_IN = 100
        private const val TAG = "GOOGLE_SIGN_IN_TAG"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // configure google sign in
        val s = getString(R.string.default_web_client_id)
        inst = FirebaseLogin(this@MainActivity, s)
        googleSignInClient = inst.enablegso()

        firebaseAuth = inst.firebaseInstance()
        if(inst.checkLoggedIn()){
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }

        // Google SignIn button
        binding.signInButton.setOnClickListener{
            Log.d(TAG, "Begin Google Sign In")
            signIn()
        }
    }

    override fun onResume() {
        super.onResume()
        var checkLogin = inst.checkLoggedIn()
        Log.v("Main Activity STATE: ", checkLogin.toString())
        var state = inst.checkState()
        if(checkLogin) {
            Log.d("CHANGED", "THE STATE WILL CHANGE NOW!")
            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
        }
        Log.d("THIS HAS BEEN", "RESUMED")
    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        Log.d("THIS SHIT IS SIGNING IN", "YESSS")
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                var go:Boolean = inst.firebaseAuthWithGoogle(account.idToken!!, this@MainActivity, HomeActivity::class.java )
                Log.d(TAG, "Do we intent ${go.toString()}")
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }
}