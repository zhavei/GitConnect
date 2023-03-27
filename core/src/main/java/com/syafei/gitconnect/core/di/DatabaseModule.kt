package com.syafei.gitconnect.core.di

import android.content.Context
import androidx.room.Room
import com.syafei.gitconnect.core.data.source.localdatabase.room.UsersDatabase
import com.syafei.gitconnect.core.data.source.localdatabase.room.UsersDatabaseDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesDatabase(@ApplicationContext context: Context): UsersDatabase =
        Room.databaseBuilder(
            context.applicationContext,
            UsersDatabase::class.java,
            "git_connect_user_db"
        ).fallbackToDestructiveMigration().build()

    @Provides
    fun provideUsersDatabaseDao(database: UsersDatabase): UsersDatabaseDao = database.getDao()


}