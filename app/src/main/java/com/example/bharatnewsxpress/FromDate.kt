package com.example.bharatnewsxpress

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class FromDate {
    @RequiresApi(Build.VERSION_CODES.O)
    private val formatter: DateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")

    @RequiresApi(Build.VERSION_CODES.O)
    val currentDate: LocalDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    val oneDayBeforeDate: LocalDate = currentDate.minusDays(1)

    @RequiresApi(Build.VERSION_CODES.O)
    fun dateApi(): String {
        return oneDayBeforeDate.format(formatter)
    }

}