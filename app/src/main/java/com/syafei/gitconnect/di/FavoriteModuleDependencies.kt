package com.syafei.gitconnect.di

import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@EntryPoint
@InstallIn(SingletonComponent::class)
interface FavoriteModuleDependencies {

    fun gitConnectFavoriteUseCase(): GitConnectUseCase

}