package com.knightshrestha.lightnovels.localdatabase.tables

import androidx.room.Embedded
import androidx.room.Relation

data class SeriesWithBooks(
    @Embedded val seriesItem: SeriesItem,
    @Relation(
        parentColumn = "path",
        entityColumn = "series_path"
    )
    val bookItems: List<BookItem>
)