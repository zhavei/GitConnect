package com.syafei.gitconnect.ui.main.main

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.gitconnect.R
import com.syafei.gitconnect.core.data.resourcerepository.Resource
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.core.ui.MainAdapter
import com.syafei.gitconnect.databinding.ActivityMainBinding
import com.syafei.gitconnect.ui.darkmode.DarkModeActivity
import com.syafei.gitconnect.ui.details.UserDetailActivity
import com.syafei.gitconnect.ui.search.SearchActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>()
    private lateinit var userResultAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupRecyclerView()
        setupAppBar()
        refreshApp()
        notFound(true)

        viewModel.users.observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {
                    notFound(false)
                    showProgressbar(true)
                }
                is Resource.Success -> {
                    showProgressbar(false)
                    if (resource.data.isNullOrEmpty()) {
                        notFound(true)
                    } else {
                        notFound(false)
                        resource.data?.let {
                            setList(it)
                        }
                    }
                }
                is Resource.Error -> {
                    notFound(true)
                    showProgressbar(false)
                    Toast.makeText(this, resource.message, Toast.LENGTH_SHORT).show()
                }
                else -> {}
            }
        }


        binding.apply {
            searchFab.setOnClickListener {
                val intent = Intent(this@MainActivity, SearchActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupRecyclerView() {
        userResultAdapter = MainAdapter()

        binding.apply {
            rvMainActivity.layoutManager = LinearLayoutManager(this@MainActivity)
            rvMainActivity.setHasFixedSize(true)
            rvMainActivity.adapter = userResultAdapter
        }
    }

    private fun setList(list: List<GitUser>) {

        userResultAdapter.submitList(list)
        userResultAdapter.setOnItemClickCallBack(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GitUser) {
                Intent(this@MainActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.USER_NAME, data.login)
                    it.putExtra(UserDetailActivity.USER_ID, data.id)
                    it.putExtra(UserDetailActivity.USER_FOLLOWING, data.following)
                    it.putExtra(UserDetailActivity.USER_FOLLOWERS, data.followers)
                    startActivity(it)
                }
            }

        })

    }

    //currently fixing this swipe function, if connection failed not fetching to a network when swiping
    private fun refreshApp() {
        binding.apply {
            swipeToRefresh.setOnRefreshListener {
                viewModel.fetchUsers()
                swipeToRefresh.isRefreshing = false
            }
        }
    }

    private fun showProgressbar(progres: Boolean) {
        if (progres) {
            binding.viewEmpty.visibility = View.VISIBLE
        } else {
            binding.viewEmpty.visibility = View.GONE
        }
    }

    private fun notFound(state: Boolean) {
        if (state) {
            binding.tvNotfound.visibility = View.VISIBLE
            binding.viewEmpty.visibility = View.VISIBLE
        } else {
            binding.tvNotfound.visibility = View.GONE
            binding.viewEmpty.visibility = View.GONE
        }
    }


    private fun setupAppBar() {
        binding.appBarMain.toolbarMain.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {

                R.id.favorite -> {
                    Log.d("Main Activity", "installFavoriteModule: Success ")
                    val uri = Uri.parse("git-connect://favorite")
                    startActivity(Intent(Intent.ACTION_VIEW, uri))
                    true
                }

                R.id.darkmode -> {
                    val intentDark = Intent(this, DarkModeActivity::class.java)
                    startActivity(intentDark)
                    true
                }
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        AlertDialog.Builder(this)
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setTitle("Github App")
            .setMessage("Wanna Leave?")
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which -> finish() })
            .setNegativeButton("No", null)
            .show()
    }

}