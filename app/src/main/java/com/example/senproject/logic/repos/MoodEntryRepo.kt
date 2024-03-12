package com.example.senproject.logic.repos

import androidx.lifecycle.LiveData
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.logic.dao.MoodEntryDao

class MoodEntryRepo(private val moodEntryDao: MoodEntryDao) {
    val getAllMoodEntries: LiveData<List<MoodEntry>> = moodEntryDao.getAllMoodEntries()

    suspend fun addMoodEntry(moodEntry: MoodEntry) {
        moodEntryDao.addMoodEntry(moodEntry)
    }

    suspend fun updateMoodEntry(moodEntry: MoodEntry) {
        moodEntryDao.updateMoodEntry(moodEntry)
    }

    suspend fun deleteMoodEntry(moodEntry: MoodEntry) {
        moodEntryDao.deleteMoodEntry(moodEntry)
    }
}