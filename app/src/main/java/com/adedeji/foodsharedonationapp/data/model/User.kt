package com.adedeji.foodsharedonationapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User (
    @ColumnInfo(name = "user_id") @PrimaryKey(autoGenerate = true) val userID: Int = 0,
    val userEmail: String,
    val userPassword: String,
    val userName: String
)