package com.adedeji.foodsharedonationapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adedeji.foodsharedonationapp.data.model.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user WHERE userEmail = :email LIMIT 1")
    suspend fun getUserByEmail(email: String): User?

    @Insert
    suspend fun createUser(user: User)

    @Query("SELECT * FROM user WHERE userName = :userName LIMIT 1")
    suspend fun getUserByUserName(userName: String): User?


}