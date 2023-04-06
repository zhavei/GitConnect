package com.syafei.gitconnect.ui.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.syafei.gitconnect.R
import com.syafei.gitconnect.databinding.ActivitySplashscreenBinding
import com.syafei.gitconnect.ui.main.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SplashScreenActivity : AppCompatActivity(R.layout.activity_splashscreen) {

    private val binding: ActivitySplashscreenBinding by viewBinding()

    companion object {
        const val TIME_WAITING: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()

        setDelayedMode()
        binding.tvSplash.text = getString(R.string.app_name)


    }

    private fun setDelayedMode() {

        Handler().postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, TIME_WAITING)
    }



}