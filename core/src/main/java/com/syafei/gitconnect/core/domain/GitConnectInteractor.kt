package com.syafei.gitconnect.core.domain

import com.syafei.gitconnect.core.data.resourcerepository.Resource
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.core.domain.repository.IGitConnectRepository
import com.syafei.gitconnect.core.domain.usecase.GitConnectUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GitConnectInteractor @Inject constructor(
    private val repository: IGitConnectRepository
) : GitConnectUseCase {

    override fun searchUsersByKeyword(input: String): Flow<Resource<List<GitUser>>> {
        return repository.searchUsersByKeyword(input)
    }

    override fun fetchUsers(username: String): Flow<Resource<List<GitUser>>> {
        return repository.fetchUsers(username)
    }

    override fun getDetailsUser(name: String): Flow<Resource<GitUser?>> {
        return repository.getDetailsUser(name)
    }

    override fun getFollowing(name: String): Flow<Resource<List<GitUser>>> {
        return repository.getFollowing(name)
    }

    override fun getFollowers(name: String): Flow<Resource<List<GitUser>>> {
        return repository.getFollowers(name)
    }

    override fun getFavoriteGithubUsers(): Flow<List<GitUser>> {
        return repository.getFavoriteGithubUsers()
    }

    override fun getThemePreference(): Flow<Boolean> {
        return repository.getThemePreference()
    }

    override suspend fun saveThemePreference(isDarkMode: Boolean) {
        return repository.saveThemePreference(isDarkMode)
    }

    override suspend fun setFavoriteGithubUsers(user: GitUser, isFavoriteState: Boolean) {
        return repository.updateFavoriteGithubUsers(user, isFavoriteState)
    }

}