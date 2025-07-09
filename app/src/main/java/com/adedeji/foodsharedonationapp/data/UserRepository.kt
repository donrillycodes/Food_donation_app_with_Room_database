package com.adedeji.foodsharedonationapp.data

import com.adedeji.foodsharedonationapp.data.database.UserDao
import com.adedeji.foodsharedonationapp.data.model.User

class UserRepository(private val userDao: UserDao) {

    suspend fun getUserByEmail(userEmail: String): User? {
        return userDao.getUserByEmail(userEmail)
    }

    suspend fun createUser(user: User) {
        userDao.createUser(user)
    }

    suspend fun getUserByUserName(userName: String): User? {
        return userDao.getUserByUserName(userName)
    }
}