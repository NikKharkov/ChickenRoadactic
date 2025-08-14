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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import chickenroadactic.composeapp.generated.resources.Grobold
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.avatar
import chickenroadactic.composeapp.generated.resources.btn_reset_records
import chickenroadactic.composeapp.generated.resources.purple_box
import com.chickengal.run.domain.settings.SettingsUiState
import com.chickengal.run.presentation.shared.CustomText
import com.chickengal.run.presentation.shared.InteractiveButton
import com.chickengal.run.presentation.theme.Yellow
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun SettingsBox(
    settingsUiState: SettingsUiState,
    onMusicToggle: (Boolean) -> Unit,
    onSoundToggle: (Boolean) -> Unit,
    onVibrationToggle: (Boolean) -> Unit,
    onNameChange: (String) -> Unit,
    onResetClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .width(260.dp)
            .height(440.dp)
    ) {
        Image(
            painter = painterResource(Res.drawable.purple_box),
            contentDescription = null,
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Image(
                painter = painterResource(Res.drawable.avatar),
                contentDescription = null,
                modifier = Modifier.size(64.dp)
            )

            BasicTextField(
                value = settingsUiState.playerName,
                onValueChange = onNameChange,
                textStyle = TextStyle(
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(Res.font.Grobold)),
                    brush = Yellow,
                    textAlign = TextAlign.Center
                ),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(vertical = 8.dp),
                singleLine = true,
                decorationBox = { innerTextField ->
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        if (settingsUiState.playerName.isEmpty()) {
                            CustomText(
                                text = "Name",
                                fontSize = 20.sp
                            )
                        }
                    }
                    innerTextField()
                }
            )

            SettingsItem(
                text = "Sound:",
                isEnabled = settingsUiState.isSoundEnabled,
                onToggle = onSoundToggle
            )

            SettingsItem(
                text = "Music:",
                isEnabled = settingsUiState.isMusicEnabled,
                onToggle = onMusicToggle
            )

            SettingsItem(
                text = "Vibration:",
                isEnabled = settingsUiState.isVibrationEnabled,
                onToggle = onVibrationToggle
            )

            InteractiveButton(
                image = painterResource(Res.drawable.btn_reset_records),
                onClick = onResetClick,
                modifier = Modifier
                    .width(140.dp)
                    .height(70.dp)
            )
        }
    }
}