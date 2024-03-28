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

class CreateEntryViewModel(application: Application): AndroidViewModel(application) {
    private val repo: MoodEntryRepo

    init {
        repo = MoodEntryRepo(MoodEntryDatabase.getDatabase(application).moodEntryDao())
    }

    fun addMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repo.addMoodEntry(moodEntry)
        }
    }
}