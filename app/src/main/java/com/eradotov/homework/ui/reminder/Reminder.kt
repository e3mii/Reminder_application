package com.eradotov.homework.ui.reminder


import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eradotov.homework.Graph
import com.eradotov.homework.data.repository.ReminderRepository
import com.eradotov.homework.data.entity.Reminder
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.launch
import com.eradotov.homework.util.viewModelProviderFactoryOf
import kotlinx.coroutines.GlobalScope
import java.text.SimpleDateFormat
import java.util.*

/*TODO-ADD ICON PICKER OR PHOTO TAKER
*     -OR THE IMPORTANCE CHOOSER - connected with colors red/yellow/green
*     -ADD EMPTY REMINDER RESTRICTION HANDLER*/
@Composable
fun Reminder(
    userId: Long,
    onBackPress: () -> Unit,
    reminderViewModel: ReminderViewModel = viewModel()
){
    var context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()

    /*
     * viewModel for gadgets - calendar
     */
    val gadgetsViewModel: Gadgets = viewModel(
        key = "reminder_context_$context",
        factory = viewModelProviderFactoryOf { Gadgets(context) }
    )
    val gadgetsViewState by gadgetsViewModel.state.collectAsState()

    val rMessage = rememberSaveable { mutableStateOf("") }
    val rLocationX = rememberSaveable { mutableStateOf("") }
    val rLocationY = rememberSaveable { mutableStateOf("") }
    val rTime = rememberSaveable { mutableStateOf("") }
    val rCreationTime = rememberSaveable { mutableStateOf("") }
    val reminderSeen = rememberSaveable { mutableStateOf("") }
    val checkedState = rememberSaveable { mutableStateOf(true) }

    Surface{
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f),
            ){
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(
                    text = "Add reminder",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier.padding(16.dp)
            ) {
                OutlinedTextField(
                    value = rMessage.value,
                    enabled = true,
                    maxLines = 4,
                    onValueChange = {data->rMessage.value=data},
                    label = { Text(text = "Reminder message")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = "Location of reminder...",
                    enabled = false,
                    onValueChange = {},
                    label = { Text(text = "Description")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        gadgetsViewModel.DisplayCalendar()
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp)
                ) {
                    Text(text = "Pick date:${gadgetsViewState.date}")
                }
                Spacer(modifier = Modifier.height(20.dp))
                Row {
                    Text(text = "Reminder notification")
                    Spacer(modifier = Modifier.width(5.dp))
                    Switch(
                        checked = checkedState.value,
                        onCheckedChange = { checkedState.value = it },
                        colors = SwitchDefaults.colors(MaterialTheme.colors.primary)
                    )
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val reminder = Reminder(
                            rUserId = userId,
                            rMessage = rMessage.value,
                            rTime = fromStringDateToLong(gadgetsViewState.date),
                            rCreationTime = Date().time,
                            rSeen = false,
                            toNotify = checkedState.value
                        )
                        coroutineScope.launch {
                            reminderViewModel.saveReminder(reminder)
                        }
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp)
                ) {
                    Text("Add")
                }
            }
        }
    }
}

/*
 * method for transforming string date to long with 00:00:00 time
 * used for db comparison which reminder is due
 */
fun fromStringDateToLong(stringDate: String): Long {
    val formatter = SimpleDateFormat("dd-MM-yyyy")
    val date = formatter.parse(stringDate)
    return date.time
}