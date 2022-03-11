package com.eradotov.homework.ui

import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.eradotov.homework.HomeWorkAppState
import com.eradotov.homework.rememberHomeWorkAppState
import com.eradotov.homework.ui.home.Home
import com.eradotov.homework.ui.login.Login
import com.eradotov.homework.ui.maps.ReminderMap
import com.eradotov.homework.ui.maps.SearchRemindersMap
import com.eradotov.homework.ui.profile.Profile
import com.eradotov.homework.ui.registration.Registration
import com.eradotov.homework.ui.reminder.EditReminder
import com.eradotov.homework.ui.reminder.Reminder

@Composable
fun HomeWorkApp(
    appState: HomeWorkAppState = rememberHomeWorkAppState()
){
    NavHost(
        navController = appState.navController,
        startDestination = "login"
    ){
        composable(route = "login"){
            Login(navController = appState.navController)
        }

        composable(
            route = "home/{activeUserName}",
            arguments = listOf(
                navArgument("activeUserName"){
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("activeUserName")?.let{ username->
                Home(activeUserUsername = username, navController = appState.navController)
            }
        }

        composable(route = "profile") {
            Profile(onBackPress = appState::navigateBack)
        }

        composable(
            route = "reminder/{userId}",
            arguments = listOf(
                navArgument("userId"){
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("userId")?.let{ userId->
                Reminder(userId = userId, onBackPress = appState::navigateBack, navController = appState.navController)
            }
        }

        composable(
            route = "editReminder/{reminderId}",
            arguments = listOf(
                navArgument("reminderId"){
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("reminderId")?.let{ reminderId->
                EditReminder(reminderId = reminderId, onBackPress = appState::navigateBack, navController = appState.navController)
            }
        }

        composable(route = "registration") {
            Registration(onBackPress = appState::navigateBack)
        }

        composable(
            route = "map/{location}",
            arguments = listOf(
                navArgument("location"){
                    type = NavType.StringType
                }
            )
        ){ backStackEntry ->
            backStackEntry.arguments?.getString("location")?.let{ location ->
                ReminderMap(location = location, navController = appState.navController)
            }
        }

        composable(
            route = "searchMap/{userId}",
            arguments = listOf(
                navArgument("userId"){
                    type = NavType.LongType
                }
            )
        ) { backStackEntry ->
            backStackEntry.arguments?.getLong("userId")?.let{ userId->
                SearchRemindersMap(navController = appState.navController, userId = userId)
            }
        }
    }
}