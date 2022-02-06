package com.eradotov.homework.ui

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.eradotov.homework.HomeWorkAppState
import com.eradotov.homework.rememberHomeWorkAppState
import com.eradotov.homework.ui.home.Home
import com.eradotov.homework.ui.login.Login
import com.eradotov.homework.ui.profile.Profile
import com.eradotov.homework.ui.registration.Registration
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
        composable(route = "home"){
            Home(navController = appState.navController)
        }
        composable(route = "profile") {
            Profile(onBackPress = appState::navigateBack)
        }
        composable(route = "reminder") {
            Reminder(onBackPress = appState::navigateBack)
        }
        composable(route = "registration") {
            Registration(onBackPress = appState::navigateBack)
        }
    }
}