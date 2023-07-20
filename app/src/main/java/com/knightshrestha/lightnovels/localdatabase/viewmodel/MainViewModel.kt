package com.knightshrestha.lightnovels.localdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.knightshrestha.lightnovels.localdatabase.MainDatabase
import com.knightshrestha.lightnovels.localdatabase.repo.MainRepo
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesWithBooks
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepo
    val seriesList: LiveData<List<SeriesItem>>
    val bookList: LiveData<List<BookItem>>

    init {
        val dao = MainDatabase.getDatabase(application).localDAO()
        repository = MainRepo(dao)
        seriesList = repository.getAllSeries()
        bookList = repository.getAllBooks()
    }

    fun getSeriesItemWithBooks(seriesPath: String): LiveData<SeriesWithBooks> {
        return repository.getSeriesWithBooks(seriesPath)
    }

    suspend fun getOneBookItem(seriesPath: String): BookItem? {
        return repository.getOneBookItem(seriesPath)
    }

    fun addSeriesItem(seriesItem: SeriesItem) {
        viewModelScope.launch {
            if (!repository.isSeriesItemExist(seriesItem.seriesPath)) {
                repository.insertSeriesItem(seriesItem)
            }
        }
    }

    fun addBookItem(bookItem: BookItem) {
        viewModelScope.launch {
            if (!repository.isBookItemExist(bookItem.bookPath)) {
                repository.insertBookItem(bookItem)
            }
        }
    }

    fun updateBookItem(bookItem: BookItem) {
        viewModelScope.launch {
            if (repository.isBookItemExist(bookItem.bookPath)) {
                repository.updateBookItem(bookItem)
            }
        }
    }


}