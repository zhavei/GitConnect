package com.syafei.gitconnect.core.data.source.remote.old

import com.syafei.gitconnect.core.data.source.remote.modelresponse.*
import com.syafei.gitconnect.core.data.source.remote.old.DetailUserResponse
import com.syafei.gitconnect.core.data.source.remote.old.SearchUserResponse
import com.syafei.gitconnect.core.data.source.remote.old.User
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query


const val API_KEY = "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"

/**
 * ini old
 */
interface Api {

    @GET("search/users")
    @Headers("Authorization: token $API_KEY")
    fun getSearchUsers(
        @Query("q") query: String
    ): Call<SearchUserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token $API_KEY")
    fun getUserDetail(
        @Path("username") username: String
    ): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_KEY")
    fun getFollowers(
        @Path("username") username: String
    ): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_KEY")
    fun getFollowing(
        @Path("username") username: String
    ): Call<ArrayList<User>>

}
