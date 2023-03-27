package com.syafei.gitconnect.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import javax.inject.Inject


@FlowPreview
@ExperimentalCoroutinesApi
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val useCase: GitConnectUseCase
) : ViewModel() {


    val query = MutableStateFlow("")

    private fun search(input: String) = useCase.searchUsersByKeyword(input)

    val result = query
        .debounce(600)
        .filter {
            it.trim().isNotEmpty()
        }
        .distinctUntilChanged()
        .flatMapLatest { input ->
            search(input)
        }
        .asLiveData()
}