package com.syafei.gitconnect.core.data.source.localdatabase.room

import androidx.room.*
import com.syafei.gitconnect.core.data.source.localdatabase.modelentity.UsersEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDatabaseDao {

    @Query("select * from github_users where user_favorite = 1")
    fun getFavoriteGithubUsers(): Flow<List<UsersEntity>>

    @Query("select * from github_users where login = :username")
    fun getGithubUserByUsername(username: String): Flow<UsersEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGithubUser(user: UsersEntity)

    @Update
    suspend fun updateFavoriteGithubUsers(user: UsersEntity)

}