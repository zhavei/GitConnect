package com.syafei.gitconnect.ui.details.fragment.following

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(private val useCase: GitConnectUseCase) : ViewModel() {

    fun getFollowingList(username: String) =
        useCase.getFollowing(username).asLiveData()


    companion object {
        const val TAG = "this Following View Model"
    }

}