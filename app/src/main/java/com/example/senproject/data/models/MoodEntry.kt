package com.example.senproject.data.models

import com.example.senproject.data.MoodState

data class MoodEntry(
    val moodState: MoodState,
    val time: String, //toDo: change to the DateTime type
    val emotions: List<String>? = null,
    val activities: List<String>? = null,
    val text: String? = null,
)
