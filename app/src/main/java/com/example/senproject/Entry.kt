package com.example.senproject

data class Entry(
    val moodState: MoodState,
    val emotions: List<String>,
    val activities: List<String>,
    val text: String,
)
