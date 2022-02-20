package com.eradotov.homework.data.room

import androidx.room.*
import com.eradotov.homework.data.entity.Reminder
import kotlinx.coroutines.flow.Flow

@Dao
abstract class ReminderDao {

    @Query("""
        UPDATE reminders SET reminderSeen = 0
        WHERE user_id = :rUserId AND rTime < :currentDate
    """)
    abstract fun updateOldReminders(rUserId: Long, currentDate: Long)

    @Query("""
        SELECT COUNT(reminderSeen) FROM reminders
        WHERE user_id = :rUserId AND reminderSeen = 1
        AND toNotify = 1
    """)
    abstract fun getOccurredRemindersCount(rUserId: Long): Int

    @Query("""
        UPDATE reminders SET reminderSeen = 1 
        WHERE user_id = :rUserId AND rTime = :currentDate
        AND toNotify = 1
        """)
    abstract fun updateOccurredReminders(rUserId: Long, currentDate: Long)

    @Query("SELECT * FROM reminders WHERE id = :reminderId")
    abstract suspend fun findReminder(reminderId: Long): Reminder

    @Query("""
        SELECT reminders.* FROM reminders 
        INNER JOIN users ON user_id = users.id
        WHERE user_id = :rUserId AND rTime = :currentDate
        """)
    abstract fun occurredRemindersFromUser(rUserId: Long, currentDate: Long): Flow<List<Reminder>>

    @Query("""
        SELECT reminders.* FROM reminders
        INNER JOIN users ON user_id = users.id
        WHERE user_id = :rUserId
    """)
    abstract fun remindersFromUser(rUserId: Long): Flow<List<Reminder>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun insert(entity: Reminder): Long

    @Update(onConflict = OnConflictStrategy.REPLACE)
    abstract suspend fun update(entity: Reminder)

    @Delete
    abstract suspend fun delete(entity: Reminder?): Int
}