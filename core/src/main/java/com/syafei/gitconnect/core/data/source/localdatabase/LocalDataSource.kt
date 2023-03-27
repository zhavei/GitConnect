package com.syafei.gitconnect.core.data.source.localdatabase

import com.syafei.gitconnect.core.data.source.localdatabase.modelentity.UsersEntity
import com.syafei.gitconnect.core.data.source.localdatabase.preference.UserThemePreferences
import com.syafei.gitconnect.core.data.source.localdatabase.room.UsersDatabaseDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor(
    private val usersDatabaseDao: UsersDatabaseDao,
    private val userPref: UserThemePreferences
) {

    fun getFavoriteGithubUsers(): Flow<List<UsersEntity>> =
        usersDatabaseDao.getFavoriteGithubUsers()

    fun getUserDetailByUsername(name: String): Flow<UsersEntity?> =
        usersDatabaseDao.getGithubUserByUsername(name)

    suspend fun saveGithubUser(user: UsersEntity) = usersDatabaseDao.saveGithubUser(user)


    suspend fun updateFavoriteGithubUsers(user: UsersEntity, isFavorite: Boolean) {
        user.isFavorite = isFavorite
        usersDatabaseDao.updateFavoriteGithubUsers(user)

    }

    suspend fun saveThemePreference(isDarkMode: Boolean) = userPref.saveThemePreference(isDarkMode)

    fun getThemePreference(): Flow<Boolean> = userPref.getThemePreference()


}