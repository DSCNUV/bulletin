package com.gdscnuv.bulletin.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.gdscnuv.bulletin.MainActivity
import com.gdscnuv.bulletin.R
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

        val inst = FirebaseLogin(this@HomeActivity, "asd").checkState()
        if(inst) startActivity(Intent(this@HomeActivity, MainActivity::class.java))
    }

    override fun onResume() {
        super.onResume()
        var inst = false
        inst = FirebaseLogin(this@HomeActivity, "asd").checkState()
        Log.e("###########","edhar tak aaya "+inst.toString());
        if(inst) {
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
            Firebase.auth.signOut()
            startActivity(Intent(this@HomeActivity, MainActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}

