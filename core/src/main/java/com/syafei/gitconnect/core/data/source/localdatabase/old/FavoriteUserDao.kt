package com.syafei.gitconnect.core.data.source.localdatabase.old

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.syafei.gitconnect.core.data.source.localdatabase.old.UserEntity

/**
 * old
 */
@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addToFavorite(userEntity: UserEntity)

    @Query("SELECT * FROM user_favorite")
    fun getFavorite() : LiveData<List<UserEntity>>

    @Query("SELECT count(*) FROM user_favorite WHERE user_favorite.id = :id")
    fun checkCountUser(id: Int) : Int

    @Query("DELETE FROM user_favorite WHERE user_favorite.id = :id")
    fun deleteFromFavorite(id: Int) : Int

}