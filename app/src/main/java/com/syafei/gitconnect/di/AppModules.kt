package com.syafei.gitconnect.di

import com.syafei.gitconnect.core.domain.GitConnectInteractor
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppModules {

    @Binds
    @Singleton
    abstract fun providesUseCase(gitConnectInteractor: GitConnectInteractor): GitConnectUseCase

}