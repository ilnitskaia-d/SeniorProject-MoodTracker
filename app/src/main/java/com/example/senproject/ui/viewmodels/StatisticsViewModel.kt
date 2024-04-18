package com.example.senproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.senproject.data.database.MoodEntryDatabase
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.logic.repos.MoodEntryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class StatisticsViewModel(application: Application): AndroidViewModel(application) {
    private val repo: MoodEntryRepo
    val getAllMoodEntries: LiveData<List<MoodEntry>>
    val moodEntriesByDate: MutableLiveData<List<MoodEntry>>

    init {
        repo = MoodEntryRepo(MoodEntryDatabase.getDatabase(application).moodEntryDao())
        getAllMoodEntries = repo.getAllMoodEntries

        moodEntriesByDate = repo.moodEntriesByDate
    }

    fun getMoodEntriesByDate(dayMonth: String) = viewModelScope.launch {
        repo.getMoodEntriesByDate(dayMonth)
    }

}