package com.gabrielbotao.swechallenge.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ErrorComponent(
    errorMessage: String?,
    modifier: Modifier = Modifier,
    onClickTryAgain: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(errorMessage ?: "Error unknown")
        Button(
            onClick = {
                onClickTryAgain.invoke()
            },
            colors = ButtonDefaults.buttonColors(Color.Gray)
        ) {
            Text(
                color = Color.White,
                text = "Try again"
            )
        }
    }
}