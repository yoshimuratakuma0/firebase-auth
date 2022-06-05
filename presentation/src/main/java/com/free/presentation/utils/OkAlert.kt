package com.free.presentation.utils

import androidx.compose.material.AlertDialog
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.res.stringResource
import com.free.presentation.R

@Composable
fun OkAlertDialog(titleResId: Int? = null, bodyResId: Int) {
    val isShowing = remember { mutableStateOf(true) }
    if (isShowing.value) {
        AlertDialog(
            onDismissRequest = { },
            title = {
                if (titleResId != null)
                    Text(stringResource(id = titleResId))
            },
            text = {
                Text(stringResource(id = bodyResId))
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        isShowing.value = false
                    }
                ) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = null
        )
    }
}