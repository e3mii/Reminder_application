package com.eradotov.homework.ui.reminder

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.eradotov.homework.Graph
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.repository.ReminderRepository
import com.google.accompanist.insets.systemBarsPadding
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun EditReminder(
    reminderId: Long,
    onBackPress: () -> Unit,
) {
    var context = LocalContext.current

    /*TODO-SWITCH TO VIEW*/
    val reminderRepository: ReminderRepository = Graph.reminderRepository
    val coroutineScope = rememberCoroutineScope()
    var selectedReminder: Reminder? = null

    GlobalScope.launch {
        selectedReminder = reminderRepository.findReminder(reminderId)
    }


    /*TODO-EXCLUDE TO OWN CLASS/FUN*/
    //CALENDAR
    val year: Int
    val month: Int
    val day: Int

    val calendar = Calendar.getInstance()
    year = calendar.get(Calendar.YEAR)
    month = calendar.get(Calendar.MONTH)
    day = calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    val date = remember { mutableStateOf("") }
    val datePickerDialog = DatePickerDialog(
        context,
        { _: DatePicker, year: Int, month: Int, dayOfMonth: Int->
            date.value = "$dayOfMonth.$month.$year"
        }, year, month, day
    )

    date.value = selectedReminder?.rTime.toString()
    val rMessage = rememberSaveable { mutableStateOf("") }
    val rLocationX = rememberSaveable { mutableStateOf("") }
    val rLocationY = rememberSaveable { mutableStateOf("") }
    val rTime = rememberSaveable { mutableStateOf("") }
    val rCreationTime = rememberSaveable { mutableStateOf("") }
    val reminderSeen = rememberSaveable { mutableStateOf("") }

    rMessage.value = selectedReminder?.rMessage.toString()
    rTime.value = selectedReminder?.rTime.toString()

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
                OutlinedTextField(
                    value = "Location of reminder...",
                    enabled = false,
                    onValueChange = {},
                    label = { Text(text = "Description") },
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedButton(
                    onClick = { datePickerDialog.show()},
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp)
                ) {
                    Text(text = "Change date:${date.value}")
                }
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = {
                        val reminder = Reminder(
                            rId = selectedReminder!!.rId,
                            rUserId = selectedReminder?.rUserId!!.toLong(),
                            rMessage = rMessage.value,
                            rTime = date.value,
                            rCreationTime = Date().time
                        )
                        GlobalScope.launch {
                            reminderRepository.updateReminder(reminder)
                        }
                        onBackPress()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp)
                ) {
                    Text("Change")
                }
            }
        }
    }
}