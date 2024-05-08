package com.example.senproject.logic.repos

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.logic.dao.MoodEntryDao

class MoodEntryRepo(private val moodEntryDao: MoodEntryDao) {
    val getAllMoodEntries: LiveData<List<MoodEntry>> = moodEntryDao.getAllMoodEntries()
    val moodEntriesByDate = MutableLiveData<List<MoodEntry>>()

    suspend fun getMoodEntriesByDate(dayMonth: String) {
        moodEntriesByDate.value = moodEntryDao.getMoodEntriesByDate(dayMonth)
    }

    suspend fun addMoodEntry(moodEntry: MoodEntry) {
        moodEntryDao.addMoodEntry(moodEntry)
    }

    suspend fun deleteAll() {
        moodEntryDao.deleteAll()
    }

    suspend fun updateMoodEntry(moodEntry: MoodEntry) {
        moodEntryDao.updateMoodEntry(moodEntry)
    }

    suspend fun deleteMoodEntry(moodEntry: MoodEntry) {
        moodEntryDao.deleteMoodEntry(moodEntry)
    }
}