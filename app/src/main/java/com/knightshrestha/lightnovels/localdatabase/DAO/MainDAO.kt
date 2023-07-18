package com.knightshrestha.lightnovels.localdatabase.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem

@Dao
interface MainDAO {
    @Query("SELECT * FROM series_list")
    fun getALl(): LiveData<List<SeriesItem>>

    @Insert
    suspend fun insertSeriesItem(seriesItem: SeriesItem)

    @Insert
    suspend fun insertBookItem(bookItem: BookItem)


}