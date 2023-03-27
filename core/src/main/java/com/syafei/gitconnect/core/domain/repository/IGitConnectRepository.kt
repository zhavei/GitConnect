package com.syafei.gitconnect.core.domain.repository

import com.syafei.gitconnect.core.data.resourcerepository.Resource
import com.syafei.gitconnect.core.domain.model.GitUser
import kotlinx.coroutines.flow.Flow

interface IGitConnectRepository {

    fun fetchUsers(username: String): Flow<Resource<List<GitUser>>>
    fun getDetailsUser(name: String): Flow<Resource<GitUser?>>
    fun searchUsersByKeyword(input: String): Flow<Resource<List<GitUser>>>
    fun getFollowing(name: String): Flow<Resource<List<GitUser>>>
    fun getFollowers(name: String): Flow<Resource<List<GitUser>>>
    fun getFavoriteGithubUsers(): Flow<List<GitUser>>
    fun getThemePreference(): Flow<Boolean>
    suspend fun saveThemePreference(isDarkMode: Boolean)
    suspend fun updateFavoriteGithubUsers(user: GitUser, isFavoriteState: Boolean)

}