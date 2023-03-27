package com.syafei.gitconnect.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class GitUser(
    val login: String?,
    val id: Int?,
    val avatarUrl: String?,
    val followers: Int?,
    val following: Int?,
    val htmlUrl: String?,
    val name: String?,
    val company: String?,
    val blog: String?,
    val location: String?,
    val publicRepos: Int?,
    var isFavorite: Boolean?
) : Parcelable