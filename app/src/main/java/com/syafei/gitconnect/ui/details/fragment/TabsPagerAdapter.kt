package com.syafei.gitconnect.ui.details.fragment

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.syafei.gitconnect.ui.details.fragment.followers.FollowersFragment
import com.syafei.gitconnect.ui.details.fragment.following.FollowingFragment
import com.syafei.gitconnect.ui.details.fragment.profile.ProfileFragment
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

class TabsPagerAdapter(
    activity: AppCompatActivity
) : FragmentStateAdapter(activity) {

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = ProfileFragment()
            1 -> fragment = FollowingFragment()
            2 -> fragment = FollowersFragment()
        }
        return fragment as Fragment
    }

    override fun getItemCount(): Int {
        return 3
    }
}
