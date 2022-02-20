package com.eradotov.homework.data.repository

import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.room.ReminderDao
import kotlinx.coroutines.flow.Flow

class ReminderRepository(
    private val reminderDao: ReminderDao
) {

    //remove old reminders
    fun updateOldReminders(rUserId: Long, currentDate: Long){
        return reminderDao.updateOldReminders(rUserId, currentDate)
    }

    //count occurred reminders
    fun getOccurredRemindersCount(rUserId: Long) : Int{
        return reminderDao.getOccurredRemindersCount(rUserId)
    }

    //update occurred reminders
    fun updateOccurredReminders(rUserId: Long, currentDate: Long){
        return reminderDao.updateOccurredReminders(rUserId, currentDate)
    }

    //load occurred users reminders
    fun occurredUsersReminders(rUserId: Long, currentDate: Long) : Flow<List<Reminder>>{
        return reminderDao.occurredRemindersFromUser(rUserId, currentDate)
    }

    //loading users reminders
    fun usersReminders(rUserId: Long) : Flow<List<Reminder>>{
        return reminderDao.remindersFromUser(rUserId)
    }

    //finding of reminder
    suspend fun findReminder(reminderId: Long) : Reminder{
        return reminderDao.findReminder(reminderId)
    }

    //update reminder
    suspend fun updateReminder(reminder: Reminder) = reminderDao.update(reminder)

    //deleting reminder
    suspend fun deletingReminder(reminder: Reminder?) = reminderDao.delete(reminder)

    //adding a new reminder
    suspend fun addReminder(reminder: Reminder) = reminderDao.insert(reminder)
}