package com.eradotov.homework.data.repository

import com.eradotov.homework.data.entity.User
import com.eradotov.homework.data.room.UserDao

class UserRepository(
    private val userDao: UserDao
) {
    suspend fun validateLogin(username: String, password: String) : User? = userDao.valideteUser(username, password)

    suspend fun getUser(username: String) : User = userDao.getUserWithUsername(username)

    suspend fun addUser(user: User) = userDao.insert(user)
}