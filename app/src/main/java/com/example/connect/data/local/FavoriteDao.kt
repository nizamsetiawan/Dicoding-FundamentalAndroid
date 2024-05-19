package com.example.connect.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface FavoriteDao {
    @Insert
    suspend fun addFavorite(favorite: Favorite)

    @Query("SELECT * from favorite")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT count(*) from favorite WHERE favorite.id = :id")
    suspend fun checkFavoriteByUsername(id : Int) : Int

    @Query("DELETE FROM favorite WHERE favorite.id = :id")
    suspend fun deleteFavorite(id: Int) : Int



}