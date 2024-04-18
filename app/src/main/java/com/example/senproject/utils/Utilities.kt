package com.example.senproject.utils

import com.example.senproject.R
import com.example.senproject.data.MoodState
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

object Utilities {
    fun getMoodIcon(moodState: MoodState): Int {
        return when (moodState) {
            MoodState.V_GOOD -> R.drawable.emotion_great
            MoodState.GOOD -> R.drawable.emotion_good
            MoodState.OK -> R.drawable.emotion_ok
            MoodState.BAD -> R.drawable.emotion_notgood
            MoodState.V_BAD -> R.drawable.emotion_bad
        }
    }

    fun getTodayDate(): LocalDate {
        return LocalDate.now()
    }

    fun getTimeNow(): String {
        return LocalTime.now().format(DateTimeFormatter.ofPattern("HH:mm"))
    }
}