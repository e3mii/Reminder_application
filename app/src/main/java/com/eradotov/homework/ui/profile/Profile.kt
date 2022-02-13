package com.eradotov.homework.ui.profile

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.eradotov.homework.R
import com.google.accompanist.insets.systemBarsPadding

/*TODO-FILL WITH REAL USER DATA*/
@Composable
fun Profile(
    onBackPress: () -> Unit
) {
    val context = LocalContext.current
    val textEditEnable = rememberSaveable{ mutableStateOf(false ) }
    val appTopBarButtonDisable = rememberSaveable{ mutableStateOf( true ) }
    val saveButtonShow = rememberSaveable{ mutableStateOf( false ) }
    val profileFirstname = rememberSaveable { mutableStateOf("") }
    val profileLastname = rememberSaveable { mutableStateOf("") }
    val profileUsername = rememberSaveable { mutableStateOf("") }
    val profileEmail = rememberSaveable { mutableStateOf("") }
    val profileMobile = rememberSaveable { mutableStateOf("") }
    val profileAddress = rememberSaveable { mutableStateOf("") }

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
                    onClick = onBackPress,
                    enabled = appTopBarButtonDisable.value
                ) {
                    if(!appTopBarButtonDisable.value){
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                }
                Text(
                    text = "Profile",
                    color = MaterialTheme.colors.primary,
                    style = MaterialTheme.typography.h6
                )
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(end = 5.dp),
                    horizontalArrangement = Arrangement.End
                ) {
                    IconButton(
                        onClick = {
                            textEditEnable.value = true
                            appTopBarButtonDisable.value = false
                            saveButtonShow.value = true
                        },
                        enabled = appTopBarButtonDisable.value
                    ) {
                        if(!appTopBarButtonDisable.value){
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(R.string.edit)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.Edit,
                                contentDescription = stringResource(R.string.edit),
                                tint = MaterialTheme.colors.primary
                            )
                        }
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth()
            ) {
                AnimatedVisibility(!textEditEnable.value){
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = null,
                        modifier = Modifier.size(150.dp),
                        tint = MaterialTheme.colors.primary
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                if(textEditEnable.value){
                    OutlinedTextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = profileFirstname.value,
                        placeholder = { Text(text = "Emanuel")},
                        enabled = textEditEnable.value,
                        onValueChange = { data -> profileUsername.value = data },
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
                        value = profileLastname.value,
                        placeholder = { Text(text = "Radotovic")},
                        enabled = textEditEnable.value,
                        onValueChange = { data -> profileUsername.value = data },
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
                } else {
                    Text(
                        text = "Emanuel Radotovic",
                        style = MaterialTheme.typography.h5
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = profileUsername.value,
                    placeholder = { Text(text = "eradotov22")},
                    enabled = textEditEnable.value,
                    onValueChange = { data -> profileUsername.value = data },
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
                    value = profileEmail.value,
                    placeholder = { Text(text = "eradotov22@student.oulu.fi")},
                    enabled = textEditEnable.value,
                    onValueChange = { data -> profileEmail.value = data },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text
                    ),
                    textStyle = MaterialTheme.typography.body1,
                    trailingIcon = {
                        Icon(
                            imageVector = Icons.Default.Mail,
                            contentDescription = null,
                            tint = MaterialTheme.colors.primary
                        )
                    }
                )
                Spacer(modifier = Modifier.height(10.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = profileMobile.value,
                    placeholder = { Text(text = "+385991231234")},
                    enabled = textEditEnable.value,
                    onValueChange = { data -> profileMobile.value = data },
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone
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
                    value = profileAddress.value,
                    placeholder = { Text(text = "Oulu, address...")},
                    enabled = textEditEnable.value,
                    onValueChange = { data -> profileAddress.value = data },
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
                AnimatedVisibility(saveButtonShow.value){
                    Button(
                        onClick = {
                            textEditEnable.value = false
                            appTopBarButtonDisable.value = true
                            saveButtonShow.value = false
                            Toast.makeText(context, "Under construction...will update new data to database", Toast.LENGTH_SHORT).show()
                        },
                        enabled = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .size(45.dp),
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(text = "Save changes")
                    }
                }
            }
        }
    }
}