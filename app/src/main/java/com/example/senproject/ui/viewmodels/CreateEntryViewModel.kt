package com.example.senproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.senproject.data.database.ActivitiesDatabase
import com.example.senproject.data.database.MoodEntryDatabase
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.logic.repos.ActivitiesRepo
import com.example.senproject.logic.repos.MoodEntryRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateEntryViewModel(application: Application): AndroidViewModel(application) {
    private val repoMoodEntry: MoodEntryRepo
    private val repoActivities: ActivitiesRepo
    val getAllActivities: LiveData<List<ActivitiesCheck>>


    init {
        repoMoodEntry = MoodEntryRepo(MoodEntryDatabase.getDatabase(application).moodEntryDao())
        repoActivities = ActivitiesRepo(ActivitiesDatabase.getDatabase(application).ActivitiesDao())

        getAllActivities = repoActivities.getAllActivities
    }

    fun addMoodEntry(moodEntry: MoodEntry) {
        viewModelScope.launch(Dispatchers.IO) {
            repoMoodEntry.addMoodEntry(moodEntry)
        }
    }
}