package com.eradotov.homework

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class HomeWorkAppState(
    val navController: NavHostController
) {
    fun navigateBack(){
        navController.popBackStack()
    }
}

@Composable
fun rememberHomeWorkAppState(
    navController: NavHostController = rememberNavController()
) = remember(navController){
    HomeWorkAppState(navController)
}