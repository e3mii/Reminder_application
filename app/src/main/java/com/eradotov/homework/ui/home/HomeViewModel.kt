package com.eradotov.homework.ui.home

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.Toast
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.work.*
import com.eradotov.homework.Graph
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.repository.UserRepository
import com.eradotov.homework.ui.MainActivity
import com.eradotov.homework.ui.home.userReminders.toDateString
import com.eradotov.homework.ui.login.LoginViewModel
import com.eradotov.homework.ui.login.LoginViewState
import com.eradotov.homework.util.NotificationWorker
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class HomeViewModel(
) : ViewModel() {

    private val _state = MutableStateFlow(ReminderOccurrencesState())
    val state: StateFlow<ReminderOccurrencesState>
        get() = _state

    val userRepository: UserRepository = Graph.userRepository

    init{
        createNotificationChannel(Graph.appContext)
    }
}

/*
 * NOTIFICATION - creating the channel which is required by android
 */
private fun createNotificationChannel(context: Context){
    if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val name = "NotificationChannelReminders"
        val descriptionText = "NotificationChannelRemindersDescriptionText"
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel("REMINDERS_CHANNEL_ID", name, importance).apply {
            description = descriptionText
        }
        //register the channel with the system
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}

data class ReminderOccurrencesState(
    var occurState: Boolean = true,
)
