package com.example.connect.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable


@Entity(tableName = "favorite")
data class Favorite (
    var login: String,
    @PrimaryKey
    var id : Int,
    var avatarUrl: String,
    var type: String
): Serializable