package com.example.marvel

import java.text.SimpleDateFormat
import java.util.*

object DateUtil {

    fun dateToString(date: Date): String {
        val df = SimpleDateFormat("dd.MM.yyyy")
        return df.format(date)
    }

    fun dateToDateRange(date: Date): String {
        val df = SimpleDateFormat("YYYY-MM-dd")
        println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@ ${df.format(date)}")
        return df.format(date)
    }

}