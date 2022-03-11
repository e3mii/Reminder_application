package com.eradotov.homework.util

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

/*TODO - This is not yet in use, will enable it in future work*/
@ExperimentalMaterialApi
@Composable
fun DefaultSnackbar(
    snackbarHostState: SnackbarHostState,
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
){
    SnackbarHost(
        hostState = snackbarHostState,
        snackbar = { data ->
            Snackbar(
                modifier = Modifier.padding(16.dp),
                action = {
                    data.actionLabel?.let { actionLabel ->
                        TextButton(
                            onClick = {
                                onDismiss()
                            },
                        ) {
                            Text(
                                text = data.message,
                                style = MaterialTheme.typography.body2,
                                color = Color.White,
                            )
                        }
                    }
                },
            ) {
                Text(
                    text = data.message,
                    style = MaterialTheme.typography.body2,
                    color = Color.White,
                )
            }
        },
        modifier = modifier
    )
}