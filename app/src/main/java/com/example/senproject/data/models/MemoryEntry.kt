package com.example.senproject.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity("MemoryEntry_table")
data class MemoryEntry(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val joy: String? = null,
    val smile: String? = null,
    val achievements: String? = null,
    val gratitude: String? = null,
    val activities: String? = null,
): Parcelable
