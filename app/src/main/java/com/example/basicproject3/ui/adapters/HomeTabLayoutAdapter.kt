package com.example.basicproject3.ui.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
//import com.bumptech.glide.manager.Lifecycle
import androidx.lifecycle.Lifecycle
import com.example.basicproject3.ui.fragments.PopularEventFragment
import com.example.basicproject3.ui.fragments.HappeningEventFragment

class HomeTabLayoutAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
         return if (position == 0) {
            PopularEventFragment()
        } else {
            HappeningEventFragment()
        }
    }

}