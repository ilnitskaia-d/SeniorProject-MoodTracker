package com.example.senproject.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.senproject.data.models.MoodEntry

@Dao
interface MoodEntryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addMoodEntry(moodEntry: MoodEntry)

    @Update
    suspend fun updateMoodEntry(moodEntry: MoodEntry)

    @Delete
    suspend fun deleteMoodEntry(moodEntry: MoodEntry)

    @Query("SELECT * FROM moodEntry_table ORDER BY time DESC")
    fun getAllMoodEntries(): LiveData<List<MoodEntry>>

    @Query("SELECT * FROM moodEntry_table WHERE day = :dayMonth ORDER BY time DESC")
    suspend fun getMoodEntriesByDate(dayMonth: String): List<MoodEntry>
}