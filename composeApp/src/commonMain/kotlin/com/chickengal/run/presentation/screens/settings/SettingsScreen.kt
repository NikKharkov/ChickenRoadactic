package com.chickengal.run.presentation.screens.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.chickengal.run.presentation.screens.settings.components.ResetDialog
import com.chickengal.run.presentation.screens.settings.components.SettingsBox
import com.chickengal.run.presentation.shared.TopBar

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel,
    onBackClick: () -> Unit
) {
    val settingsState by settingsViewModel.settingsUiState.collectAsState()
    var showResetDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars),
        contentAlignment = Alignment.Center
    ) {
        TopBar(
            modifier = Modifier.align(Alignment.TopCenter),
            onBackClick = onBackClick,
            screenTitle = "Profile & Settings"
        )

        SettingsBox(
            settingsUiState = settingsState,
            onMusicToggle = { settingsViewModel.toggleMusic() },
            onSoundToggle = { settingsViewModel.toggleSound() },
            onVibrationToggle = { settingsViewModel.toggleVibration() },
            onNameChange = { settingsViewModel.updatePlayerName(it) },
            onResetClick = {
                settingsViewModel.playClickSound()
                showResetDialog = true
            }
        )
    }

    if (showResetDialog) {
        ResetDialog(
            onDismiss = {
                settingsViewModel.playClickSound()
                showResetDialog = false
            },
            onReset = {
                settingsViewModel.playClickSound()
                settingsViewModel.resetScore()
            }
        )
    }
}