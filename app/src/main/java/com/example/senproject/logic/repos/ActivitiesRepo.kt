package com.example.senproject.logic.repos

import androidx.lifecycle.LiveData
import com.example.senproject.data.models.ActivitiesCheck
import com.example.senproject.data.models.MoodEntry
import com.example.senproject.logic.dao.ActivitiesDao
import com.example.senproject.logic.dao.MoodEntryDao

class ActivitiesRepo(private val activitiesDao: ActivitiesDao) {
    val getAllActivities: LiveData<List<ActivitiesCheck>> = activitiesDao.getAllActivities()

    suspend fun addActivities(activitiesCheck: ActivitiesCheck) {
        activitiesDao.addActivities(activitiesCheck)
    }

    suspend fun updateActivities(activitiesCheck: ActivitiesCheck) {
        activitiesDao.updateActivities(activitiesCheck)
    }

    suspend fun deleteActivities(activitiesCheck: ActivitiesCheck) {
        activitiesDao.deleteActivities(activitiesCheck)
    }
}