package com.example.reminderapp.data.repository

import com.example.reminderapp.data.local.dao.ReminderDao
import com.example.reminderapp.data.local.entity.ReminderEntity
import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao
) : ReminderRepository {

    override fun getAllReminders(): Flow<List<Reminder>> {
        return reminderDao.getAllReminders().map { entities ->
            entities.map { it.toDomainModel() }
        }
    }

    override suspend fun getReminderById(id: String): Reminder? {
        return reminderDao.getReminderById(id)?.toDomainModel()
    }

    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(ReminderEntity.fromDomainModel(reminder))
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(ReminderEntity.fromDomainModel(reminder))
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(ReminderEntity.fromDomainModel(reminder))
    }

    override suspend fun deleteReminderById(id: String) {
        reminderDao.deleteReminderById(id)
    }

    override suspend fun updateReminderEnabled(id: String, enabled: Boolean) {
        reminderDao.updateReminderEnabled(id, enabled)
    }
}