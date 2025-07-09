package com.adedeji.foodsharedonationapp.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.adedeji.foodsharedonationapp.data.model.Donation

@Dao
interface DonationDao {
    @Insert
    suspend fun insertDonation(donation: Donation)

    @Query("SELECT * FROM donations ORDER BY donationDate DESC")
    suspend fun getAllDonations(): List<Donation>

    //@Query("SELECT * FROM donations WHERE user_id = :userId ORDER BY donationDate DESC")
    //suspend fun getDonationsByUser(userId: String): List<Donation>

    @Update
    suspend fun updateDonation(donation: Donation)

    @Delete
    suspend fun deleteDonation(donation: Donation)

}