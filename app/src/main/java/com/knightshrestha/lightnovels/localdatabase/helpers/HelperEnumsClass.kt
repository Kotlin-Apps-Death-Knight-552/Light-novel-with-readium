package com.knightshrestha.lightnovels.localdatabase.helpers

data class Count(
    val UNREAD: Int = 0,
    val READING: Int= 0,
    val COMPLETED: Int = 0
)

enum class Status {
    UNREAD,
    READING,
    COMPLETED
}

data class AssociatedTitles(
    val seriesID: Int,
    val relation: Relation
)

data class Bookmarks(
    val text: String,
    val page: Int
)

enum class Relation {
    PREQUEL, SEQUEL, SPIN_OFF
}