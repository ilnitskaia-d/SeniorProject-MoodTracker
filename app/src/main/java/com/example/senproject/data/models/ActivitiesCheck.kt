package com.example.senproject.data.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity("activities_table")
data class ActivitiesCheck(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    var checked: Boolean = false,
    var name: String,
    var iconEmoji: String = ":)"
): Parcelable
