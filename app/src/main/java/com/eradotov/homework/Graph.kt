package com.eradotov.homework

import android.content.Context
import androidx.room.Room
import com.eradotov.homework.data.repository.ReminderRepository
import com.eradotov.homework.data.repository.UserRepository
import com.eradotov.homework.data.room.HomeWorkDatabase

object Graph {
    lateinit var database: HomeWorkDatabase

    val reminderRepository by lazy {
        ReminderRepository(
            reminderDao = database.reminderDao()
        )
    }

    val userRepository by lazy {
        UserRepository(
            userDao = database.userDao()
        )
    }

    fun provide(context: Context){
        database = Room.databaseBuilder(context, HomeWorkDatabase::class.java, "data.db")
            .fallbackToDestructiveMigration()
            .build()
    }
}