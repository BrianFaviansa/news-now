package com.faviansa.newsnow.utils

import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.random.Random

object Helper {
    fun generateRandomId(): Int {
        return Random.nextInt(1_000, Int.MAX_VALUE)
    }

    fun formatCardDate(isoDate: String): String {
        val zonedDateTime = ZonedDateTime.parse(isoDate)

        val formatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale.getDefault())

        return zonedDateTime.format(formatter)
    }
}