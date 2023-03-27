package com.syafei.gitconnect.core.data.source.remote.old
import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * ini old data
 */
data class DetailUserResponse (
    val login : String,
    val id: Int,
    @SerializedName("avatar_url")
    val avatarUrl : String,

    @SerializedName("followers_url")
    val followersUrl : String,

    @SerializedName("following_url")
    val followingUrl: String,

    @SerializedName("html_url")
    val htmlUrl: String,

    val name: String,
    val following: Int,
    val followers: Int,
    val company: String,
    val blog: String,
    val location: String,

    @SerializedName("public_repos")
    val publicRepos: Int
)

