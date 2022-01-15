package com.gdscnuv.bulletin.activities

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.gdscnuv.bulletin.MainActivity
import com.gdscnuv.bulletin.R
import com.gdscnuv.bulletin.RegisterEventActivity
import com.gdscnuv.bulletin.adapters.BottomNavAdapter
import com.gdscnuv.bulletin.helpers.FirebaseLogin
import com.gigamole.navigationtabstrip.NavigationTabStrip
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setBottomNav()
        val toolbar:androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar)
        toolbar.setTitleTextColor(Color.WHITE)
        val inst = FirebaseLogin(this@HomeActivity, "asd").checkState()
        if(inst) startActivity(Intent(this@HomeActivity, MainActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
//        var inst = false
        val inst = FirebaseLogin(this@HomeActivity, "asd")
        var checkLogin = inst.checkLoggedIn()
        Log.v("HOME STATE: ", checkLogin.toString())
        var state = inst.checkState()
        if(!checkLogin) {
            Log.d("CHANGED", "THE STATE WILL CHANGE NOW!")
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        }
        Log.d("THIS HAS BEEN", "RESUMED")
    }

    private  fun setBottomNav(){
        var viewPager: ViewPager = findViewById(R.id.view_pager)
        var tabLayout: NavigationTabStrip = findViewById(R.id.nts_center)

        viewPager.adapter = BottomNavAdapter(supportFragmentManager)
        tabLayout.setViewPager(viewPager);
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.option_menu, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.title == "Logout"){
            Log.v("HOME STATE: ", "Loggin Out")
            Firebase.auth.signOut()
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        }
        else if(item.title == "Register"){
            startActivity(Intent(this@HomeActivity, RegisterEventActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}

