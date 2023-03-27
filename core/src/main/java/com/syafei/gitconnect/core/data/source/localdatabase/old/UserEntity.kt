package com.syafei.gitconnect.core.data.source.localdatabase.old

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
import java.io.Serializable


@Entity(tableName = "user_favorite")
@Parcelize
data class UserEntity(
    val username: String,
    val avatarUrl: String,
    val htmlUrl: String,
    @PrimaryKey
    val id: Int,
) : Parcelable
