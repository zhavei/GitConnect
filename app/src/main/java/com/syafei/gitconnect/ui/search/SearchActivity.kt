package com.syafei.gitconnect.ui.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.gitconnect.R
import com.syafei.gitconnect.core.data.resourcerepository.Resource
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.core.ui.MainAdapter
import com.syafei.gitconnect.databinding.ActivitySearchBinding
import com.syafei.gitconnect.ui.details.UserDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class SearchActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySearchBinding
    private val viewModel by viewModels<SearchViewModel>()
    private lateinit var userResultAdapter: MainAdapter
    private var queryInput: String = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = ""
        supportActionBar?.elevation = 0f

        setupRecyclerView()
        notFound(true)

        viewModel.result.observe(this) {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> {
                        notFound(false)
                        showProgressbar(true)
                    }
                    is Resource.Success -> {
                        showProgressbar(false)
                        if (it.data.isNullOrEmpty()) {
                            notFound(true)
                        } else {
                            notFound(false)
                            it.data?.let { listData ->
                                setList(listData)
                            }
                        }
                    }
                    is Resource.Error -> {
                        showProgressbar(false)
                        notFound(true)
                        Toast.makeText(this, "${it.message}", Toast.LENGTH_SHORT).show()
                    }
                    else -> {}
                }
            }
        }

    }

    private fun setList(list: List<GitUser>) {

        userResultAdapter.submitList(list)
        userResultAdapter.setOnItemClickCallBack(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GitUser) {
                Intent(this@SearchActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.USER_NAME, data.login)
                    it.putExtra(UserDetailActivity.USER_ID, data.id)
                    it.putExtra(UserDetailActivity.USER_FOLLOWING, data.following)
                    it.putExtra(UserDetailActivity.USER_FOLLOWERS, data.followers)
                    startActivity(it)
                }
            }

        })

    }

    private fun setupRecyclerView() {
        userResultAdapter = MainAdapter()
        binding.apply {
            rvSearchActivity.layoutManager = LinearLayoutManager(this@SearchActivity)
            rvSearchActivity.setHasFixedSize(true)
            rvSearchActivity.adapter = userResultAdapter
        }
    }

    private fun notFound(state: Boolean) {
        if (state) {
            binding.tvNotfound.visibility = View.VISIBLE
        } else {
            binding.tvNotfound.visibility = View.GONE
        }
    }

    private fun showProgressbar(progres: Boolean) {
        if (progres) {
            binding.viewLoading.visibility = View.VISIBLE
        } else {
            binding.viewLoading.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.search_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search_menu).actionView as SearchView

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(componentName))
            queryHint = resources.getString(R.string.search_hint)
            setIconifiedByDefault(false)
            onActionViewExpanded()

            setOnQueryTextListener(object : SearchView.OnQueryTextListener {

                override fun onQueryTextSubmit(query: String): Boolean {
                    queryInput = query
                    lifecycleScope.launch {
                        viewModel.query.value = query
                    }
                    clearFocus()
                    return true

                }

                override fun onQueryTextChange(newText: String): Boolean {
                    queryInput = newText
                    lifecycleScope.launch {
                        viewModel.query.value = newText
                    }
                    return false
                }
            })
        }
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}