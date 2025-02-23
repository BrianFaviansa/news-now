package com.faviansa.newsnow.utils

import kotlin.random.Random

object Helper {
    fun generateRandomId(): Int {
        return Random.nextInt(1_000, Int.MAX_VALUE)
    }
}