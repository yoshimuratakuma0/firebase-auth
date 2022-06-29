package com.free.presentation.views.items

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

data class ArrowItemState(
    override val title: String
) : SettingsItemState

@Composable
fun ArrowItem(state: ArrowItemState, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .wrapContentHeight(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(state.title)
        IconButton(
            onClick = {
                onClick.invoke()
            }
        ) {
            Icon(Icons.Default.ArrowForward, contentDescription = null)
        }
    }
}

@Preview
@Composable
fun PreviewArrowItem() {
    ArrowItem(ArrowItemState("test")) {}
}