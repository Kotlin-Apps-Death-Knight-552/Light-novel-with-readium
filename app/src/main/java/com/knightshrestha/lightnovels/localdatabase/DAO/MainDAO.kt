package com.knightshrestha.lightnovels.localdatabase.DAO

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesWithBooks


@Dao
interface MainDAO {
    @Query("SELECT * FROM series_list")
    fun getALlSeries(): LiveData<List<SeriesItem>>

    @Transaction
    @Query("SELECT * FROM series_list WHERE path = :seriesPath")
    fun getSeriesItemWithBooks(seriesPath: String): LiveData<SeriesWithBooks>

    @Query("SELECT * FROM book_list")
    fun getAllBooks(): LiveData<List<BookItem>>

    @Query("SELECT * FROM book_list WHERE path = :bookPath")
    fun getOneBookByPath(bookPath: String): LiveData<BookItem>

    @Query("SELECT * FROM series_list WHERE path = :path")
    suspend fun getSeriesByPath(path: String): SeriesItem?

    @Query("SELECT * FROM book_list WHERE path = :path")
    suspend fun getBookByPath(path: String): BookItem?

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun insertSeriesItem(seriesItem: SeriesItem)

    @Insert
    suspend fun insertBookItem(bookItem: BookItem)

    @Update
    suspend fun updateOneBookItem(bookItem: BookItem)

    @Update
    suspend fun updateOneSeriesItem(seriesItem: SeriesItem)

    @Delete
    suspend fun deleteSeriesItem(seriesItem: SeriesItem)

    @Delete
    suspend fun deleteBookItem(bookItem: BookItem)

    @Query("DELETE FROM series_list WHERE path = :seriesPath")
    suspend fun deleteSeriesItemByPath(seriesPath: String)
}
