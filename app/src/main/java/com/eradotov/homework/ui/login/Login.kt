package com.eradotov.homework.ui.login

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.accompanist.insets.systemBarsPadding


@Composable
fun Login(
    navController: NavController,
){
    val loginViewModel: LoginViewModel = LoginViewModel()
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize()
    ){
        val username = rememberSaveable {
            mutableStateOf("")
        }
        val password = rememberSaveable {
            mutableStateOf("")
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .systemBarsPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "HomeWork Application",
                fontSize = 35.sp
            )
            Spacer(modifier = Modifier.padding(10.dp))
            Text(
                text = "Hello",
                fontSize = 25.sp,
                color = MaterialTheme.colors.primary
            )
            Spacer(modifier = Modifier.padding(10.dp))
            OutlinedTextField(
                value = username.value,
                onValueChange = {data -> username.value = data},
                label = { Text(text = "Username")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            )
            Spacer(modifier = Modifier.height(10.dp))
            OutlinedTextField(
                value = password.value,
                onValueChange = {data -> password.value = data},
                label = { Text(text = "Password")},
                modifier = Modifier.fillMaxWidth(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password
                ),
                visualTransformation = PasswordVisualTransformation(),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(
                onClick = {
                    if(loginViewModel.validateLogin(username = username.value, password = password.value, context = context)){
                        navController.navigate("home")
                    }
                          },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(45.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Login")
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "OR"
            )
            Spacer(modifier = Modifier.height(10.dp))
            Button(
                onClick = { navController.navigate("registration") },
                enabled = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .size(45.dp),
                shape = MaterialTheme.shapes.small
            ) {
                Text(text = "Registration")
            }
        }
        Column(
            verticalArrangement = Arrangement.Bottom,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "v1.0"
            )
        }
    }
}