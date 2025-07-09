package com.adedeji.foodsharedonationapp.ui

import androidx.lifecycle.ViewModel
import com.adedeji.foodsharedonationapp.FoodShareApplication
import com.adedeji.foodsharedonationapp.data.UserRepository
import com.adedeji.foodsharedonationapp.data.model.User

class LoginViewModel: ViewModel() {
    private var repository: UserRepository = FoodShareApplication.Companion.userRepository

    suspend fun getUser(email: String): User? {
        val user = repository.getUserByEmail(email)
        return user
    }
}