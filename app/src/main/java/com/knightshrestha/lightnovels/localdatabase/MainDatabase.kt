package com.knightshrestha.lightnovels.localdatabase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.knightshrestha.lightnovels.localdatabase.DAO.MainDAO
import com.knightshrestha.lightnovels.localdatabase.tables.BookItem
import com.knightshrestha.lightnovels.localdatabase.tables.SeriesItem
import com.knightshrestha.neonnovels.datasources.local.database.CountConverters
import com.knightshrestha.neonnovels.datasources.local.database.ListConverters
import com.knightshrestha.neonnovels.datasources.local.database.RelatedTitleConverters

@Database(entities = [SeriesItem::class, BookItem::class], version = 1, exportSchema = false)
@TypeConverters( ListConverters::class, CountConverters::class, RelatedTitleConverters::class)
abstract class MainDatabase : RoomDatabase() {
    abstract fun localDAO(): MainDAO

    companion object {

        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDatabase(context: Context): MainDatabase {

            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext, MainDatabase::class.java, "Origin"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}