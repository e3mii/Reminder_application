package com.eradotov.homework.ui.home.userReminders

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eradotov.homework.Graph.reminderRepository
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.util.viewModelProviderFactoryOf
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun UserReminders(
    activeUserUsername: String,
    modifier: Modifier = Modifier,
    navController: NavController
) {
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
        )
    }
}

@Composable
fun ListOfReminders(
    list: List<Reminder>,
    navController: NavController
){
    val inputDialogState = remember{ mutableStateOf(false)}
    var context = LocalContext.current
    LazyColumn(
        contentPadding = PaddingValues(0.dp),
        verticalArrangement = Arrangement.Center
    ){
        items(list){ item ->
            RemindersListItem(
                reminder = item,
                onClick = {
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
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current
    ConstraintLayout(modifier = modifier.clickable { onClick() }) {
        val (divider, rMessage, /*rContent,*/ icon, rTime,rCreationTime) = createRefs()
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
                reminder.rCreationTime == null -> { "Created on: ${reminder.rCreationTime.formatToString()}" }
                else -> "Created on: ${Date().formatToString()}"
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
            text = "Reminder set for: ${reminder.rTime}",
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
        IconButton(
            onClick = {
                Toast.makeText(context, "Deleting...", Toast.LENGTH_SHORT).show()
                coroutineScope.launch {
                    reminderRepository.deletingReminder(reminder)
                }
                 },
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