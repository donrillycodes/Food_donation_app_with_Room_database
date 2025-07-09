package com.adedeji.foodsharedonationapp.ui

import androidx.lifecycle.ViewModel
import com.adedeji.foodsharedonationapp.FoodShareApplication
import com.adedeji.foodsharedonationapp.R
import com.adedeji.foodsharedonationapp.data.DonationRepository
import com.adedeji.foodsharedonationapp.data.NewsRepository
import com.adedeji.foodsharedonationapp.data.UserRepository
import com.adedeji.foodsharedonationapp.data.model.Donation
import com.adedeji.foodsharedonationapp.data.model.News
import com.adedeji.foodsharedonationapp.data.model.User

class HomeViewModel: ViewModel() {
    private var repository: NewsRepository = FoodShareApplication.Companion.newsRepository
    private var donationRepository: DonationRepository = FoodShareApplication.Companion.donationRepository
    private var userRepository: UserRepository = FoodShareApplication.Companion.userRepository


    suspend fun getAllNews(): List<News>{
        return repository.getAllNews()
    }

    //insert dummy data
    suspend fun insertDummyNews() {
        if (repository.getAllNews().isEmpty()) {
            val dummyNews = listOf(
                News(
                    title = "Community Food Drive",
                    description = "Distributed 500 meals last weekend",
                    imageResId = R.drawable.d1
                ),
                News(
                    title = "School Lunch Program",
                    description = "Providing meals to 10 local schools",
                    imageResId = R.drawable.d2
                )
            )
            dummyNews.forEach { repository.insertNews(it) }
        }
    }

    suspend fun insertDonation(donations: Donation) {
        donationRepository.insertDonation(donations)
    }

    suspend fun getUserByUserName(userName: String): User? {
        return userRepository.getUserByUserName(userName)
    }

    suspend fun getUserByEmail(userEmail: String): User? {
        return userRepository.getUserByEmail(userEmail)
    }
}