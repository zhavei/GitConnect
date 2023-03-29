package com.syafei.gitconnect

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import javax.inject.Inject

class ViewModelFactory @Inject constructor(private val useCase: GitConnectUseCase) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) -> {
                FavoriteViewModel(useCase) as T
            }
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }
}