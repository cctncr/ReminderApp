package com.example.reminderapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.reminderapp.navigation.Screen
import com.example.reminderapp.presentation.screens.createedit.CreateEditReminderScreen
import com.example.reminderapp.presentation.screens.main.MainScreen
import com.example.reminderapp.presentation.screens.main.ReminderViewModel
import com.example.reminderapp.ui.theme.ReminderAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val reminderViewModel: ReminderViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReminderAppTheme {
                val navController = rememberNavController()
                ReminderNavHost(
                    navController = navController,
                    reminderViewModel = reminderViewModel
                )
            }
        }
    }
}

@Composable
fun ReminderNavHost(
    navController: NavHostController,
    reminderViewModel: ReminderViewModel
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Main.route
    ) {
        composable(route = Screen.Main.route) {
            MainScreen(
                viewModel = reminderViewModel,
                onNavigateToCreateReminder = {
                    navController.navigate(Screen.CreateReminder.route)
                },
                onNavigateToEditReminder = { reminderId ->
                    navController.navigate(Screen.EditReminder.createRoute(reminderId))
                }
            )
        }

        composable(route = Screen.CreateReminder.route) {
            CreateEditReminderScreen(
                reminderViewModel = reminderViewModel,
                reminderId = null,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.EditReminder.route,
            arguments = Screen.EditReminder.arguments
        ) { backStackEntry ->
            val reminderId = backStackEntry.arguments?.getString("reminderId")
            CreateEditReminderScreen(
                reminderViewModel = reminderViewModel,
                reminderId = reminderId,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}