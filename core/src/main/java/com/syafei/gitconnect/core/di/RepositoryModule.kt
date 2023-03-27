package com.syafei.gitconnect.core.di

import com.syafei.gitconnect.core.data.resourcerepository.GitConnectRepositoryImpl
import com.syafei.gitconnect.core.domain.repository.IGitConnectRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module(includes = [NetworkModule::class, DatabaseModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideRepository(userRepositoryImpl: GitConnectRepositoryImpl) : IGitConnectRepository
}