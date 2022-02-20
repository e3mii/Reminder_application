package com.eradotov.homework.ui.home

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.User
import com.eradotov.homework.ui.home.userReminders.UserReminders
import com.eradotov.homework.ui.home.userReminders.UserRemindersViewModel
import com.eradotov.homework.util.viewModelProviderFactoryOf
import kotlinx.coroutines.launch

@Composable
fun Home(
    activeUserUsername: String,
    homeViewModel: HomeViewModel = viewModel(),
    navController: NavController
){
    Surface(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            activeUser = activeUserUsername,
            navController = navController,
            homeViewModel = homeViewModel,
        )
    }

}

@Composable
@SuppressLint("CoroutineCreationDuringComposition")
fun HomeContent(
    activeUser: String,
    navController: NavController,
    homeViewModel: HomeViewModel,
) {
    /*
     * for the access to the displayData method
     */
    val remindersViewModel: UserRemindersViewModel = viewModel(
        key = "user_reminder_${activeUser}",
        factory = viewModelProviderFactoryOf { UserRemindersViewModel(activeUser) }
    )
    val remindersViewState by remindersViewModel.state.collectAsState()

    /*
     * for regulating state of button and displayed reminders from one place, that is from
     * homeViewModel
     */
    val reminderOccurrencesState by homeViewModel.state.collectAsState()
    val onlyOccurred = rememberSaveable{ mutableStateOf( true ) }
    val coroutineScope = rememberCoroutineScope()
    var user: User? = null
    coroutineScope.launch {
        user = homeViewModel.userRepository.getUser(activeUser)
    }

    Scaffold(
        modifier = Modifier.padding(bottom = 20.dp),
        floatingActionButton = {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                FloatingActionButton(
                    onClick = {
                        onlyOccurred.value = !onlyOccurred.value
                        reminderOccurrencesState.occurState = onlyOccurred.value
                        coroutineScope.launch {
                            remindersViewModel.displayData(reminderOccurrencesState.occurState)
                        }
                              },
                    contentColor = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(start = 20.dp, top = 20.dp, end = 20.dp)
                ) {
                    onlyOccurred.value = reminderOccurrencesState.occurState
                    if(onlyOccurred.value){
                        Icon(
                            imageVector = Icons.Default.Visibility,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.VisibilityOff,
                            contentDescription = null
                        )
                    }
                }
                FloatingActionButton(
                    onClick = {
                        reminderOccurrencesState.occurState = true
                        navController.navigate(route = "reminder/${user?.userId}")
                              },
                    contentColor = MaterialTheme.colors.primary,
                    modifier = Modifier.padding(all = 20.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null
                    )
                }
            }
        }
    ) {
        Column(
            modifier = Modifier
                .systemBarsPadding()
                .fillMaxWidth()
        ) {
            val appBarColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f)

            HomeAppBar(
                backgroundColor = appBarColor,
                navController = navController
            )

            UserReminders(
                modifier = Modifier.fillMaxSize(),
                activeUserUsername = activeUser,
                navController = navController,
                homeViewModel = homeViewModel,
            )
        }
    }
}

@Composable
private fun HomeAppBar(
    backgroundColor: Color,
    navController: NavController
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                color = MaterialTheme.colors.primary,
                modifier = Modifier
                    .padding(start = 4.dp)
                    .heightIn(max = 24.dp)
            )
        },
        backgroundColor = backgroundColor,
        actions = {
            IconButton( onClick = { navController.navigate("profile") } ) {
                Icon(
                    imageVector = Icons.Filled.AccountCircle,
                    contentDescription = stringResource(R.string.account),
                    tint = MaterialTheme.colors.primary
                )
            }
            /*TODO: 1) fix logout for back press
            *       2) display alert box logout/cancel*/
            /*TODO: 1) potential fix navigation.clearStack or something similar (need to try)*/
            IconButton(
                onClick = {
                    navController.navigate("login")
                }
            ) {
                Icon(
                    imageVector = Icons.Filled.Logout,
                    contentDescription = stringResource(R.string.logout),
                    tint = MaterialTheme.colors.primary
                )
            }
        }
    )
}
