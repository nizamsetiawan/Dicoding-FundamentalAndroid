package com.example.connect.data.remote.api

import com.example.connect.data.remote.response.DetailResponse
import com.example.connect.data.remote.response.ItemResponse
import com.example.connect.data.remote.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    fun getListUser(
        @Query("q") id: String
    ): Call<SearchResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<ItemResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<ItemResponse>>
}