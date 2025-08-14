package com.chickengal.run.presentation.screens.game.components

import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import kotlin.math.abs

@Composable
fun SwipeDetector(
    onSwipeUp: () -> Unit,
    onSwipeDown: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .pointerInput(Unit) {
                detectDragGestures(
                    onDragEnd = {}
                ) { change, dragAmount ->
                    val deltaY = dragAmount.y

                    if (abs(deltaY) > 30f) {
                        when {
                            deltaY > 30f -> onSwipeDown()
                            deltaY < -30f -> onSwipeUp()
                        }
                    }
                }
            }
    ) {
        content()
    }
}