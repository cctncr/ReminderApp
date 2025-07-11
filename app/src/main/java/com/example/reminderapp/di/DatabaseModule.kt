package com.example.reminderapp.di

import android.content.Context
import androidx.room.Room
import com.example.reminderapp.data.local.dao.ReminderDao
import com.example.reminderapp.data.local.database.ReminderDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideReminderDatabase(
        @ApplicationContext context: Context
    ): ReminderDatabase {
        return Room.databaseBuilder(
            context,
            ReminderDatabase::class.java,
            "reminder_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideReminderDao(database: ReminderDatabase): ReminderDao {
        return database.reminderDao()
    }
}