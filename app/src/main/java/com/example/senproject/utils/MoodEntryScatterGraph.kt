package com.example.senproject.utils;

import com.example.senproject.data.MoodState
import com.example.senproject.data.models.MoodEntry;
import com.example.senproject.utils.Utilities.getTodayDate
import java.time.Duration
import java.time.LocalDate
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

    fun reSort(list: List<MoodEntry>) : List<Pair<LocalDate, MoodEntry>> {
        var result = emptyList<Pair<LocalDate, MoodEntry>>()

        val today = getTodayDate()
        val firstDay = today.minusDays(7)

        var currentDate = firstDay
        var count: Int = 0
        var averageMood: Int = 0
//        for (entry in list) {
//
//        }

        return result
    }

    fun getX(moodEntries: List<MoodEntry>): List<Float> {
        /*
        * Get map by the date? Get counted by the date
        * the date is the last 7 day
        */
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
