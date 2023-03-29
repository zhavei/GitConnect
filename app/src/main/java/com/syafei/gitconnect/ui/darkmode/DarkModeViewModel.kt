package com.syafei.gitconnect.ui.darkmode

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.launch
import javax.inject.Inject

@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class DarkModeViewModel @Inject constructor(private val useCase: GitConnectUseCase) : ViewModel() {


    fun getThemePreference(): LiveData<Boolean> {
        return useCase.getThemePreference().asLiveData()
    }

    fun saveThemePreference(isDarkMode: Boolean) {
        viewModelScope.launch {
            useCase.saveThemePreference(isDarkMode)
        }
    }


}