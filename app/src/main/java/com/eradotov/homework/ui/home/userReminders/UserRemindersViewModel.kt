package com.eradotov.homework.ui.home.userReminders

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.compose.runtime.collectAsState
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.work.*
import com.eradotov.homework.Graph
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.entity.User
import com.eradotov.homework.data.repository.ReminderRepository
import com.eradotov.homework.data.repository.UserRepository
import com.eradotov.homework.ui.MainActivity
import com.eradotov.homework.ui.home.HomeViewModel
import com.eradotov.homework.ui.home.ReminderOccurrencesState
import com.eradotov.homework.util.NotificationWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class UserRemindersViewModel(
    private val activeUserUsername: String,
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    private val userRepository: UserRepository = Graph.userRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UserRemindersViewState())
    val state: StateFlow<UserRemindersViewState>
        get() = _state

    private val placeOfUse = "MainUse"

    init{
        /*
         * firstly are displayed the occurred reminders (that are happening on that day)
         */
        viewModelScope.launch {
            val activeUser: User = userRepository.getUser(activeUserUsername)
            setOneTimeNotification(activeUser.userId, placeOfUse)
            val sdf = SimpleDateFormat("dd-MM-yyyy")
            val currentDateInDate = sdf.format(Date())
            val currentDate = fromStringDateToLong(currentDateInDate)
            reminderRepository.occurredUsersReminders(activeUser.userId, currentDate).collect { list ->
                _state.value = UserRemindersViewState(
                    reminders = list
                )
            }
        }
    }

    /*
     * methods for regulating type of reminders that are displayed
     */
    suspend fun displayData(onlyOccurred: Boolean){
        val activeUser: User = userRepository.getUser(activeUserUsername)
        val sdf = SimpleDateFormat("dd-MM-yyyy")
        val currentDateInDate = sdf.format(Date())
        val currentDate = fromStringDateToLong(currentDateInDate)
        if(onlyOccurred){
            reminderRepository.occurredUsersReminders(activeUser.userId, currentDate).collect { list ->
                _state.value = UserRemindersViewState(
                    reminders = list
                )
            }
        } else {
            reminderRepository.usersReminders(activeUser.userId).collect { list ->
                _state.value = UserRemindersViewState(
                    reminders = list
                )
            }
        }
    }
}

//NOTIFICATION & WORK MANAGER PART
fun setOneTimeNotification(activeUserId: Long, placeOfUse: String){
    val workManager = WorkManager.getInstance(Graph.appContext)
    val constraints = Constraints.Builder()
        .setRequiredNetworkType(NetworkType.CONNECTED)
        .build()

    val data = workDataOf("ACTIVE_USER" to activeUserId)

    val notificationWorker = OneTimeWorkRequestBuilder<NotificationWorker>()
        .setConstraints(constraints)
        .setInputData(data)
        .build()

    workManager.enqueue(notificationWorker)

    //monitoring for state of work
    workManager.getWorkInfoByIdLiveData(notificationWorker.id)
        .observeForever{ workInfo ->
            val numberOfOccurredReminder = workInfo.outputData.getInt("NUM_OF_OCCURRED_REMINDERS", 0)
            if(workInfo.state == WorkInfo.State.SUCCEEDED){
                if(numberOfOccurredReminder == 0 && placeOfUse == "MainUse"){
                    freeReminderNotification()
                } else if(placeOfUse == "MainUse" || placeOfUse == "AddUse" && numberOfOccurredReminder != 0) {
                    occurredReminderNotification(numberOfOccurredReminder)
                }
            }
        }
}

private fun occurredReminderNotification(
    numberOfOccurredReminder: Int
){
    val resultIntent = Intent(Graph.appContext, MainActivity::class.java)
    val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(Graph.appContext).run {
        // Add the intent, which inflates the back stack
        addNextIntentWithParentStack(resultIntent)
        // Get the PendingIntent containing the entire back stack
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    val notificationId = 1
    val builder = NotificationCompat.Builder(Graph.appContext, "REMINDERS_CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_round_notifications_active_24)
        .setContentTitle("There are things to be done")
        .setContentText("Number of important reminders for today: $numberOfOccurredReminder")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setContentIntent(resultPendingIntent)
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId, builder.build())
    }
}

private fun freeReminderNotification(
){
    val notificationId = 2
    val builder = NotificationCompat.Builder(Graph.appContext, "REMINDERS_CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_round_notifications_none_24)
        .setContentTitle("Free day")
        .setContentText("You don't have important reminders set for today!")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId, builder.build())
    }
}

data class UserRemindersViewState(
    val reminders: List<Reminder> = emptyList()
)