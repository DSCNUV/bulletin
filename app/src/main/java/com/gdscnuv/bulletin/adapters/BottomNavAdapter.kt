package com.gdscnuv.bulletin.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.gdscnuv.bulletin.fragments.EventsFragment
import com.gdscnuv.bulletin.fragments.ProfileFragment

class BottomNavAdapter(fm: FragmentManager):FragmentPagerAdapter(fm){
    override fun getCount(): Int {
        return 2;
    }

    override fun getItem(position: Int): Fragment {
        return when(position) {
            0 -> EventsFragment()
            1 -> ProfileFragment()
            else -> EventsFragment()
        }
    }
}
