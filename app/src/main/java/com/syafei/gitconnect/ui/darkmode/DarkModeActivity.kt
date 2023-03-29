package com.syafei.gitconnect.ui.darkmode

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.syafei.gitconnect.R
import com.syafei.gitconnect.databinding.ActivityDarkModeBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class DarkModeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDarkModeBinding

    private val viewModel: DarkModeViewModel by viewModels()

    private var isDarkThemeEnabled: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDarkModeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAppBar()

        supportActionBar?.hide()

        //region Dark Mode
        observeUIPreferences()
        initViewSwitch()
        //endregion
    }

    //region Dark Mode
    private fun observeUIPreferences() {
        viewModel.getThemePreference().observe(this) { isDarkThemeEnabled ->
            val nightMode =
                if (isDarkThemeEnabled) AppCompatDelegate.MODE_NIGHT_YES else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(nightMode)
            binding.switchMain.isChecked = isDarkThemeEnabled
        }
    }

    private fun initViewSwitch() {
        binding.switchMain.setOnCheckedChangeListener { _, isChecked ->
            val newThemePreference = !isDarkThemeEnabled
            lifecycleScope.launch {
                when (isChecked) {
                    true -> viewModel.saveThemePreference(newThemePreference)
                    false -> viewModel.saveThemePreference(!newThemePreference)
                }
            }
        }
    }
    //endregion
    private fun setupAppBar() {
        binding.appBarMainDark.toolbarMainDark.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.favorite -> {
                    val uri = Uri.parse("git-connect://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }
                else -> false
            }
        }
    }

}