package com.example.senproject.logic.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.senproject.data.models.ActivitiesCheck

@Dao
interface ActivitiesDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addActivities(activities: ActivitiesCheck)

    @Update
    suspend fun updateActivities(activities: ActivitiesCheck)

    @Delete
    suspend fun deleteActivities(activities: ActivitiesCheck)

    @Query("SELECT * FROM activities_table")
    fun getAllActivities(): LiveData<List<ActivitiesCheck>>
}