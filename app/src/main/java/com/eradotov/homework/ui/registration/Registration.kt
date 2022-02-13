package com.eradotov.homework.ui.registration

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.eradotov.homework.R
import com.google.accompanist.insets.systemBarsPadding
import androidx.lifecycle.viewmodel.compose.viewModel
import com.eradotov.homework.data.entity.User
import kotlinx.coroutines.launch

@Composable
fun Registration(
    onBackPress: () -> Unit,
    viewModel: RegistrationViewModel = viewModel()
) {
    val context = LocalContext.current
    val coroutineScope = rememberCoroutineScope()
    val Firstname = rememberSaveable { mutableStateOf("") }
    val Lastname = rememberSaveable { mutableStateOf("") }
    val Username = rememberSaveable { mutableStateOf("") }
    val Password = rememberSaveable { mutableStateOf("") }
    val PasswordConfirm = rememberSaveable { mutableStateOf("") }
    val Email = rememberSaveable { mutableStateOf("") }
    val Mobile = rememberSaveable { mutableStateOf("") }
    val Address = rememberSaveable { mutableStateOf("") }

    Surface {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .systemBarsPadding()
        ) {
            TopAppBar(
                backgroundColor = MaterialTheme.colors.secondary.copy(alpha = 0.87f),
            ) {
                IconButton(
                    onClick = onBackPress
                ) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = null,
                        tint = MaterialTheme.colors.primary
                    )
                }
                Text(
                    text = "Registration",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    Icon(
                        imageVector = Icons.Default.AppRegistration,
                        contentDescription = stringResource(R.string.edit),
                        tint = MaterialTheme.colors.primary
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = Firstname.value,
                    enabled = true,
                    label = { Text(text = "First name")},
                    onValueChange = {data -> Firstname.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = MaterialTheme.typography.body1,
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
                    modifier = Modifier.fillMaxWidth(),
                    value = Lastname.value,
                    enabled = true,
                    label = { Text(text = "Last name")},
                    onValueChange = {data -> Lastname.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = MaterialTheme.typography.body1,
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
                    modifier = Modifier.fillMaxWidth(),
                    value = Username.value,
                    enabled = true,
                    label = { Text(text = "Username")},
                    onValueChange = {data -> Username.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = MaterialTheme.typography.body1,
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
                    modifier = Modifier.fillMaxWidth(),
                    value = Password.value,
                    enabled = true,
                    label = { Text(text = "Password")},
                    onValueChange = {data -> Password.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = MaterialTheme.typography.body1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = PasswordConfirm.value,
                    enabled = true,
                    label = { Text(text = "Password confirmation")},
                    onValueChange = {data -> PasswordConfirm.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Password
                    ),
                    visualTransformation = PasswordVisualTransformation(),
                    textStyle = MaterialTheme.typography.body1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = Email.value,
                    enabled = true,
                    label = { Text(text = "Email")},
                    onValueChange = {data -> Email.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = Mobile.value,
                    enabled = true,
                    label = { Text(text = "Phone number")},
                    onValueChange = {data -> Mobile.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Number
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Phone,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = Address.value,
                    enabled = true,
                    label = { Text(text = "Adrress")},
                    onValueChange = {data -> Address.value = data},
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Map,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        val user = User(
                            firstName = Firstname.value,
                            lastName = Lastname.value,
                            username = Username.value,
                            password = Password.value,
                            mail = Email.value,
                            phoneNumber = Mobile.value,
                            address = Address.value
                        )
                        coroutineScope.launch {
                            viewModel.saveRegistration(user)
                            onBackPress()
                        }
                    },
                    enabled = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .size(45.dp),
                    shape = MaterialTheme.shapes.small
                ) {
                    Text(text = "Register")
                }
            }
        }
    }
}