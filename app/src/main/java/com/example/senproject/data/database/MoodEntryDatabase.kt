package com.example.senproject.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.logic.dao.MoodEntryDao

@Database(entities = [MoodEntry::class], version = 1, exportSchema = false)
abstract class MoodEntryDatabase: RoomDatabase() {

    abstract fun moodEntryDao(): MoodEntryDao

    companion object{
        @Volatile
        private var INSTANCE: MoodEntryDatabase? = null

        fun getDatabase(context: Context): MoodEntryDatabase {
            val tempInstance:MoodEntryDatabase? = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoodEntryDatabase::class.java,
                    "moodentry_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}