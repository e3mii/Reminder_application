package com.eradotov.homework.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class LoginViewModel(
    private val activeUserUsername: String
) : ViewModel(){
    private val _state = MutableStateFlow(LoginViewState())
    val state: StateFlow<LoginViewState>
        get() = _state

    init {
        viewModelScope.launch {
            _state.value = LoginViewState(activeUserUsername = activeUserUsername)
        }
    }
}

data class LoginViewState(
    var activeUserUsername: String = ""
)