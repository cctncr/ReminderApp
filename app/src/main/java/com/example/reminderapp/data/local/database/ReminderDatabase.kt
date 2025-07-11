package com.example.reminderapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminderapp.data.local.dao.ReminderDao
import com.example.reminderapp.data.local.entity.ReminderEntity

@Database(
    entities = [ReminderEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ReminderDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}