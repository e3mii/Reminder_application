package com.eradotov.homework.ui.reminder

import android.app.DatePickerDialog
import android.content.Context
import android.widget.CalendarView
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding
import java.util.*

@Composable
fun Reminder(
    onBackPress: () -> Unit
){
    val context = LocalContext.current

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
                    value = "under construction...",
                    enabled = false,
                    onValueChange = {},
                    label = { Text(text = "Title of reminder")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = "under construction...",
                    enabled = false,
                    onValueChange = {},
                    label = { Text(text = "Description")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    value = "under construction...",
                    enabled = false,
                    onValueChange = {},
                    label = { Text(text = "Set date")},
                    modifier = Modifier.fillMaxWidth()
                )
                Spacer(modifier = Modifier.height(10.dp))
                Button(
                    onClick = { Toast.makeText(context, "Under construction...will add reminder", Toast.LENGTH_SHORT).show() },
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