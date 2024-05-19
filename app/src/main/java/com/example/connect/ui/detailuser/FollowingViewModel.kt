package com.example.connect.ui.detailuser

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.connect.data.remote.api.ApiConfig
import com.example.connect.data.remote.response.ItemResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {
    private val _listFollowing = MutableLiveData<List<ItemResponse>>()
    val listFollowing: LiveData<List<ItemResponse>> = _listFollowing

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FollowingViewModel"
    }
    fun getFollowing(username: String) {
        _isLoading.postValue(true)
        val call = ApiConfig.getApiService().getFollowing(username)
        call.enqueue(object : Callback<List<ItemResponse>> {
            override fun onResponse(call: Call<List<ItemResponse>>, response: Response<List<ItemResponse>>) {
                _isLoading.postValue(false)
                if (response.isSuccessful) {
                    val following = response.body()
                    _listFollowing.postValue(following?.toList())
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemResponse>>, t: Throwable) {
                _isLoading.postValue(false)
                Log.e(TAG, "onFailure: ${t.message.toString()}")
            }
        })
    }
}