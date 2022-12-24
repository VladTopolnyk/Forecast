package com.vladtop.forecast_test_task.data.mappers

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    @SuppressLint("SimpleDateFormat")
    fun toDayDateFormat(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-mm-dd")
        val outputFormat = SimpleDateFormat("dd MMM")
        val date1 = inputFormat.parse(date)
        return outputFormat.format(date1 as Date)
    }

    fun toHourDateFormat(date: String): String {
        return date.substring(date.lastIndex - 5)
    }
}