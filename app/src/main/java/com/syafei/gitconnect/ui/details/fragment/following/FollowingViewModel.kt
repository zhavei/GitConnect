package com.syafei.gitconnect.ui.details.fragment.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafei.gitconnect.core.data.source.remote.old.RetrofitClient
import com.syafei.gitconnect.core.data.source.remote.old.User
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class FollowingViewModel @Inject constructor(private val useCase: GitConnectUseCase) : ViewModel() {

    fun getFollowingList(username: String) =
        useCase.getFollowing(username).asLiveData()


    companion object {
        const val TAG = "this Following View Model"
    }

}