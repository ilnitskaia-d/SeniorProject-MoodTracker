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

    fun getRandomPrompt(mood: MoodState): String {
        val random = (0..4).random()

        when (mood) {
            MoodState.V_GOOD -> {
                return when(random) {
                    0 -> "What specific events or experiences contributed to your exceptional mood today?"
                    1 -> "How did you express joy or gratitude throughout the day?"
                    2 -> "What activities did you engage in that made you feel happy?"
                    3 -> "Reflect on a moment that made you smile or laugh today:"
                    4 -> "How did your positive mood impact your activities today?"
                    else -> ""
                }
            }
            MoodState.GOOD -> {
                return when(random) {
                    0 -> "What moments or accomplishments brought you a sense of contentment or satisfaction?"
                    1 -> "Describe a small victory or something positive that brightened your day:"
                    2 -> "How did you maintain your positivity during challenges you faced today?"
                    3 -> "Reflect on something that made you feel optimistic or hopeful today:"
                    4 -> "What contributed to your mood today?"
                    else -> ""
                }
            }
            MoodState.OK -> {
                return when(random) {
                    0 -> "What were the highlights of your day, even if they were small or seemingly insignificant?"
                    1 -> "Describe any moments of tranquility or peace you experienced today."
                    2 -> "How did you navigate through any mundane or routine tasks with a sense of calmness?"
                    3 -> "Reflect on a moment where you felt balanced or at ease."
                    4 -> "Did you engage in any self-care practices today? If so, how did they contribute to your overall sense of well-being?"
                    else -> ""
                }
            }
            MoodState.BAD -> {
                return when(random) {
                    0 -> "What triggered your negative mood today? Describe the events or thoughts that led to it."
                    1 -> "How did you attempt to cope with your negative emotions?"
                    2 -> "Reflect on any patterns or triggers that you notice recurring when you're in a bad mood."
                    3 -> "What strategies or techniques did you employ to try and improve your mood, if any?"
                    4 -> "Did you reach out to someone for support or comfort? If not, is there someone you could confide in?"
                    else -> ""
                }
            }
            MoodState.V_BAD -> {
                return when(random) {
                    0 -> "Describe the intensity of your negative emotions today and how they manifested."
                    1 -> "What thoughts or beliefs contributed to your extremely low mood?"
                    2 -> "Reflect on any physical sensations or behaviors associated with your distress."
                    3 -> "Have you experienced similar intense emotions in the past? If so, how did you cope with them then?"
                    4 -> "What steps can you take to prioritize self-care and seek support during times of deep distress?"
                    else -> ""
                }
            }
        }
    }
}
