package com.syafei.gitconnect.core.data.source.remote.modelresponse

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize


@Parcelize
data class UsersResponse(
    @SerializedName("login")
    val login: String? = null,

    @SerializedName("id")
    val id: Int = 0,

    @SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @SerializedName("followers")
    val followers: Int? = null,

    @SerializedName("following")
    val following: Int? = null,

    @SerializedName("html_url")
    val htmlUrl: String? = null,

    @SerializedName("name")
    val name: String? = null,

    @SerializedName("company")
    val company: String? = null,

    @SerializedName("blog")
    val blog: String? = null,

    @SerializedName("location")
    val location: String? = null,

    @SerializedName("public_repos")
    val publicRepos: Int? = null,

    ) : Parcelable