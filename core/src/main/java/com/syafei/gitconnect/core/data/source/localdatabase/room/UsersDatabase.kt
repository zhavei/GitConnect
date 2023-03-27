package com.syafei.gitconnect.core.data.source.localdatabase.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.syafei.gitconnect.core.data.source.localdatabase.modelentity.UsersEntity

@Database(entities = [UsersEntity::class], version = 1, exportSchema = false )
abstract class UsersDatabase : RoomDatabase() {

    abstract fun getDao() : UsersDatabaseDao
}