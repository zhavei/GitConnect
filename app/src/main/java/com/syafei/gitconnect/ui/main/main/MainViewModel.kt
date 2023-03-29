package com.syafei.gitconnect.ui.main.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
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