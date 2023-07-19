package com.knightshrestha.lightnovels.localdatabase.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.knightshrestha.lightnovels.localdatabase.helpers.Status
import org.readium.r2.shared.publication.Locator

@Entity(tableName = "book_list")
data class BookItem(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "path") val bookPath: String,
    @ColumnInfo(name = "series_path") val seriesPath: String,
    @ColumnInfo(name = "title") val bookTitle: String,
    @ColumnInfo(name = "last_location") val locator: Locator? = null,
    @ColumnInfo(name = "status") val status: Status = Status.UNREAD
)
