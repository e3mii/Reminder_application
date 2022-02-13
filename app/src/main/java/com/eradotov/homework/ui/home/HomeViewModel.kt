package com.eradotov.homework.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eradotov.homework.Graph
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.entity.User
import com.eradotov.homework.data.repository.ReminderRepository
import com.eradotov.homework.data.repository.UserRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel(
    //private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    //private val userRepository: UserRepository = Graph.userRepository
) : ViewModel() {
    val userRepository: UserRepository = Graph.userRepository
    /*init{
        loadUserFromDb()
    }

    private fun loadUserFromDb(){
        val user = User(
            firstName = "Radi",
            lastName = "Radic",
            username = "radic123",
            mail = "radic@gmail.com",
            phoneNumber = "+385993209168",
            address = "Helsinki, address..."
        )
        viewModelScope.launch {
            userRepository.addUser(user)
        }
    }*/

    /*suspend fun getActiveUser(activeUser: String): User?{
        return userRepository.getUser(activeUser)
    }*/

}