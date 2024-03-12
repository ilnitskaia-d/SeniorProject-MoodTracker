package com.example.senproject.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.senproject.data.MoodState
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity("moodEntry_table")
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val moodState: MoodState,
    val time: String, //toDo: change to the DateTime type
    val emotions: List<String>? = null,
    val activities: List<String>? = null,
    val text: String? = null,
): Parcelable
