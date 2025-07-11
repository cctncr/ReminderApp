package com.example.reminderapp.data.mapper

import com.example.reminderapp.data.local.entity.ReminderEntity
import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.models.Time
import com.example.reminderapp.utils.ReminderUtils
import javax.inject.Inject

class ReminderEntityMapper @Inject constructor() {

    fun toEntity(reminder: Reminder): ReminderEntity {
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

    fun toDomain(entity: ReminderEntity): Reminder {
        return Reminder(
            id = entity.id,
            title = entity.title,
            time = Time(entity.hour, entity.minute),
            reminderType = ReminderType.valueOf(entity.reminderType),
            isEnabled = entity.isEnabled
        )
    }
}