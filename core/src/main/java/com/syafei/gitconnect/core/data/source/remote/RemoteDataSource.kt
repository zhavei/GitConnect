package com.syafei.gitconnect.core.data.source.remote

import android.util.Log
import com.syafei.gitconnect.core.data.source.remote.modelresponse.UsersResponse
import com.syafei.gitconnect.core.data.source.remote.network.ApiResponse
import com.syafei.gitconnect.core.data.source.remote.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class RemoteDataSource @Inject constructor(private val apiService: ApiService) {

    companion object {
        private const val TAG = "RemoteDataSource"
    }

    suspend fun getUsers(): Flow<ApiResponse<List<UsersResponse>>> {
        //get data from remote api
        return flow {
            try {
                val response = apiService.getUsers()
                if (response.isNotEmpty()) {
                    emit(ApiResponse.Success(response))
                    Log.i(TAG, "Not Empty")
                } else {
                    emit(ApiResponse.Empty)
                    Log.e(TAG, "Empty")
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getRemoteDetailsUser(username: String): Flow<ApiResponse<UsersResponse>> {
        return flow {
            try {
                val response = apiService.getRemoteDetailsUser(username)
                val dataArray = response.body()
                if (dataArray != null) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getUserDetails: $e")
            }
        }.flowOn(Dispatchers.Default)
    }

    suspend fun searchUsersByKeyword(input: String): Flow<ApiResponse<List<UsersResponse>>> {
        return flow {
            try {
                val response = apiService.searchUsersByKeyword(input)
                val dataArray = response.body()?.listItems
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "searchUsersByKeyword: $e")
            }
        }.flowOn(Dispatchers.IO)
    }

    suspend fun getFollowers(username: String): Flow<ApiResponse<List<UsersResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowers(username)
                val dataArray = response.body()
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getFollowers: $e")
            }
        }.flowOn(Dispatchers.Default)
    }

    suspend fun getFollowing(username: String): Flow<ApiResponse<List<UsersResponse>>> {
        return flow {
            try {
                val response = apiService.getFollowing(username)
                val dataArray = response.body()
                if (dataArray?.isNotEmpty() == true) {
                    emit(ApiResponse.Success(dataArray))
                } else {
                    emit(ApiResponse.Empty)
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
                Log.e(TAG, "getFollowing: $e")
            }
        }.flowOn(Dispatchers.Default)
    }
}
