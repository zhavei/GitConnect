package com.syafei.gitconnect.core.data.source.localdatabase.modelentity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize


@Entity(tableName = "github_users")
@Parcelize
data class UsersEntity (
    @PrimaryKey
    @ColumnInfo(name= "id")
    val id: Int,

    @ColumnInfo(name= "login")
    val login: String?,

    @ColumnInfo(name= "avatar_url")
    val avatarUrl: String?,

    @ColumnInfo(name= "followers")
    val followers: Int?,

    @ColumnInfo(name= "following")
    val following: Int?,

    @ColumnInfo(name= "html_url")
    val htmlUrl: String?,

    @ColumnInfo(name= "name")
    val name: String?,

    @ColumnInfo(name= "company")
    val company: String?,

    @ColumnInfo(name= "blog")
    val blog: String?,

    @ColumnInfo(name= "location")
    val location: String?,

    @ColumnInfo(name= "public_repos")
    val publicRepos: Int?,

    @ColumnInfo(name = "user_favorite")
    var isFavorite: Boolean? = false
) : Parcelable
