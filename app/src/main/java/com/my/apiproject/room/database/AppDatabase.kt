package com.my.apiproject.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.my.apiproject.room.dao.ItemsDao
import com.my.apiproject.room.entity.ItemsEntity

@Database(entities = [ItemsEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun itemsDao(): ItemsDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private const val databaseName = "myDB.db"
        private val LOCK = Any()

        fun getInstance(context: Context): AppDatabase = INSTANCE ?: synchronized(LOCK) {
            val instance = Room.databaseBuilder(context, AppDatabase::class.java, databaseName)
                .fallbackToDestructiveMigration()
                .build()
            INSTANCE = instance
            instance
        }
    }


}