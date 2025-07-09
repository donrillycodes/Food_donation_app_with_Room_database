package com.adedeji.foodsharedonationapp.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.adedeji.foodsharedonationapp.data.model.News

@Dao
interface NewsDao {

    @Insert
    suspend fun insertNews(news: News)

    @Query("SELECT * FROM news")
    suspend fun getAllNews(): List<News>
}