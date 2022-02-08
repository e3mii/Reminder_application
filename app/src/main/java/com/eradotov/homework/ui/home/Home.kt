package com.eradotov.homework.ui.home

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionContext
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun Home(
    navController: NavController
){
    Surface(modifier = Modifier.fillMaxSize()) {
        HomeContent(
            navController = navController
        )
    }

}

@Composable
fun HomeContent(
    navController: NavController,
) {
    Scaffold(
        modifier = Modifier.padding(bottom = 20.dp),
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(route = "reminder") },
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

            ListOfReminders(
                modifier = Modifier.fillMaxSize()
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

@Composable
fun ListOfReminders(
    modifier: Modifier = Modifier
){
    val viewModel: HomeViewModel = viewModel()
    val viewState by viewModel.state.collectAsState()

    Column(
        modifier = modifier
    ) {
        RemindersList(
            list = viewState.reminderss
        )
    }
}

@Composable
private fun RemindersList(
    list: List<Reminder>
){
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(list){ item ->
            RemindersListItem(
                reminder = item,
                onClick = {},
                modifier = Modifier.fillParentMaxWidth()
            )
        }
    }
}

@Composable
private fun RemindersListItem(
    reminder: Reminder,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
){
    val context = LocalContext.current

    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, rTitle, rContent, icon, rDate) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )

        // rTitle
        Text(
            text = reminder.rTitle,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle1,
            modifier = Modifier.constrainAs(rTitle) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // rContent
        Text(
            text = reminder.rContent,
            maxLines = 1,
            style = MaterialTheme.typography.subtitle2,
            modifier = Modifier.constrainAs(rContent) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 8.dp,
                    bias = 0f
                )
                top.linkTo(rTitle.bottom, margin = 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
                width = Dimension.preferredWrapContent
            }
        )

        // date
        Text(
            text = when {
                reminder.rDate != null -> { reminder.rDate.formatToString() }
                else -> Date().formatToString()
            },
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(rDate) {
                linkTo(
                    start = rContent.end,
                    end = icon.start,
                    startMargin = 8.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                centerVerticallyTo(rContent)
                top.linkTo(rTitle.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )

        // icon
        IconButton(
            onClick = { Toast.makeText(context, "Under construction...will delete reminder", Toast.LENGTH_SHORT).show() },
            modifier = Modifier
                .size(50.dp)
                .padding(6.dp)
                .constrainAs(icon) {
                    top.linkTo(parent.top, 10.dp)
                    bottom.linkTo(parent.bottom, 10.dp)
                    end.linkTo(parent.end)
                }
        ) {
            Icon(
                imageVector = Icons.Filled.Delete,
                contentDescription = stringResource(R.string.reminder_icon),
                tint = MaterialTheme.colors.primary
            )
        }
    }
}

private fun Date.formatToString(): String {
    return SimpleDateFormat("MMMM dd, yyyy", Locale.getDefault()).format(this)
}
