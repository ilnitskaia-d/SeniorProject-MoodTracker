package com.example.senproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.logic.dao.ActivitiesDao

@Database(entities = [ActivitiesCheck::class], version = 1, exportSchema = false)
abstract class ActivitiesDatabase: RoomDatabase() {

    abstract fun ActivitiesDao(): ActivitiesDao

    companion object{
        @Volatile
        private var INSTANCE: ActivitiesDatabase? = null

        fun getDatabase(context: Context): ActivitiesDatabase {
            val tempInstance:ActivitiesDatabase? = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ActivitiesDatabase::class.java,
                    "activities_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}