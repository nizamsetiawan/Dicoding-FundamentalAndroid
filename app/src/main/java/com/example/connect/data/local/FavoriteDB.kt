package com.example.connect.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [Favorite::class], version = 2)
abstract class FavoriteDB : RoomDatabase() {
    companion object {
         var instance: FavoriteDB? = null

        fun getDatabase(context: Context): FavoriteDB {
            return instance ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteDB::class.java,
                    "favorites_db"
                ).build()
                instance
            }
        }
    }
    abstract fun favoriteDao(): FavoriteDao
}