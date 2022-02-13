package com.eradotov.homework.ui.home

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.entity.User
import com.eradotov.homework.ui.home.userReminders.UserReminders
import com.eradotov.homework.util.viewModelProviderFactoryOf
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Home(
    activeUserUsername: String,
    viewModel: HomeViewModel = viewModel(),
    navController: NavController
){
    val context = LocalContext.current

    Surface(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            activeUser = activeUserUsername,
            navController = navController,
            viewModel = viewModel
        )
    }

}

@Composable
@SuppressLint("CoroutineCreationDuringComposition")
fun HomeContent(
    activeUser: String,
    navController: NavController,
    viewModel: HomeViewModel
) {
    val coroutineScope = rememberCoroutineScope()
    var user: User? = null
    coroutineScope.launch {
        user = viewModel.userRepository.getUser(activeUser)
    }
    Scaffold(
        modifier = Modifier.padding(bottom = 20.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "reminder/${user?.userId}") },
                contentColor = MaterialTheme.colors.primary,
                modifier = Modifier.padding(all = 20.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null
                )
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
