package com.syafei.gitconnect.core.data.source.localdatabase.old

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [UserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class UserDatabaseOld : RoomDatabase() {
    abstract fun favoriteDao(): FavoriteUserDao

    companion object {
        private var INSTANCE: UserDatabaseOld? = null

        fun getDatabase(context: Context): UserDatabaseOld? {
            if (INSTANCE == null) {
                synchronized(UserDatabaseOld::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        UserDatabaseOld::class.java,
                        "user_database"
                    ).build()
                }
            }
            return INSTANCE
        }
    }


}