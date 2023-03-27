package com.syafei.gitconnect.core.data.source.remote.old

import com.google.gson.annotations.SerializedName

data class SearchUserResponse(

    @SerializedName("total_count")
    val totalUserFound: Int,
    @SerializedName("items")
    val listSearchResult: ArrayList<User>
)