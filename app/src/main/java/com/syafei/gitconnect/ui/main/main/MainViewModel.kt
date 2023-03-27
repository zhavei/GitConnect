package com.syafei.gitconnect.ui.main.main

import android.util.Log
import androidx.lifecycle.*
import com.syafei.gitconnect.core.data.source.remote.old.RetrofitClient
import com.syafei.gitconnect.core.data.source.remote.old.SearchUserResponse
import com.syafei.gitconnect.core.data.source.remote.old.User
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class MainViewModel @Inject constructor(
    private val useCase: GitConnectUseCase
) : ViewModel() {

    private var username: MutableLiveData<String> = MutableLiveData()

    init {
        username.value = ""
    }

    val users = Transformations.switchMap(username) {
        useCase.fetchUsers(it).asLiveData()
    }


    companion object {
        const val TAG = "SEARCH_USER_VIEWMODEL"
    }
}