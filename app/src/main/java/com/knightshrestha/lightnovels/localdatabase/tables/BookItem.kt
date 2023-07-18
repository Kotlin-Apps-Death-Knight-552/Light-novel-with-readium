package com.knightshrestha.lightnovels.localdatabase.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.readium.r2.shared.publication.Locator

@Entity(tableName = "book_list")
data class BookItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "series_id") val seriesID: Int = 0,
    @ColumnInfo(name = "title") val seriesTitle: String,
    @ColumnInfo(name = "author") val seriesAuthor: String = "",
    @ColumnInfo(name = "path") val seriesPath: String,
    @ColumnInfo(name = "last_location") val locator: Locator? = null
)
