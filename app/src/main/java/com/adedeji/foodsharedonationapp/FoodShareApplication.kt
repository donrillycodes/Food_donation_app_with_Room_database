package com.adedeji.foodsharedonationapp

import android.app.Application
import com.adedeji.foodsharedonationapp.data.DonationRepository
import com.adedeji.foodsharedonationapp.data.NewsRepository
import com.adedeji.foodsharedonationapp.data.UserRepository
import com.adedeji.foodsharedonationapp.data.database.FoodShareDatabase

class FoodShareApplication: Application() {
    override fun onCreate() {
        super.onCreate()

        val database = FoodShareDatabase.getDatabase(this)
        donationRepository = DonationRepository(database.getDonationDao())
        newsRepository = NewsRepository(database.getNewsDao())
        userRepository = UserRepository(database.getUserDao())
    }

    companion object {
        lateinit var donationRepository: DonationRepository
        lateinit var newsRepository: NewsRepository
        lateinit var userRepository: UserRepository
    }
}