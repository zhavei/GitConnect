package com.syafei.gitconnect.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.syafei.gitconnect.R
import com.syafei.gitconnect.core.data.source.remote.old.User
import com.syafei.gitconnect.core.data.source.localdatabase.old.UserEntity
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.databinding.ActivityFavoriteBinding
import com.syafei.gitconnect.ui.details.UserDetailActivity
import com.syafei.gitconnect.core.ui.MainAdapter
import com.syafei.gitconnect.ui.darkmode.DarkModeActivity

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private val viewModel by viewModels<FavoriteViewModel>()
    private lateinit var userFavoriteAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        if (supportActionBar != null) {
            (supportActionBar)?.title = "User Fovorite"
        }
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setupRecyclerView()
        setupAppBar()

    }

    private fun setupRecyclerView() {
        userFavoriteAdapter = MainAdapter()
        userFavoriteAdapter.notifyDataSetChanged()

        userFavoriteAdapter.setOnItemClickCallBack(object : MainAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GitUser) {
                Intent(this@FavoriteActivity, UserDetailActivity::class.java).also { intent ->
                    intent.putExtra(UserDetailActivity.USER_ID, data.id)
                    intent.putExtra(UserDetailActivity.USER_NAME, data.login)
                    intent.putExtra(UserDetailActivity.USER_AVATAR_URL, data.avatarUrl)
                    intent.putExtra(UserDetailActivity.USER_HTML_URL, data.htmlUrl)
                    startActivity(intent)
                }

            }
        })

        binding.apply {
            rvUserFavorite.layoutManager = LinearLayoutManager(this@FavoriteActivity)
            rvUserFavorite.adapter = userFavoriteAdapter
            rvUserFavorite.setHasFixedSize(true)
        }

        //database
        viewModel.getUserFavorite()?.observe(this) {
            if (it != null) {
                val list = mappingList(it)
                //userFavoriteAdapter.setListUser(list)

                if (list.isEmpty()) {
                    binding.notFoundFavorite.visibility = View.VISIBLE
                } else {
                    binding.notFoundFavorite.visibility = View.GONE
                }
            }
        }
    }

    private fun mappingList(users: List<UserEntity>): ArrayList<User> {
        val listUser = ArrayList<User>()
        for (user in users) {
            val userMapped = User(
                user.username,
                user.avatarUrl,
                user.htmlUrl,
                user.id
            )
            listUser.add(userMapped)
        }
        return listUser
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
}