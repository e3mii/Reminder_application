package com.eradotov.homework.ui.home.userReminders

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
import kotlin.math.absoluteValue

class UserRemindersViewModel(
    private val activeUserUsername: String,
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
    private val userRepository: UserRepository = Graph.userRepository
) : ViewModel() {
    private val _state = MutableStateFlow(UserRemindersViewState())
    val state: StateFlow<UserRemindersViewState>
        get() = _state

    init{
        viewModelScope.launch {
            val activeUser: User = userRepository.getUser(activeUserUsername)
            reminderRepository.usersReminders(activeUser.userId).collect { list ->
                _state.value = UserRemindersViewState(
                    reminders = list
                )
            }
        }
    }
}

data class UserRemindersViewState(
    val reminders: List<Reminder> = emptyList()
)