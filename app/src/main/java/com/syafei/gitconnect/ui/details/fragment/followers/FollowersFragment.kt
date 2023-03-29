package com.syafei.gitconnect.ui.details.fragment.followers

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.syafei.gitconnect.core.data.resourcerepository.Resource
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.databinding.FragmentFollowBinding
import com.syafei.gitconnect.ui.details.UserDetailActivity
import com.syafei.gitconnect.ui.details.fragment.FollowsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@FlowPreview
@ExperimentalCoroutinesApi
@AndroidEntryPoint
class FollowersFragment : Fragment() {

    private var _binding: FragmentFollowBinding? = null
    private val binding get() = _binding!!

    private val viewModel: FollowersViewModel by viewModels()
    private lateinit var adapter: FollowsAdapter
    private lateinit var userName: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // get the name and id
        userName = requireActivity().intent.getStringExtra(UserDetailActivity.USER_NAME).toString()
        val followingNumber =
            requireActivity().intent.getIntExtra(UserDetailActivity.USER_FOLLOWING, 0)

        //setup recyclerview
        binding.apply {
            rvFollow.layoutManager = LinearLayoutManager(activity)
            rvFollow.setHasFixedSize(true)
            adapter = FollowsAdapter()
            rvFollow.adapter = adapter

            if (followingNumber == 0) {
                tvNotfoundFollow.visibility = View.GONE
            } else {
                tvNotfoundFollow.visibility = View.VISIBLE
            }
        }

        setupListFollowers()

        showProgressbar(true)
    }

    private fun setupListFollowers() {
        activity?.let {
            viewModel.getListFollowers(userName).observe(it) { response ->
                when (response) {
                    is Resource.Loading -> showProgressbar(true)
                    is Resource.Success -> {
                        showProgressbar(false)
                        if (response.data?.isNotEmpty() == true) {
                            notFound(false)
                            response.data?.let { data ->
                                setListData(data)
                            }
                        } else {
                            notFound(true)
                        }
                    }
                    is Resource.Error -> {
                        showProgressbar(false)
                        notFound(true)
                        Snackbar.make(
                            it.window.decorView.rootView,
                            "${response.message}",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }

        }
    }

    private fun setListData(data: List<GitUser>) {
        adapter.submitList(data)
        adapter.setOnItemClickCallBack(object : FollowsAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: GitUser) {
                Intent(requireContext(), UserDetailActivity::class.java).also { intent ->
                    intent.putExtra(UserDetailActivity.USER_NAME, data.login)
                    intent.putExtra(UserDetailActivity.USER_ID, data.id)
                    intent.putExtra(UserDetailActivity.USER_FOLLOWING, data.following)
                    intent.putExtra(UserDetailActivity.USER_FOLLOWERS, data.followers)
                    startActivity(intent)
                }
            }
        })
    }

    private fun notFound(state: Boolean) {
        if (state) {
            binding.tvNotfoundFollow.visibility = View.VISIBLE
        } else {
            binding.tvNotfoundFollow.visibility = View.GONE
        }
    }

    private fun showProgressbar(progres: Boolean) {
        if (progres) {
            binding.progressBarFollow.visibility = View.VISIBLE
        } else {
            binding.progressBarFollow.visibility = View.GONE
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}