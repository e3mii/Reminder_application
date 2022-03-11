package com.eradotov.homework.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.entity.User

@Database(
    entities = [Reminder::class, User::class],
    version = 13,
    exportSchema = false
)
abstract class HomeWorkDatabase : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
    abstract fun userDao(): UserDao
}