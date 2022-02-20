package com.eradotov.homework.ui.reminder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eradotov.homework.Graph
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class EditReminderViewModel(
    private val reminderId: Long,
    private val reminderRepository: ReminderRepository = Graph.reminderRepository
) : ViewModel() {
    private val _state = MutableStateFlow(EditReminderViewState())
    val state: StateFlow<EditReminderViewState>
        get() = _state

    init {
        viewModelScope.launch {
            val reminderToEdit: Reminder = reminderRepository.findReminder(reminderId)
            _state.value = EditReminderViewState(reminder = reminderToEdit)
        }
    }
}

data class EditReminderViewState(
    val reminder: Reminder? = null
)