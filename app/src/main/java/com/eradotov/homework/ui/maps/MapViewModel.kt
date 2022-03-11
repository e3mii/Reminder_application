package com.eradotov.homework.ui.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eradotov.homework.Graph
import com.eradotov.homework.data.entity.Reminder
import com.eradotov.homework.data.repository.ReminderRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class MapViewModel(
    private val userId: Long,
    private val reminderRepository: ReminderRepository = Graph.reminderRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(MapViewState())
    val state: StateFlow<MapViewState>
        get() = _state

    init{
        viewModelScope.launch {
            reminderRepository.usersReminders(userId).collect { list ->
                _state.value = MapViewState(
                    reminders = list
                )
            }
        }
    }
}

data class MapViewState(
    val reminders: List<Reminder> = emptyList()
)