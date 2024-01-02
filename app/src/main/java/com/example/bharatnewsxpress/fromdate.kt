package com.example.bharatnewsxpress

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class fromdate {
    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    val oneDayBeforeDate = currentDate.minusDays(1)

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateApi(): String {
        return oneDayBeforeDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
    }

}