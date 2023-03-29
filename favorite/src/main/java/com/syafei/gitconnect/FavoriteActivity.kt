package com.syafei.gitconnect

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.core.ui.MainAdapter
import com.syafei.gitconnect.databinding.ActivityFavoriteBinding
import com.syafei.gitconnect.di.FavoriteModuleDependencies
import com.syafei.gitconnect.ui.darkmode.DarkModeActivity
import com.syafei.gitconnect.ui.details.UserDetailActivity
import dagger.hilt.android.EntryPointAccessors
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private lateinit var binding: ActivityFavoriteBinding

    private val viewModel by viewModels<FavoriteViewModel> {
        factory
    }

    private lateinit var userFavoriteAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)



        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (supportActionBar != null) {
            (supportActionBar)?.title = "User Fovorite"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()

        viewModel.getFavorite.observe(this) {
            setList(it)
            listEmpty(it.isEmpty())
        }

        setupAppBar()

    }

    private fun setupRecyclerView() {
        userFavoriteAdapter = MainAdapter()

        binding.apply {
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.setHasFixedSize(true)
            rvUserFavorite.adapter = userFavoriteAdapter
        }

    }

    private fun setList(list: List<GitUser>) {

        userFavoriteAdapter.submitList(list)
        userFavoriteAdapter.setOnItemClickCallBack(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GitUser) {
                Intent(this@FavoriteActivity, UserDetailActivity::class.java).also {
                    it.putExtra(UserDetailActivity.USER_NAME, data.login)
                    it.putExtra(UserDetailActivity.USER_ID, data.id)
                    it.putExtra(UserDetailActivity.USER_FOLLOWING, data.following)
                    it.putExtra(UserDetailActivity.USER_FOLLOWERS, data.followers)
                    startActivity(it)
                }
            }

        })

    }

    private fun setupAppBar() {
        binding.appBarMainFavorite.toolbarMainFavorite.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.darkmode -> {
                    val intent = Intent(this, DarkModeActivity::class.java)
                    startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }

    private fun listEmpty(isEmpty: Boolean) {
        binding.notFoundFavorite.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}