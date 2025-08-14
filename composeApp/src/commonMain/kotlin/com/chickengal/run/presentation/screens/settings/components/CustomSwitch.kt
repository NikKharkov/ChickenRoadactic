package com.chickengal.run.presentation.screens.settings.components

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.off
import chickenroadactic.composeapp.generated.resources.on
import com.chickengal.run.presentation.theme.Gray
import com.chickengal.run.presentation.theme.Purple
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChanged: (Boolean) -> Unit
) {
    val switchWidth = 54.dp
    val switchHeight = 29.dp
    val handleSize = 28.dp
    val handlePadding = 2.dp

    val valueToOffset = if (checked) 1f else 0f
    val offset = remember { Animatable(valueToOffset) }
    val scope = rememberCoroutineScope()

    DisposableEffect(checked) {
        if (offset.targetValue != valueToOffset) {
            scope.launch {
                offset.animateTo(valueToOffset, animationSpec = tween(300))
            }
        }
        onDispose { }
    }

    Box(
        contentAlignment = Alignment.CenterStart,
        modifier = Modifier
            .width(switchWidth)
            .height(switchHeight)
            .clip(RoundedCornerShape(16.dp))
            .background(
                brush = if (checked) Purple else Gray,
                shape = RoundedCornerShape(16.dp)
            )
            .clickable { onCheckedChanged(!checked) }
    ) {
        Box(
            modifier = Modifier
                .padding(horizontal = handlePadding)
                .size(handleSize)
                .offset(x = (switchWidth - handleSize - handlePadding * 2f) * offset.value)
                .clip(RoundedCornerShape(12.dp))
        ) {
            Image(
                painter = painterResource(if (checked) Res.drawable.on else Res.drawable.off),
                contentDescription = if (checked) "Switch On" else "Switch Off",
                contentScale = ContentScale.Fit,
                modifier = Modifier.size(handleSize).clip(shape = RoundedCornerShape(12.dp))
            )
        }
    }
}