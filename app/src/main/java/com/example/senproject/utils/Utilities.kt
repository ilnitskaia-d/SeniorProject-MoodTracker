package com.example.senproject.utils

import com.example.senproject.R
import com.example.senproject.data.MoodState

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
}