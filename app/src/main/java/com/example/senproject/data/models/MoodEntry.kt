package com.example.senproject.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.senproject.data.MoodState
import com.example.senproject.utils.Converters
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
@Entity("moodEntry_table")
@TypeConverters(Converters::class)
data class MoodEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val moodState: MoodState,
    val time: String,
    val day: LocalDate,
    val activities: List<String>,
    val text: String? = null,
): Parcelable
