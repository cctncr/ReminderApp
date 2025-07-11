package com.example.reminderapp.presentation.navigation

import androidx.navigation.NavType
import androidx.navigation.navArgument

sealed class Screen(val route: String) {
    data object Main : Screen("main")
    data object CreateReminder : Screen("create_reminder")
    data object EditReminder : Screen("edit_reminder/{reminderId}") {
        fun createRoute(reminderId: String) = "edit_reminder/$reminderId"

        val arguments = listOf(
            navArgument("reminderId") { type = NavType.StringType }
        )
    }
}