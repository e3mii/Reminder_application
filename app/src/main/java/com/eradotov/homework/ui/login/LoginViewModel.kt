package com.eradotov.homework.ui.login

import android.content.Context
import android.widget.Toast

class LoginViewModel {
    fun validateLogin(username: String?, password: String?, context: Context): Boolean {
        val correct: Boolean?
        if (username == "" || password == "") {
            correct = false
            Toast.makeText(context, "You need to enter username/password...", Toast.LENGTH_SHORT).show()
        } else {
            if(username != "eradotov22" || password != "admin"){
                correct = false
                Toast.makeText(context, "Username or password is incorrect...", Toast.LENGTH_SHORT).show()
            } else {
                correct = true
            }
        }
        return correct
    }
}