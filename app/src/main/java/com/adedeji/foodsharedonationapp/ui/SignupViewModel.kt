package com.adedeji.foodsharedonationapp.ui

import androidx.lifecycle.ViewModel
import com.adedeji.foodsharedonationapp.FoodShareApplication
import com.adedeji.foodsharedonationapp.data.UserRepository
import com.adedeji.foodsharedonationapp.data.model.User

class SignupViewModel: ViewModel() {
    private var repository: UserRepository = FoodShareApplication.Companion.userRepository

    suspend fun createUser(email: String, password: String, username: String){
        val user = User(userEmail = email, userPassword = password, userName = username)
        repository.createUser(user)
    }

    suspend fun getUser(email: String): User? {
        return repository.getUserByEmail(email)

    }
}