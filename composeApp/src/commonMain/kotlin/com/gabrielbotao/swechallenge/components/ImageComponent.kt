package com.gabrielbotao.swechallenge.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.rememberAsyncImagePainter

@Composable
fun ImageComponent(
    imageUrl: String,
    contentDescription: String? = null
) {
    Image(
        painter = rememberAsyncImagePainter(model = imageUrl),
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .requiredSize(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(color = Color.LightGray)
    )
}

