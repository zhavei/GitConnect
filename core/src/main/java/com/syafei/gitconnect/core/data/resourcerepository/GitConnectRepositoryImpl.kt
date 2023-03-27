package com.syafei.gitconnect.core.data.resourcerepository

import com.syafei.gitconnect.core.data.source.localdatabase.LocalDataSource
import com.syafei.gitconnect.core.data.source.networkboundresource.NetworkBoundResource
import com.syafei.gitconnect.core.data.source.remote.RemoteDataSource
import com.syafei.gitconnect.core.data.source.remote.modelresponse.UsersResponse
import com.syafei.gitconnect.core.data.source.remote.network.ApiResponse
import com.syafei.gitconnect.core.domain.model.GitUser
import com.syafei.gitconnect.core.domain.repository.IGitConnectRepository
import com.syafei.gitconnect.core.utils.DataMapper
import kotlinx.coroutines.flow.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GitConnectRepositoryImpl @Inject constructor(
    private val localDataSource: LocalDataSource,
    private val remoteDataSource: RemoteDataSource
) : IGitConnectRepository {
    override fun searchUsersByKeyword(input: String): Flow<Resource<List<GitUser>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.searchUsersByKeyword(input).first()) {
            is ApiResponse.Success -> {
                val result = DataMapper.mapResponsesListToEntities(response.data)
                val data = DataMapper.mapEntityListToDomain(result)
                emit(Resource.Success(data))
            }
            is ApiResponse.Empty -> {
                val result = DataMapper.mapResponsesListToEntities(listOf())
                val data = DataMapper.mapEntityListToDomain(result)
                emit(Resource.Success(data))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }

    override fun fetchUsers(username: String): Flow<Resource<List<GitUser>>> =
        object : NetworkBoundResource<List<GitUser>, List<UsersResponse>>() {
            override fun loadFromNetwork(data: List<UsersResponse>): Flow<List<GitUser>> {
                return DataMapper.mapResponsesToDomain(data)
            }

            override suspend fun createCall(): Flow<ApiResponse<List<UsersResponse>>> {
                return remoteDataSource.getUsers()
            }

        }.asFlow()

    override fun getDetailsUser(name: String): Flow<Resource<GitUser?>> = flow {
        emit(Resource.Loading())
        if (localDataSource.getUserDetailByUsername(name).first() == null) {
            when (val response = remoteDataSource.getRemoteDetailsUser(name).last()) {
                is ApiResponse.Success -> {
                    localDataSource.saveGithubUser(DataMapper.mapResponseToEntities(response.data))
                    emitAll(
                        localDataSource.getUserDetailByUsername(name).map {
                            Resource.Success(it?.let { detail ->
                                DataMapper.mapEntityToDomain(detail)
                            })
                        }
                    )
                }
                is ApiResponse.Empty -> {
                    emitAll(
                        localDataSource.getUserDetailByUsername(name).map {
                            Resource.Success(it?.let { detail ->
                                DataMapper.mapEntityToDomain(detail)
                            })
                        }
                    )
                }
                is ApiResponse.Error -> {
                    emit(Resource.Error(response.errorMessage))
                }
            }
        } else {
            emitAll(
                localDataSource.getUserDetailByUsername(name).map {
                    Resource.Success(it?.let { detail ->
                        DataMapper.mapEntityToDomain(detail)
                    })
                }
            )
        }
    }

    override fun getFollowing(name: String): Flow<Resource<List<GitUser>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getFollowing(name).first()) {
            is ApiResponse.Success -> {
                val result = DataMapper.mapResponsesListToEntities(response.data)
                val data = DataMapper.mapEntityListToDomain(result)
                emit(Resource.Success(data))
            }
            is ApiResponse.Empty -> {
                val result = DataMapper.mapResponsesListToEntities(listOf())
                val data = DataMapper.mapEntityListToDomain(result)
                emit(Resource.Success(data))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }

    override fun getFollowers(name: String): Flow<Resource<List<GitUser>>> = flow {
        emit(Resource.Loading())
        when (val response = remoteDataSource.getFollowers(name).first()) {
            is ApiResponse.Success -> {
                val result = DataMapper.mapResponsesListToEntities(response.data)
                val data = DataMapper.mapEntityListToDomain(result)
                emit(Resource.Success(data))
            }
            is ApiResponse.Empty -> {
                val result = DataMapper.mapResponsesListToEntities(listOf())
                val data = DataMapper.mapEntityListToDomain(result)
                emit(Resource.Success(data))
            }
            is ApiResponse.Error -> {
                emit(Resource.Error(response.errorMessage))
            }
        }
    }

    override fun getFavoriteGithubUsers(): Flow<List<GitUser>> {
        return localDataSource.getFavoriteGithubUsers().map {
            DataMapper.mapEntityListToDomain(it)
        }
    }

    override fun getThemePreference(): Flow<Boolean> {
        return localDataSource.getThemePreference()
    }

    override suspend fun saveThemePreference(isDarkMode: Boolean) {
        return localDataSource.saveThemePreference(isDarkMode)
    }

    override suspend fun updateFavoriteGithubUsers(user: GitUser, isFavoriteState: Boolean) {
        val userEntity = DataMapper.mapDomainToEntity(user)
        localDataSource.updateFavoriteGithubUsers(userEntity, isFavoriteState)
    }


}