package com.faviansa.newsnow.utils

sealed class ToastEvent {
    data class Show(val message: String) : ToastEvent()
}