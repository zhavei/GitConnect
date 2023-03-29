package com.syafei.gitconnect.ui.details.fragment.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.snackbar.Snackbar
import com.syafei.gitconnect.R
import com.syafei.gitconnect.core.data.resourcerepository.Resource
import com.syafei.gitconnect.core.data.source.remote.old.DetailUserResponse
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.databinding.FragmentProfileBinding
import com.syafei.gitconnect.ui.details.UserDetailActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*


@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {

    private val binding: FragmentProfileBinding by viewBinding()
    private val profileDetailViewModel: ProfileDetailViewModel by viewModels()

    private var isFavorite: Boolean = false


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showProgressbar(true)


        activity?.let {
            val username = it.intent.getStringExtra(UserDetailActivity.USER_NAME)
            profileDetailViewModel.getDetailUser(username ?: "").observe(it) { itResource ->
                when (itResource) {
                    is Resource.Loading -> showProgressbar(true)
                    is Resource.Success -> {
                        showProgressbar(false)
                        itResource.data?.let { data -> setData(data) }
                    }
                    is Resource.Error -> {
                        showProgressbar(false)
                        Snackbar.make(
                            it.window.decorView.rootView,
                            "${itResource.message}}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                        notFound(true)
                    }
                }
            }

        }


    }

    private fun setData(data: GitUser) {

        binding.apply {
            tvDetailName.text = data.name
            tvDetailUsernames.text = data.htmlUrl
            tvDetailRepository.text = data.publicRepos.toString()
            tvDetailCompany.text = data.company
            tvDetailLocation.text = data.location
            tvDetailBlog.text = data.blog
            tvFollowersRepository.text = data.followers.toString()
            tvFollowingRepository.text = data.following.toString()

            Glide.with(this@ProfileFragment).load(data.avatarUrl).circleCrop().apply(
                RequestOptions.placeholderOf(
                    R.drawable.baseline_refresh_24
                ).error(R.drawable.baseline_broken_image_24)
            ).into(ivDetailItemProfile)
        }

        isFavorite = data.isFavorite ?: false

        setIsFavoriteState(isFavorite)

        binding.apply {
            toggleFavorite.setOnClickListener {
                profileDetailViewModel.addToFavorite(isFavorite, data)
                setIsFavoriteState(!isFavorite)
            }
        }

        // open detail user url in browser
        binding.apply {
            toggleWebsite.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.data = Uri.parse(data.htmlUrl)
                Log.i(TAG, "onViewCreated: check data $intent")
                startActivity(intent)
            }
        }

    }

    private fun setIsFavoriteState(favorite: Boolean) {
        binding.toggleFavorite.setImageDrawable(
            ContextCompat.getDrawable(
                requireActivity(),
                if (favorite) R.drawable.ic_favorite else R.drawable.ic_unfavorite
            )
        )

        val message = if (favorite) {
            "Added to favorites"
        } else {
            return
        }
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show()
    }

    private fun showProgressbar(progres: Boolean) {
        if (progres) {
            binding.progressBarDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarDetail.visibility = View.GONE
        }
    }

    private fun notFound(state: Boolean) {
        if (state) {
            binding.tvNotfoundDetail.visibility = View.VISIBLE
        } else {
            binding.tvNotfoundDetail.visibility = View.GONE
        }
    }

    companion object {
        private const val TAG = "Profile Fragment"
    }

}