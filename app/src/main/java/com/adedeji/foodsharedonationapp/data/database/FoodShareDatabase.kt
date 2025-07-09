package com.adedeji.foodsharedonationapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.adedeji.foodsharedonationapp.data.model.Donation
import com.adedeji.foodsharedonationapp.data.model.News
import com.adedeji.foodsharedonationapp.data.model.User
import kotlinx.coroutines.InternalCoroutinesApi

@Database(entities = [Donation::class, News::class, User::class], version = 1)
abstract class FoodShareDatabase: RoomDatabase() {

    abstract fun getDonationDao(): DonationDao
    abstract fun getNewsDao(): NewsDao
    abstract fun getUserDao(): UserDao


    companion object{
        @Volatile
        private var INSTANCE: FoodShareDatabase? = null

        @OptIn(InternalCoroutinesApi::class)
        fun getDatabase(context: Context): FoodShareDatabase {
            return INSTANCE?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FoodShareDatabase::class.java,
                    "food_share_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}