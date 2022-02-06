package com.eradotov.homework.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eradotov.homework.data.entity.Reminder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.*

class HomeViewModel : ViewModel() {
    private val _state = MutableStateFlow(HomeViewState())

    val state: StateFlow<HomeViewState>
        get() = _state

    init{
        val list = mutableListOf<Reminder>()
        for (x in 1..20){
            list.add(
                Reminder(
                    rId = x.toLong(),
                    rTitle = "Reminder $x",
                    rContent = "Programing awaits, WAKE UP",
                    rDate = Date()
                )
            )
        }

        viewModelScope.launch {
            _state.value = HomeViewState(
                reminderss = list
            )
        }
    }
}

data class HomeViewState(
    val reminderss: List<Reminder> = emptyList()
)