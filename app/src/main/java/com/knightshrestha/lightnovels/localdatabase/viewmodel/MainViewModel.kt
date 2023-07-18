package com.knightshrestha.lightnovels.localdatabase.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.knightshrestha.lightnovels.localdatabase.MainDatabase
import com.knightshrestha.lightnovels.localdatabase.repo.MainRepo
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository: MainRepo
    val seriesList: LiveData<List<SeriesItem>>

    init {
        val dao = MainDatabase.getDatabase(application).localDAO()
        repository = MainRepo(dao)
        seriesList = repository.getAllSeries()
    }

    fun addSeriesItem(seriesItem: SeriesItem) {
        viewModelScope.launch {
            repository.insertSeriesItem(seriesItem)
        }
    }

}