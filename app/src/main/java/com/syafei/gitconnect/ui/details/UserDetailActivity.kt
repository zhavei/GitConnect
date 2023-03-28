package com.syafei.gitconnect.ui.details

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.syafei.gitconnect.R
import com.syafei.gitconnect.databinding.ActivityDetailUserBinding
import com.syafei.gitconnect.ui.details.fragment.TabsPagerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class UserDetailActivity : AppCompatActivity(R.layout.activity_detail_user) {

    private val binding: ActivityDetailUserBinding by viewBinding()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        if (supportActionBar != null) {
            (supportActionBar)?.title = "User Details"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)

        setupTabsViewPager()

    }

    private fun setupTabsViewPager() {

        binding.apply {
            //custom color tab
            tabLayout.setSelectedTabIndicatorColor(Color.RED)
            tabLayout.setBackgroundColor(
                ContextCompat.getColor(
                    this@UserDetailActivity,
                    R.color.primary_bold
                )
            )
            tabLayout.tabTextColors =
                ContextCompat.getColorStateList(
                    this@UserDetailActivity,
                    android.R.color.white
                )

            tabLayout.tabMode = TabLayout.MODE_FIXED
            binding.viewPagerDetails.isUserInputEnabled = true    // Enable Swipe
        }

        binding.apply {
            val tabsAdapter = TabsPagerAdapter(
                this@UserDetailActivity,
            )
            viewPagerDetails.adapter = tabsAdapter

            TabLayoutMediator(tabLayout, viewPagerDetails) { tab, position ->
                when (position) {
                    0 -> {
                        tab.text = "Profile"
                        tab.setIcon(R.drawable.ic_baseline_person_24)
                    }
                    1 -> {
                        tab.text = "Following"
                        tab.setIcon(R.drawable.ic_baseline_person_add_24)
                    }
                    2 -> {
                        tab.text = "Followers"
                        tab.setIcon(R.drawable.ic_baseline_3p_24)
                    }
                }
                tab.icon?.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                    Color.WHITE, BlendModeCompat.SRC_ATOP
                )
            }.attach()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(
            androidx.appcompat.R.anim.abc_fade_in,
            androidx.appcompat.R.anim.abc_fade_out
        )
    }

    companion object {
        const val USER_NAME = "_username"
        const val USER_FOLLOWERS = "_followers"
        const val USER_FOLLOWING = "_following"
        const val USER_ID = "_id"
        const val USER_AVATAR_URL = "_avatar_url"
        const val USER_HTML_URL = "_html_url"
    }

}