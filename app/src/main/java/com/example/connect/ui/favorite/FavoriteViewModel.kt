package com.example.connect.ui.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.connect.data.local.Favorite
import com.example.connect.data.local.FavoriteDB
import com.example.connect.data.local.FavoriteDao

class FavoriteViewModel(application: Application) : AndroidViewModel(application) {
    private var favoriteDao: FavoriteDao?
    private var favoriteDB: FavoriteDB?

    init {
        favoriteDB = FavoriteDB.getDatabase(application)
        favoriteDao = favoriteDB?.favoriteDao()
    }

    fun getFavoriteListUser() : LiveData<List<Favorite>>? {
       return favoriteDao?.getAllFavorite()
    }







}