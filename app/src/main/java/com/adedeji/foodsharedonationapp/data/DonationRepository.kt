package com.adedeji.foodsharedonationapp.data

import com.adedeji.foodsharedonationapp.data.database.DonationDao
import com.adedeji.foodsharedonationapp.data.model.Donation

class DonationRepository(private val donationDao: DonationDao) {

    suspend fun insertDonation(donations: Donation) {
        donationDao.insertDonation(donations)
    }

    suspend fun getAllDonations(): List<Donation> {
        return donationDao.getAllDonations()
    }

//    suspend fun getDonationsByUser(userId: String): List<Donation> {
//        return donationDao.getDonationsByUser(userId)
//    }

    suspend fun updateDonations(donation: Donation) {
        donationDao.updateDonation(donation)
    }

    suspend fun deleteDonation(donation: Donation) {
        donationDao.deleteDonation(donation)
    }

}