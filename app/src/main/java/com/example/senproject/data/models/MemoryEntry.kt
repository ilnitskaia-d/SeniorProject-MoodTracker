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
    var joy: String? = null,
    var achievements: String? = null,
    var smile: String? = null,
    var gratitude: String? = null,
): Parcelable {

}
