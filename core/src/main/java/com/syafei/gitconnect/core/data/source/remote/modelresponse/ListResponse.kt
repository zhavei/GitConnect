package com.syafei.gitconnect.core.data.source.remote.modelresponse

import com.google.gson.annotations.SerializedName

data class ListResponse<T>(
    @field:SerializedName("items")
    val listItems: List<T> = listOf(),
)