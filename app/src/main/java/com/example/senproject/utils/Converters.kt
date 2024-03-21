package com.example.senproject.utils

import androidx.room.TypeConverter

class Converters{
    companion object {
        @TypeConverter
        @JvmStatic
        fun toList(value: String):List<String> {
            return value.split(", ").map { it }
        }

        @TypeConverter
        @JvmStatic
        fun fromList(value: List<String>): String {
            return value.joinToString { it.toString() }
        }
    }
}