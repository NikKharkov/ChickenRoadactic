package com.chickengal.run.presentation.screens.settings.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.btn_cancel
import chickenroadactic.composeapp.generated.resources.btn_reset
import chickenroadactic.composeapp.generated.resources.dialog_box
import com.chickengal.run.presentation.shared.CustomText
import com.chickengal.run.presentation.shared.InteractiveButton
import org.jetbrains.compose.resources.painterResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ResetDialog(
    onDismiss: () -> Unit,
    onReset: () -> Unit
) {
    BasicAlertDialog(
        onDismissRequest = onDismiss,
        modifier = Modifier
            .width(280.dp)
            .height(180.dp)
            .padding(16.dp),
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(Res.drawable.dialog_box),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier.fillMaxSize()
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceEvenly
            ) {
                CustomText(
                    text = "Are you sure you want to reset your progress?",
                    fontSize = 18.sp
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    InteractiveButton(
                        image = painterResource(Res.drawable.btn_cancel),
                        onClick = onDismiss,
                        modifier = Modifier
                            .width(74.dp)
                            .height(36.dp)
                    )

                    InteractiveButton(
                        image = painterResource(Res.drawable.btn_reset),
                        onClick = {
                            onReset()
                            onDismiss()
                        },
                        modifier = Modifier
                            .width(74.dp)
                            .height(36.dp)
                    )
                }
            }
        }
    }
}