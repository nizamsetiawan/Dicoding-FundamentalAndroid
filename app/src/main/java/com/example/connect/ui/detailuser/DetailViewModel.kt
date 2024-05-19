package com.example.connect.ui.detailuser

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.connect.data.local.Favorite
import com.example.connect.data.local.FavoriteDB
import com.example.connect.data.local.FavoriteDao
import com.example.connect.data.remote.api.ApiConfig
import com.example.connect.data.remote.response.DetailResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private var favoriteDao: FavoriteDao?
    private var favoriteDB: FavoriteDB?

    init {
        favoriteDB = FavoriteDB.getDatabase(application)
        favoriteDao = favoriteDB?.favoriteDao()
    }

    private val _detailUser = MutableLiveData<DetailResponse>()
    val detailUser: LiveData<DetailResponse> = _detailUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val tags = "DetailViewModel"
    fun getDetailUser(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _detailUser.postValue(response.body())
                } else {
                    Log.e(tags, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(tags, "onFailure: ${t.message.toString()}")
            }
        })
    }
    fun addToFavorite(username: String, id :Int, avatarUrl: String, type : String) {
       CoroutineScope(Dispatchers.IO).launch {
              val favoriteUser = Favorite(
                  username,
                  id,
                  avatarUrl,
                  type
              )
              favoriteDao?.addFavorite(favoriteUser)
       }
    }
   suspend fun checkFavoriteUser(id: Int) = favoriteDao?.checkFavoriteByUsername(id)

    fun removeFavorite(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            favoriteDao?.deleteFavorite(id)
        }
    }

}