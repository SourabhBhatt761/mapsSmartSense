package com.srb.mapssmartsense.db

import android.content.Context
import androidx.room.*


@Database(entities = [AppEntity :: class], version = 1, exportSchema = true)
//@TypeConverters(DateConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun appDao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE
                    ?: buildDatabase(context).also { INSTANCE = it }
            }

        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java, "map_database"
            ).build()
    }

}