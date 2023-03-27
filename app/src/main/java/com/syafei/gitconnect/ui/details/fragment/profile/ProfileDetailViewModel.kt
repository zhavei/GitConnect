package com.syafei.gitconnect.ui.details.fragment.profile

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.syafei.gitconnect.core.data.source.localdatabase.old.FavoriteUserDao
import com.syafei.gitconnect.core.data.source.localdatabase.old.UserDatabaseOld
import com.syafei.gitconnect.core.data.source.localdatabase.old.UserEntity
import com.syafei.gitconnect.core.data.source.remote.old.DetailUserResponse
import com.syafei.gitconnect.core.data.source.remote.old.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileDetailViewModel(apps: Application) : AndroidViewModel(apps) {

    val userViewModel = MutableLiveData<DetailUserResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _onFailure = MutableLiveData<Boolean>()
    val onFailure: LiveData<Boolean> = _onFailure

    private var userDao: FavoriteUserDao?
    private var userDb: UserDatabaseOld? = UserDatabaseOld.getDatabase(apps)

    init {
        userDao = userDb?.favoriteDao()
    }

    fun setupUserDetails(name: String) {
        _onFailure.value = false
        _isLoading.value = true

        RetrofitClient.apiInstance.getUserDetail(name)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>
                ) {
                    _isLoading.value = false
                    _onFailure.value = false
                    if (response.isSuccessful) {
                        userViewModel.postValue(response.body())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    _isLoading.value = false
                    _onFailure.value = true
                    Log.d(TAG, t.message.toString())
                }

            })
    }

    fun getDetailuser(): LiveData<DetailUserResponse> {
        return userViewModel
    }

    fun addToFavorite(
        userName: String,
        avaUrl: String,
        htmlUrl: String,
        id: Int
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            val user = UserEntity(userName, avaUrl, htmlUrl, id)

            userDao?.addToFavorite(user)
        }
    }

    fun chekUsers(id: Int) = userDao?.checkCountUser(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            userDao?.deleteFromFavorite(id)
        }
    }

    companion object {
        const val TAG = "This_Details_View_Model"
    }

}