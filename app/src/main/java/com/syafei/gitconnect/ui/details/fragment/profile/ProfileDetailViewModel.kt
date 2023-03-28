package com.syafei.gitconnect.ui.details.fragment.profile

import androidx.lifecycle.*
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileDetailViewModel @Inject constructor(private val useCase: GitConnectUseCase) :
    ViewModel() {


    fun getDetailUser(username: String) = useCase.getDetailsUser(username).asLiveData()

    fun addToFavorite(userFavorite: Boolean, user: GitUser) {
        viewModelScope.launch {
            useCase.setFavoriteGithubUsers(user, !userFavorite)
        }
    }

    companion object {
        const val TAG = "This_Details_View_Model"
    }

}