package com.knightshrestha.lightnovels.localdatabase.DAO

import androidx.room.Dao
import androidx.room.Query
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem

@Dao
interface MainDAO {
    @Query("SELECT * FROM series_list")
    fun getALl(): List<SeriesItem>
}