package com.knightshrestha.lightnovels.localdatabase.tables

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.knightshrestha.lightnovels.localdatabase.helpers.AssociatedTitles
import com.knightshrestha.lightnovels.localdatabase.helpers.Count
import com.knightshrestha.lightnovels.localdatabase.helpers.Status


@Entity(tableName = "series_list", indices = [Index(value = ["path"], unique = true)])
data class SeriesItem(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "series_id") val seriesID: Int = 0,
    @ColumnInfo(name = "title") val seriesTitle: String,
    @ColumnInfo(name = "author") val seriesAuthor: String = "",
    @ColumnInfo(name = "thumbnail") val seriesThumbnail: String = "",
    @ColumnInfo(name = "synopsis") val seriesSynopsis: String = "",
    @ColumnInfo(name = "genres") val seriesGenres: List<String> = listOf("UNCATEGORIZED"),
    @ColumnInfo(name = "download") val seriesDownload: String = "",
    @ColumnInfo(name = "status") val seriesStatus: Status = Status.UNREAD,
    @ColumnInfo(name = "count") val seriesBooksCount: Count = Count(0,0,0),
    @ColumnInfo(name = "last_opened") val lastReadBook: Int = 0,
    @ColumnInfo(name = "anilist_id") val anilistID: Int = 0,
    @ColumnInfo(name = "path") val seriesPath: String,
    @ColumnInfo(name = "associated_titles") val associatedTitles: List<AssociatedTitles> = emptyList()
)
