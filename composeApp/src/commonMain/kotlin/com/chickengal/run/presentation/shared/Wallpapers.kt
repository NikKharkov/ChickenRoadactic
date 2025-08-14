package com.chickengal.run.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun Wallpapers(backgroundResImage: DrawableResource) {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(
            painter = painterResource(backgroundResImage),
            contentDescription = "Wallpapers",
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop
        )
    }
}