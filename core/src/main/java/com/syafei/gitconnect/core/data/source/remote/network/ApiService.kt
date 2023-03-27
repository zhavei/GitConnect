package com.syafei.gitconnect.core.data.source.remote.network

import com.syafei.gitconnect.core.data.source.remote.modelresponse.ListResponse
import com.syafei.gitconnect.core.data.source.remote.modelresponse.UsersResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


interface ApiService {

    @GET("users")
    suspend fun getUsers(): List<UsersResponse>

    @GET("search/users")
    suspend fun searchUsersByKeyword(
        @Query("q") keyword: String
    ): Response<ListResponse<UsersResponse>>

    @GET("users/{username}")
    suspend fun getRemoteDetailsUser(
        @Path("username") username: String
    ): Response<UsersResponse>

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): Response<List<UsersResponse>>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): Response<List<UsersResponse>>
}