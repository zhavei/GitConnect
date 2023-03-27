package com.syafei.gitconnect.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.syafei.gitconnect.core.data.source.localdatabase.old.FavoriteUserDao
import com.syafei.gitconnect.core.data.source.localdatabase.old.UserDatabaseOld
import com.syafei.gitconnect.core.data.source.localdatabase.old.UserEntity

class FavoriteViewModel(app: Application) : AndroidViewModel(app) {

    private var userDao: FavoriteUserDao?
    private var userDatabaseOld: UserDatabaseOld? = UserDatabaseOld.getDatabase(app)

    init {
        userDao = userDatabaseOld?.favoriteDao()
    }

    fun getUserFavorite(): LiveData<List<UserEntity>>? {
        return userDao?.getFavorite()
    }
}