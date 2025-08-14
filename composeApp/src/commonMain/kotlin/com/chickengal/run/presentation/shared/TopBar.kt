package com.chickengal.run.presentation.shared

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.egg
import chickenroadactic.composeapp.generated.resources.ic_back
import org.jetbrains.compose.resources.painterResource

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit,
    screenTitle: String,
    fontSize: TextUnit = 28.sp,
    eggs: Int = 0,
    showEggs: Boolean = false
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        InteractiveButton(
            image = painterResource(Res.drawable.ic_back),
            onClick = onBackClick,
            modifier = Modifier
                .width(38.dp)
                .height(32.dp)
        )

        CustomText(
            text = screenTitle,
            fontSize = fontSize
        )

        if (showEggs) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Image(
                    painter = painterResource(Res.drawable.egg),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                CustomText(
                    text = "$eggs",
                    fontSize = 16.sp
                )
            }
        } else {
            Spacer(modifier = Modifier.size(40.dp))
        }
    }
}