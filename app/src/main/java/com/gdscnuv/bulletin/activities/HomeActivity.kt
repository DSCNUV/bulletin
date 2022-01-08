package com.gdscnuv.bulletin.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.gdscnuv.bulletin.R
import com.gdscnuv.bulletin.adapters.BottomNavAdapter
import com.gigamole.navigationtabstrip.NavigationTabStrip


class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setBottomNav()
    }

    private  fun setBottomNav(){
        var viewPager: ViewPager = findViewById(R.id.view_pager)
        var tabLayout: NavigationTabStrip = findViewById(R.id.nts_center)

        viewPager.adapter = BottomNavAdapter(supportFragmentManager)
        tabLayout.setViewPager(viewPager);
    }
}

