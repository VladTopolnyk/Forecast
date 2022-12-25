package com.vladtop.forecast_test_task.data.mappers

import android.annotation.SuppressLint
import android.util.Log
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    @SuppressLint("SimpleDateFormat")
    fun toDayDateFormat(date: String): String {
        val inputFormat = SimpleDateFormat("yyyy-mm-dd")
        val outputFormat = SimpleDateFormat("dd MMM")
        val date1 = inputFormat.parse(date)
        val outputDate = outputFormat.format(date1 as Date)
        Log.e("day date", outputDate)
        return outputFormat.format(date1)
    }

    fun toHourDateFormat(date: String): String {
        val outputDate = date.substring(date.lastIndex - 5)
        Log.e("hour date", outputDate)
        return outputDate
    }
}