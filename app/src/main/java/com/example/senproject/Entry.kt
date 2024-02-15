package com.example.senproject

data class Entry(
    val moodState: MoodState,
    val time: String, //toDo: change to the DateTime type
    val emotions: List<String>?,
    val activities: List<String>?,
    val text: String?,
)
