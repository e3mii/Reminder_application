package com.eradotov.homework.ui.profile

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
import androidx.compose.ui.unit.dp
import com.eradotov.homework.R
import com.google.accompanist.insets.systemBarsPadding

@Composable
fun Profile(
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
                        onClick = { Toast.makeText(context, "Under construction...will enable change", Toast.LENGTH_SHORT).show() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = stringResource(R.string.edit),
                            tint = MaterialTheme.colors.primary
                        )
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
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null,
                    modifier = Modifier.size(150.dp),
                    tint = MaterialTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Emanuel Radotovic",
                    style = MaterialTheme.typography.h5
                )
                Spacer(modifier = Modifier.height(20.dp))
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = "eradotov22",
                    enabled = false,
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
                    value = "eradotov22@student.oulu.fi",
                    enabled = false,
                    onValueChange = {},
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
                    value = "+358991231234",
                    enabled = false,
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
                    value = "Oulu, address...",
                    enabled = false,
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
            }
        }
    }
}