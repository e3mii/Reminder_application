package com.eradotov.homework.ui.reminder

import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.ViewModel
import com.eradotov.homework.Graph
import com.eradotov.homework.R
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.repository.ReminderRepository
import com.eradotov.homework.ui.MainActivity
import com.eradotov.homework.ui.home.userReminders.setOneTimeNotification
import com.eradotov.homework.ui.home.userReminders.toDateString

class ReminderViewModel(
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel(){

    private val placeOfUse = "AddUse"

    suspend fun saveReminder(reminder: Reminder): Long{
        createReminderNotification(reminder)
        setOneTimeNotification(reminder.rUserId, placeOfUse)
        return reminderRepository.addReminder(reminder)
    }

}

private fun createReminderNotification(
    reminder: Reminder
){
    val resultIntent = Intent(Graph.appContext, MainActivity::class.java)
    val resultPendingIntent: PendingIntent? = TaskStackBuilder.create(Graph.appContext).run {
        // Add the intent, which inflates the back stack
        addNextIntentWithParentStack(resultIntent)
        // Get the PendingIntent containing the entire back stack
        getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
    }
    val notificationId = 3
    val builder = NotificationCompat.Builder(Graph.appContext, "REMINDERS_CHANNEL_ID")
        .setSmallIcon(R.drawable.ic_baseline_new_releases_24)
        .setContentTitle("New reminder set for ${reminder.rTime.toDateString()}")
        .setContentText("Expand to view the message of reminder")
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setCategory(NotificationCompat.CATEGORY_REMINDER)
        .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        .setStyle(NotificationCompat.BigTextStyle().bigText(reminder.rMessage))
        .setContentIntent(resultPendingIntent)
        .setAutoCancel(true)
    with(NotificationManagerCompat.from(Graph.appContext)){
        notify(notificationId, builder.build())
    }
}