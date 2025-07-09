package com.adedeji.foodsharedonationapp.data

import com.adedeji.foodsharedonationapp.data.database.NewsDao
import com.adedeji.foodsharedonationapp.data.model.News

class NewsRepository(private val newsDao: NewsDao) {

    suspend fun getAllNews(): List<News> {
        return newsDao.getAllNews()
    }
    suspend fun insertNews(news: News) {
        newsDao.insertNews(news)
    }

}