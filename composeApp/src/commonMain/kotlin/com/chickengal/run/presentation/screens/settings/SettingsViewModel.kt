package com.chickengal.run.presentation.screens.settings

import androidx.lifecycle.ViewModel
import com.chickengal.run.domain.settings.SettingsUiState
import com.chickengal.run.managers.MusicManager
import com.chickengal.run.managers.SettingsManager
import com.chickengal.run.managers.VibrationManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class SettingsViewModel(
    private val settings: SettingsManager,
    private val musicManager: MusicManager
) : ViewModel() {

    private val _settingsUiState = MutableStateFlow(
        SettingsUiState(
            isMusicEnabled = settings.isMusicEnabled,
            isSoundEnabled = settings.isSoundEnabled,
            isVibrationEnabled = settings.isVibrationEnabled,
            playerName = settings.playerName
        )
    )

    val settingsUiState = _settingsUiState.asStateFlow()

    fun toggleMusic() {
        _settingsUiState.update { currentState ->
            val newValue = !currentState.isMusicEnabled
            musicManager.setMusicEnabled(newValue)

            if (newValue) {
                musicManager.playBackgroundMusic()
            } else {
                musicManager.stopBackgroundMusic()
            }

            currentState.copy(isMusicEnabled = newValue)
        }
    }

    fun toggleSound() {
        _settingsUiState.update { currentState ->
            val newValue = !currentState.isSoundEnabled
            musicManager.setSoundEnabled(newValue)
            currentState.copy(isSoundEnabled = newValue)
        }
    }

    fun toggleVibration() {
        _settingsUiState.update { currentState ->
            val newValue = !currentState.isVibrationEnabled
            settings.isVibrationEnabled = newValue
            currentState.copy(isVibrationEnabled = newValue)
        }
    }

    fun playClickSound() {
        musicManager.playClickSound()
    }

    fun startBackgroundMusic() {
        musicManager.playBackgroundMusic()
    }

    fun vibrate() {
        if (_settingsUiState.value.isVibrationEnabled) {
            VibrationManager.vibrate(100)
        }
    }

    fun resetScore() {
        settings.bestScore = 0
    }

    fun updatePlayerName(name: String) {
        _settingsUiState.update { currentState ->
            settings.playerName = name
            currentState.copy(playerName = name)
        }
    }

    override fun onCleared() {
        super.onCleared()
        musicManager.release()
    }
}
