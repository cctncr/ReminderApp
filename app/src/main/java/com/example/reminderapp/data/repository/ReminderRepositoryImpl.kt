package com.example.reminderapp.data.repository

import com.example.reminderapp.data.local.dao.ReminderDao
import com.example.reminderapp.data.mapper.ReminderEntityMapper
import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.repository.ReminderRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ReminderRepositoryImpl @Inject constructor(
    private val reminderDao: ReminderDao,
    private val entityMapper: ReminderEntityMapper
) : ReminderRepository {

    override fun getAllReminders(): Flow<List<Reminder>> {
        return reminderDao.getAllReminders().map { entities ->
            entities.map { entityMapper.toDomain(it) }
        }
    }

    override suspend fun getReminderById(id: String): Reminder? {
        return reminderDao.getReminderById(id)?.let { entityMapper.toDomain(it) }
    }

    override suspend fun insertReminder(reminder: Reminder) {
        reminderDao.insertReminder(entityMapper.toEntity(reminder))
    }

    override suspend fun updateReminder(reminder: Reminder) {
        reminderDao.updateReminder(entityMapper.toEntity(reminder))
    }

    override suspend fun deleteReminder(reminder: Reminder) {
        reminderDao.deleteReminder(entityMapper.toEntity(reminder))
    }

    override suspend fun deleteReminderById(id: String) {
        reminderDao.deleteReminderById(id)
    }

    override suspend fun updateReminderEnabled(id: String, enabled: Boolean) {
        reminderDao.updateReminderEnabled(id, enabled)
    }
}