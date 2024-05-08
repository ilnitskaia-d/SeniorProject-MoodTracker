package com.example.senproject.utils;

import com.example.senproject.data.MoodState
import com.example.senproject.data.models.MoodEntry;
import java.time.LocalDate
import java.time.temporal.ChronoUnit

class MoodEntryScatterGraph() {
    fun getY(moodState: MoodState): Float {
        return when (moodState) {
            MoodState.V_BAD -> 1f
            MoodState.BAD -> 2f
            MoodState.OK -> 3f
            MoodState.GOOD -> 4f
            MoodState.V_GOOD -> 5f
        }
    }

//    fun getX(/*last week entries*/moodEntries: List<Pair<LocalDate, MoodState>>): List<Float> {
//        //We need the list of x position, the horizontal position,
//        return res
//    }
}
