package com.eradotov.homework.ui.registration

import androidx.lifecycle.ViewModel
import com.eradotov.homework.Graph
import com.eradotov.homework.data.entity.User
import com.eradotov.homework.data.repository.UserRepository

class RegistrationViewModel(
    private val userRepository: UserRepository = Graph.userRepository
) : ViewModel() {

    suspend fun saveRegistration(user: User): Long{
        return userRepository.addUser(user)
    }
}