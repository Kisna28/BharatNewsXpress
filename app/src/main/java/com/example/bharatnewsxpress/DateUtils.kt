package com.example.bharatnewsxpress

import java.text.SimpleDateFormat
import java.util.Locale

object DateUtils {
    fun formatApiDate(apiDateString: String): String {
        val apiDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
        val outputDateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())

        try {
            val date = apiDateFormat.parse(apiDateString)
            return outputDateFormat.format(date)
        } catch (e: Exception) {
            e.printStackTrace()
            return apiDateString // Return the original string if parsing fails
        }
    }
}