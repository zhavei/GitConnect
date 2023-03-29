package com.syafei.gitconnect

import android.content.Context
import com.syafei.gitconnect.di.FavoriteModuleDependencies
import dagger.BindsInstance
import dagger.Component
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview

@Component(dependencies = [FavoriteModuleDependencies::class])
interface FavoriteComponent {

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    fun inject(activity: FavoriteActivity)

    @Component.Builder
    interface Builder {
        fun context(@BindsInstance context: Context): Builder
        fun appDependencies(favoriteModuleDependencies: FavoriteModuleDependencies): Builder
        fun build(): FavoriteComponent
    }
}