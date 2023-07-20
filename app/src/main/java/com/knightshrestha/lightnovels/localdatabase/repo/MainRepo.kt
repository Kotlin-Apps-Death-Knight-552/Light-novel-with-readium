package com.knightshrestha.lightnovels.localdatabase.repo

import androidx.lifecycle.LiveData
import com.knightshrestha.lightnovels.localdatabase.DAO.MainDAO
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesWithBooks

class MainRepo(private val seriesDao: MainDAO) {
    fun getAllSeries(): LiveData<List<SeriesItem>> {
        return seriesDao.getALlSeries()
    }

    fun getAllBooks(): LiveData<List<BookItem>> {
        return seriesDao.getAllBooks()
    }

    fun getSeriesWithBooks(path: String): LiveData<SeriesWithBooks> {
        return seriesDao.getSeriesItemWithBooks(path)
    }

    suspend fun getOneBookItem(path: String): BookItem? {
        return seriesDao.getBookByPath(path)
    }

    suspend fun isSeriesItemExist(path: String): Boolean {
        val seriesItem = seriesDao.getSeriesByPath(path)
        return seriesItem != null
    }

    suspend fun isBookItemExist(path: String): Boolean {
        val bookItem = seriesDao.getBookByPath(path)
        return bookItem != null
    }


    suspend fun insertSeriesItem(seriesItem: SeriesItem) {
        seriesDao.insertSeriesItem(seriesItem)
    }

    suspend fun insertBookItem(bookItem: BookItem) {
        seriesDao.insertBookItem(bookItem)
    }

    suspend fun updateBookItem(bookItem: BookItem) {
        seriesDao.updateOneBookItem(bookItem)

    }

    suspend fun updateSeriesItem(seriesItem: SeriesItem) {
        seriesDao.updateOneSeriesItem(seriesItem)
    }

    suspend fun deleteSeriesItemByPath(seriesPath:String) {
        seriesDao.deleteSeriesItemByPath(seriesPath)
    }

    suspend fun deleteBookItem(bookItem: BookItem) {
        seriesDao.deleteBookItem(bookItem)
    }
}