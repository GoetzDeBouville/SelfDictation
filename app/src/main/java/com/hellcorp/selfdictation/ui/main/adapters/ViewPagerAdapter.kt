package com.hellcorp.selfdictation.ui.main.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.hellcorp.selfdictation.ui.usersetlist.UsersSetFragment
import com.hellcorp.selfdictation.ui.setlist.SetListFragment

class ViewPagerAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) :
    FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return NUM_TABS
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SetListFragment()
            else -> UsersSetFragment()
        }
    }

    companion object {
        private const val NUM_TABS = 2
    }
}