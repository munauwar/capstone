package com.example.capstone.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.capstone.MoviesDetailFragment
import com.example.capstone.MoviesFragment
import com.example.capstone.ReviewsFragment

class TabAdapter(private val fm: FragmentManager?, private val totalTabs: Int) : FragmentPagerAdapter(fm!!) {

    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                MoviesDetailFragment()
            }
            1 -> {
                ReviewsFragment()
            }
            else -> MoviesFragment()
        }
    }

    override fun getCount(): Int {
        return totalTabs
    }
}