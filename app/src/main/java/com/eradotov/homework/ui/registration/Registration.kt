package com.eradotov.homework.ui.registration

import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import com.eradotov.homework.R
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Registration(
    onBackPress: () -> Unit
) {
    val context = LocalContext.current

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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "First name")},
                    onValueChange = {},
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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "Last name")},
                    onValueChange = {},
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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "Username")},
                    onValueChange = {},
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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "Password")},
                    onValueChange = {},
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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "Password confirmation")},
                    onValueChange = {},
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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "Email")},
                    onValueChange = {},
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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "Phone number")},
                    onValueChange = {},
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
                    value = "under construction...",
                    enabled = false,
                    label = { Text(text = "Adrress")},
                    onValueChange = {},
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
                    onClick = { Toast.makeText(context, "Under construction...will enable registration", Toast.LENGTH_SHORT).show() },
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