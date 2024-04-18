package com.example.senproject.utils;

import com.example.senproject.data.MoodState
import com.example.senproject.data.models.MoodEntry;
import java.time.Duration
import java.time.temporal.ChronoUnit

class MoodEntryScatterGraph() {
    fun getY(moodEntry:MoodEntry): Float {
        var height = 0f
        height = when (moodEntry.moodState) {
            MoodState.V_BAD -> 1f
            MoodState.BAD -> 2f
            MoodState.OK -> 3f
            MoodState.GOOD -> 4f
            MoodState.V_GOOD -> 5f
        }
        return height
    }

    fun getX(moodEntries: List<MoodEntry>): List<Float> {
        moodEntries.reversed()
        val earliest = moodEntries.first().day
        val latest = moodEntries.last().day
        val diff = ChronoUnit.DAYS.between(earliest, latest).toFloat()
        //(Duration.between(earliest, latest))

        val res = moodEntries.map {
            (ChronoUnit.DAYS.between(earliest, it.day).toFloat() / diff)
        } as ArrayList<Float>

        return res
    }
}
