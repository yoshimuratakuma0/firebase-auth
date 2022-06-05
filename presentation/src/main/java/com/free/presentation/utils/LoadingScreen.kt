package com.free.presentation.utils

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import com.free.presentation.R

@Composable
fun LoadingScreen(alpha: Float = 0.5F) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .alpha(alpha)
            .background(colorResource(id = R.color.black)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Preview
@Composable
fun LoadingScreenPreview() {
    LoadingScreen()
}