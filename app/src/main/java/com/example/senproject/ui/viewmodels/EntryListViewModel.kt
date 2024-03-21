package com.example.senproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.senproject.data.database.MoodEntryDatabase
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.logic.repos.MoodEntryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EntryListViewModel(application: Application): AndroidViewModel(application) {
    private val repo: MoodEntryRepo
    val getAllMoodEntries: LiveData<List<MoodEntry>>

    init {
        repo = MoodEntryRepo(MoodEntryDatabase.getDatabase(application).moodEntryDao())
        getAllMoodEntries = repo.getAllMoodEntries
    }

    fun updateMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.updateMoodEntry(moodEntry)
        }
    }

    fun deleteMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.deleteMoodEntry(moodEntry)
        }
    }
}