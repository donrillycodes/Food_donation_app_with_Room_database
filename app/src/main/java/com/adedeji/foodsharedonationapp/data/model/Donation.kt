package com.adedeji.foodsharedonationapp.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Entity(tableName = "donations")
data class Donation(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    //@ColumnInfo(name = "user_id") val userId: String,
    val foodType: String,
    val quantity: Int,
    val expiryDate: String,
    val donationMethod: String,
    val donationDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
)