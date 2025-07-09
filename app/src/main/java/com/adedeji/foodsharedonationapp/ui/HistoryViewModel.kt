package com.adedeji.foodsharedonationapp.ui

import androidx.lifecycle.ViewModel
import com.adedeji.foodsharedonationapp.FoodShareApplication
import com.adedeji.foodsharedonationapp.data.DonationRepository
import com.adedeji.foodsharedonationapp.data.model.Donation

class HistoryViewModel: ViewModel() {
    private var repository: DonationRepository = FoodShareApplication.Companion.donationRepository

    suspend fun getAllDonations(): List<Donation> {
        return repository.getAllDonations()
    }

//    suspend fun getDonationsByUser(userId: String): List<Donation> {
//        return repository.getDonationsByUser(userId)
//    }

    suspend fun updateDonation(donation: Donation) {
        repository.updateDonations(donation)
    }

    suspend fun deleteDonation(donation: Donation) {
        repository.deleteDonation(donation)
    }

}