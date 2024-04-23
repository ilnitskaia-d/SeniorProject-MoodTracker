package com.example.senproject.ui.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.senproject.data.database.ActivitiesDatabase
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.logic.repos.ActivitiesRepo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditActivitiesViewModel(application: Application)
    : AndroidViewModel(application) {
        private val repoActivities: ActivitiesRepo
        val getAllActivities: LiveData<List<ActivitiesCheck>>

        init {
            repoActivities = ActivitiesRepo(
                ActivitiesDatabase
                    .getDatabase(application)
                    .ActivitiesDao())

            getAllActivities = repoActivities.getAllActivities
        }

        fun addActivities(a : ActivitiesCheck) {
            viewModelScope.launch(Dispatchers.IO) {
                repoActivities.addActivities(a)
            }
        }

        fun updateActivities(a : ActivitiesCheck) {
            viewModelScope.launch(Dispatchers.IO) {
                repoActivities.updateActivities(a)
            }
        }

        fun deleteActivities(a : ActivitiesCheck) {
            viewModelScope.launch(Dispatchers.IO) {
                repoActivities.deleteActivities(a)
            }
        }
}