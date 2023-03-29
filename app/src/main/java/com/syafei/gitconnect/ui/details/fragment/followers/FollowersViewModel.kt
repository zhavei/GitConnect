package com.syafei.gitconnect.ui.details.fragment.followers

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowersViewModel @Inject constructor(private val useCase: GitConnectUseCase) : ViewModel() {

    fun getListFollowers(username: String) =
        useCase.getFollowers(username).asLiveData()


    companion object {
        const val TAG = "this Followers View Model"
    }
}




