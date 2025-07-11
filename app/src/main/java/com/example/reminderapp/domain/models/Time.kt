package com.example.reminderapp.domain.models

data class Time(
    val hour: Int, val minute: Int
) {
    override fun toString(): String {
        return hour.toString().padStart(2, '0') + ":" +
                minute.toString().padStart(2, '0')
    }
}