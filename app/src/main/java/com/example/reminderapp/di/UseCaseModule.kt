package com.example.reminderapp.di

import com.example.reminderapp.domain.repository.ReminderRepository
import com.example.reminderapp.domain.usecases.CreateReminderUseCase
import com.example.reminderapp.domain.usecases.DeleteReminderUseCase
import com.example.reminderapp.domain.usecases.GetAllRemindersUseCase
import com.example.reminderapp.domain.usecases.GetReminderByIdUseCase
import com.example.reminderapp.domain.usecases.ReminderUseCases
import com.example.reminderapp.domain.usecases.ToggleReminderEnabledUseCase
import com.example.reminderapp.domain.usecases.UpdateReminderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object UseCaseModule {

    @Provides
    @ViewModelScoped
    fun provideReminderUseCases(repository: ReminderRepository): ReminderUseCases {
        return ReminderUseCases(
            getAllReminders = GetAllRemindersUseCase(repository),
            getReminderById = GetReminderByIdUseCase(repository),
            createReminder = CreateReminderUseCase(repository),
            updateReminder = UpdateReminderUseCase(repository),
            deleteReminder = DeleteReminderUseCase(repository),
            toggleReminderEnabled = ToggleReminderEnabledUseCase(repository)
        )
    }
}