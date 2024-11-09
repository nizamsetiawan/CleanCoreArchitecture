package com.example.app.dicodingeventsapp.presentation.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun dataFormatter(dateTime: String): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy, hh:mm a", Locale.getDefault())

    return try {
        val date: Date = inputFormat.parse(dateTime) ?: Date()
        outputFormat.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        dateTime
    }
}
