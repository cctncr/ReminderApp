package com.example.reminderapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.models.Time
import com.example.reminderapp.utils.ReminderUtils

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey
    val id: String,
    val title: String?,
    val hour: Int,
    val minute: Int,
    val reminderType: String,
    val isEnabled: Boolean,
    val sortOrder: Int
) {
    fun toDomainModel(): Reminder {
        return Reminder(
            id = id,
            title = title,
            time = Time(hour, minute),
            reminderType = ReminderType.valueOf(reminderType),
            isEnabled = isEnabled
        )
    }

    companion object {
        fun fromDomainModel(reminder: Reminder): ReminderEntity {
            return ReminderEntity(
                id = reminder.id,
                title = reminder.title,
                hour = reminder.time.hour,
                minute = reminder.time.minute,
                reminderType = reminder.reminderType.name,
                isEnabled = reminder.isEnabled,
                sortOrder = ReminderUtils.timeToMinutes(reminder.time)
            )
        }
    }
}