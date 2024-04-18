package com.example.senproject.utils

import android.util.Log
import androidx.room.TypeConverter
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class Converters{
    companion object {
        @TypeConverter
        @JvmStatic
        fun toList(value: String): List<String> {
            return value.split(", ").map { it }
        }

        @TypeConverter
        @JvmStatic
        fun fromList(value: List<String>): String {
            return value.joinToString { it.toString() }
        }

        private var formatDate = DateTimeFormatter.ofPattern("dd.MM")

        @TypeConverter
        @JvmStatic
        fun toLocalDate(value: String): LocalDate {
            val day = value.substring(0, 2).toInt()
            val month = value.substring(3, 5).toInt()
            val date = LocalDate.of(0, month, day )
            return date
        }

        @TypeConverter
        @JvmStatic
        fun fromLocalDate(value: LocalDate): String {
            return value.format(formatDate)
        }

    }
}