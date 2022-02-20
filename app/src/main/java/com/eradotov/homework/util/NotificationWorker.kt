package com.eradotov.homework.util

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.eradotov.homework.Graph
import com.eradotov.homework.data.repository.ReminderRepository
import com.eradotov.homework.ui.home.userReminders.fromStringDateToLong
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class NotificationWorker(
    context: Context,
    userParameters: WorkerParameters,
) : CoroutineWorker(context, userParameters) {

    private val reminderRepository: ReminderRepository = Graph.reminderRepository
    private val activeUserId = inputData.getLong("ACTIVE_USER", 0)
    private val sdf = SimpleDateFormat("dd-MM-yyyy")
    private val currentDateInDate = sdf.format(Date())
    private val currentDate = fromStringDateToLong(currentDateInDate)
    var occurredReminders: Int = 0

    //this is where to do background processing
    override suspend fun doWork(): Result {
        return try {
            reminderRepository.updateOldReminders(activeUserId, currentDate)
            reminderRepository.updateOccurredReminders(activeUserId, currentDate)
            occurredReminders = reminderRepository.getOccurredRemindersCount(activeUserId)
            //Toast.makeText(Graph.appContext, "$occurredReminders", Toast.LENGTH_SHORT).show()
            val outputData = workDataOf("NUM_OF_OCCURRED_REMINDERS" to occurredReminders)
            Result.success(outputData)
        } catch (throwable: Throwable){
            Timber.e(throwable, "Error getting reminders")
            Result.failure()
        }
    }
}

