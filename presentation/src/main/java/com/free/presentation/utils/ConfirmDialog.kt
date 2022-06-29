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
fun ConfirmDialog(
    titleResId: Int? = null,
    bodyResId: Int,
    onConfirmed:  (() -> Unit)?,
    onCanceled: (() -> Unit)?,

) {
    val isShowing = remember { mutableStateOf(true) }
    val isConfirmed = remember {
        mutableStateOf(false)
    }
    val isCanceled = remember {
        mutableStateOf(false)
    }

    if (isConfirmed.value) {
        onConfirmed?.invoke()
        return
    }

    if (isCanceled.value) {
        onCanceled?.invoke()
        return
    }

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
                        isConfirmed.value = true
                    }
                ) {
                    Text(stringResource(id = R.string.ok))
                }
            },
            dismissButton = {
                TextButton(
                    onClick = {
                        isCanceled.value = true
                    }
                ) {
                    Text(stringResource(id = R.string.cancel))
                }
            }
        )
    }
}