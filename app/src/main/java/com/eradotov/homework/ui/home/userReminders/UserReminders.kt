package com.eradotov.homework.ui.home.userReminders

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Schedule
import androidx.compose.material.icons.outlined.NotificationsActive
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.NotificationsActive
import androidx.compose.material.icons.rounded.NotificationsNone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.ui.home.HomeViewModel
import com.eradotov.homework.util.viewModelProviderFactoryOf
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserReminders(
    activeUserUsername: String,
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel,
) {
    /*
     * for access to the UserReminderViewModel where is reminders being get from db
     */
    val viewModel: UserRemindersViewModel = viewModel(
        key = "user_reminder_$activeUserUsername",
        factory = viewModelProviderFactoryOf { UserRemindersViewModel(activeUserUsername) }
    )
    val viewState by viewModel.state.collectAsState()

    Column(
        modifier = modifier
    ){
        ListOfReminders(
            list = viewState.reminders,
            navController = navController,
            homeViewModel = homeViewModel,
        )
    }
}

@Composable
fun ListOfReminders(
    list: List<Reminder>,
    navController: NavController,
    homeViewModel: HomeViewModel,
){

    /*
     * for the regulation of consistency of button view all and displayed reminders
     */
    val reminderOccurrencesState by homeViewModel.state.collectAsState()

    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(list){ item ->
            RemindersListItem(
                reminder = item,
                onClick = {
                    reminderOccurrencesState.occurState = true
                    navController.navigate(route = "editReminder/${item.rId}")
                },
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
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, rMessage, icon, rTime,rCreationTime) = createRefs()
        Divider(
            Modifier.constrainAs(divider) {
                top.linkTo(parent.top)
                centerHorizontallyTo(parent)
                width = Dimension.fillToConstraints
            }
        )
        //rCreationTime
        Text(
            text = when {
                reminder.rCreationTime != null -> { "Modified on: ${reminder.rCreationTime.toDateString()}" }
                else -> "Modified on: ${Date().formatToString()}"
            },
            maxLines = 1,
            style = MaterialTheme.typography.caption,
            modifier = Modifier.constrainAs(rCreationTime) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                centerHorizontallyTo(parent)
                top.linkTo(parent.top, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        // rMessage
        Text(
            text = reminder.rMessage,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.body1,
            modifier = Modifier.constrainAs(rMessage) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                top.linkTo(rCreationTime.bottom, margin = 10.dp)
                width = Dimension.preferredWrapContent
            }
        )
        //rTime
        Text(
            text = "Reminder set for: ${reminder.rTime.toDateString()}",
            maxLines = 1,
            style = MaterialTheme.typography.body2,
            modifier = Modifier.constrainAs(rTime) {
                linkTo(
                    start = parent.start,
                    end = icon.start,
                    startMargin = 24.dp,
                    endMargin = 16.dp,
                    bias = 0f
                )
                centerVerticallyTo(rMessage)
                top.linkTo(rMessage.bottom, 6.dp)
                bottom.linkTo(parent.bottom, 10.dp)
            }
        )
        // icon
        /*TODO-add color for the schedule icon red/yellow/green(importance)
              -(which replaces the delete icon)
        *     -delete is placed on press reminder (in editing part)*/
        if(reminder.toNotify){
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(6.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top, 10.dp)
                        bottom.linkTo(parent.bottom, 10.dp)
                        end.linkTo(parent.end)
                    },
                imageVector = Icons.Filled.NotificationsActive,
                contentDescription = stringResource(R.string.reminder_icon),
                tint = MaterialTheme.colors.primary
            )
        } else {
            Icon(
                modifier = Modifier
                    .size(40.dp)
                    .padding(6.dp)
                    .constrainAs(icon) {
                        top.linkTo(parent.top, 10.dp)
                        bottom.linkTo(parent.bottom, 10.dp)
                        end.linkTo(parent.end)
                    },
                imageVector = Icons.Outlined.NotificationsNone,
                contentDescription = stringResource(R.string.reminder_icon),
                tint = MaterialTheme.colors.primary
            )
        }

    }
}

/*
 * formatting methods
 */
fun Date.formatToString(): String {
    return SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(this)
}

fun Long.toDateString(): String {
    return SimpleDateFormat("dd MMMM, yyyy", Locale.getDefault()).format(Date(this))
}

fun fromStringDateToLong(stringDate: String): Long {
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    val date = formatter.parse(stringDate)
    return date.time
}