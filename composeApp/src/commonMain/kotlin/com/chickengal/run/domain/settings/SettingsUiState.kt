package com.chickengal.run.domain.settings

data class SettingsUiState(
    val isMusicEnabled: Boolean = true,
    val isSoundEnabled: Boolean = true,
    val isVibrationEnabled: Boolean = true,
    val playerName: String
)