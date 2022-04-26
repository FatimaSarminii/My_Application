package com.example.myapplication.ui.home

import android.content.res.Resources
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.myapplication.ui.fav.FavFragment
import com.example.myapplication.ui.posts.PostsFragment

class PagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0 -> {
                PostsFragment()
            }
            1 -> {
                FavFragment()
            }
            else -> {throw Resources.NotFoundException("Position Not Found")}

        }
    }

}