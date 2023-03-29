package com.syafei.gitconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase

class FavoriteViewModel(useCase: GitConnectUseCase) : ViewModel() {

    val getFavorite = useCase.getFavoriteGithubUsers().asLiveData()

}