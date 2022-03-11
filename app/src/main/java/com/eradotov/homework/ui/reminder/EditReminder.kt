package com.eradotov.homework.ui.reminder

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Map
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.eradotov.homework.Graph
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.repository.ReminderRepository
import com.eradotov.homework.ui.home.HomeViewModel
import com.eradotov.homework.ui.home.userReminders.toDateString
import com.eradotov.homework.util.viewModelProviderFactoryOf
import com.google.accompanist.insets.systemBarsPadding
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun EditReminder(
    reminderId: Long,
    onBackPress: () -> Unit,
    navController: NavController
) {
    val reminderRepository: ReminderRepository = Graph.reminderRepository
    var context = LocalContext.current

    /*
     * viewModel for db - updating and deleting
     */
    val editReminderViewModel: EditReminderViewModel = viewModel(
        key = "edit_reminder_reminderId_$reminderId",
        factory = viewModelProviderFactoryOf { EditReminderViewModel(reminderId) }
    )
    val editReminderViewState by editReminderViewModel.state.collectAsState()

    /*
     * viewModel for gadgets - calender
     */
    val gadgets: Gadgets = viewModel(
        key = "edit_reminder_context_$context",
        factory = viewModelProviderFactoryOf { Gadgets(context) }
    )
    val gadgetsViewState by gadgets.state.collectAsState()

    var selectedReminder = editReminderViewState.reminder
    val rMessage = rememberSaveable { mutableStateOf("") }
    val rLocationX = rememberSaveable { mutableStateOf("") }
    val rLocationY = rememberSaveable { mutableStateOf("") }
    val rTime = rememberSaveable { mutableStateOf("") }
    val rCreationTime = rememberSaveable { mutableStateOf("") }
    val reminderSeen = rememberSaveable { mutableStateOf("") }
    var date = rememberSaveable { mutableStateOf("")}
    var checkedState = rememberSaveable{ mutableStateOf(true) }

    val latlng = navController
        .currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<LatLng>("location_data")
        ?.value

    date.value = selectedReminder?.rTime?.toDateStringForEdit().toString()
    rMessage.value = selectedReminder?.rMessage.toString()
    rTime.value = selectedReminder?.rTime.toString()
    checkedState.value = selectedReminder?.toNotify == true

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
                    onClick = {
                        /*TODO - this is just temporary solution, because I could not update
                        *        the "view" button after only "onBackPress" so I "update"
                        *        db with same data so that screen could refresh it self
                        *        (I tried so many things...)*/
                        val reminder = Reminder(
                            rId = selectedReminder!!.rId,
                            rUserId = selectedReminder?.rUserId!!.toLong(),
                            rMessage = selectedReminder.rMessage,
                            rLocationX = selectedReminder.rLocationX,
                            rLocationY = selectedReminder.rLocationY,
                            rTime = selectedReminder.rTime,
                            rCreationTime = selectedReminder.rCreationTime,
                            rSeen = selectedReminder.rSeen,
                            toNotify = selectedReminder.toNotify
                        )
                        GlobalScope.launch {
                            reminderRepository.updateReminder(reminder)
                        }
                        onBackPress()
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(
                    text = "Edit reminder",
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
                    maxLines = 8,
                    onValueChange = {data->rMessage.value=data},
                    label = { Text(text = "Reminder message") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    if(latlng == null){
                        OutlinedTextField(
                            value = "${selectedReminder?.rLocationX}, ${selectedReminder?.rLocationY}",
                            enabled = false,
                            onValueChange = {},
                            label = { Text(text = "Where you need to be") }
                        )
                    } else {
                        OutlinedTextField(
                            value = "${latlng.latitude}, ${latlng.longitude}",
                            enabled = false,
                            onValueChange = {},
                            label = { Text(text = "Where you need to be") }
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    OutlinedButton(
                        onClick = {
                            navController.navigate("map/${selectedReminder?.rLocationX},${selectedReminder?.rLocationY}")
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(45.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Filled.Map,
                            contentDescription = stringResource(R.string.map),
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = {
                        gadgets.DisplayCalendar()
                              },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp)
                ) {
                    if(gadgetsViewState.date == ""){
                        Text(text = "Change date:${date.value}")
                    } else {
                        Text(text = "Change date:${gadgetsViewState.date}")
                    }
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
                        if(rMessage.value == ""){
                            Toast.makeText(Graph.appContext, "Missing reminder's message...", Toast.LENGTH_SHORT).show()
                        } else {
                            if(gadgetsViewState.date != ""){
                                date.value = gadgetsViewState.date
                            }
                            if(latlng == null){
                                val reminder = Reminder(
                                    rId = selectedReminder!!.rId,
                                    rUserId = selectedReminder?.rUserId!!.toLong(),
                                    rMessage = rMessage.value,
                                    rLocationX = selectedReminder.rLocationX,
                                    rLocationY = selectedReminder.rLocationY,
                                    rTime = fromStringDateToLong(date.value),
                                    rCreationTime = Date().time,
                                    rSeen = selectedReminder.rSeen,
                                    toNotify = checkedState.value
                                )
                                GlobalScope.launch {
                                    reminderRepository.updateReminder(reminder)
                                }
                                onBackPress()
                            } else {
                                val reminder = Reminder(
                                    rId = selectedReminder!!.rId,
                                    rUserId = selectedReminder?.rUserId!!.toLong(),
                                    rMessage = rMessage.value,
                                    rLocationX = latlng.latitude,
                                    rLocationY = latlng.longitude,
                                    rTime = fromStringDateToLong(date.value),
                                    rCreationTime = Date().time,
                                    rSeen = selectedReminder.rSeen,
                                    toNotify = checkedState.value
                                )
                                GlobalScope.launch {
                                    reminderRepository.updateReminder(reminder)
                                }
                                onBackPress()
                            }
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp)
                ) {
                    Text("Change")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        GlobalScope.launch {
                            reminderRepository.deletingReminder(selectedReminder)
                        }
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp)
                ) {
                    Text("Delete")
                }
            }
        }
    }
}

/*
 * formatting method
 */
fun Long.toDateStringForEdit(): String {
    return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date(this))
}