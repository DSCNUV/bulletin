package com.gdscnuv.bulletin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

import android.content.Intent
import android.util.Log
import com.gdscnuv.bulletin.databinding.ActivityMainBinding
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var googleSignInClient: GoogleSignInClient
    lateinit var firebaseAuth: FirebaseAuth

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
//        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//            .requestIdToken(getString(R.string.default_web_client_id))
//            .requestEmail()
//            .build()
        val inst = firebaseAuth(this@MainActivity, s)
        val gso = inst.enablegso()
        googleSignInClient = GoogleSignIn.getClient(this, gso)

        // init firebase auth
        firebaseAuth = FirebaseAuth.getInstance()
        checkUser()


        // Google SignIn button
        binding.signInButton.setOnClickListener{
            Log.d(TAG, "Begin Google Sign In")
            signIn()
        }
//        binding.googleSignInBtn.setOnclickListener {
//            Log.d(TAG, "Begin Google Sign In")
//            signIn()
//        }
//
//        val signInButton = findViewById<SignInButton>(R.id.sign_in_button)
//        signInButton.setSize(SignInButton.SIZE_STANDARD)
//        signInButton.setOnClickListener(this)

    }

    private fun signIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun checkUser() {
        val firebaseUser = firebaseAuth.currentUser
        if(firebaseUser != null) {
            startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            finish()
        }
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
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user = firebaseAuth.currentUser
//                    updateUI(user)
                } else {
                    // If sign in fails, display a message to the user.
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
//                    updateUI(null)
                }
                startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Firebase auth failed! ${e.message}")
            }
    }

}