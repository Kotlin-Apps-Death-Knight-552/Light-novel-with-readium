package com.knightshrestha.lightnovels.localdatabase.repo

import androidx.lifecycle.LiveData
import com.knightshrestha.lightnovels.localdatabase.DAO.MainDAO
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem

class MainRepo(private val seriesDao: MainDAO) {
    fun getAllSeries(): LiveData<List<SeriesItem>> {
        return seriesDao.getALl()
    }

    suspend fun insertSeriesItem(seriesItem: SeriesItem) {
        seriesDao.insertSeriesItem(seriesItem)
    }

    suspend fun insertBookItem(bookItem: BookItem) {
        seriesDao.insertBookItem(bookItem)
    }
}