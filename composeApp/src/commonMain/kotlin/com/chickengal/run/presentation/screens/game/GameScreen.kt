package com.chickengal.run.presentation.screens.game

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalDensity
import com.chickengal.run.domain.game.GameState
import com.chickengal.run.platform.BackHandler
import com.chickengal.run.presentation.screens.game.components.CoinItem
import com.chickengal.run.presentation.screens.game.components.GameOverOverlay
import com.chickengal.run.presentation.screens.game.components.GameUI
import com.chickengal.run.presentation.screens.game.components.ObstacleItem
import com.chickengal.run.presentation.screens.game.components.PauseOverlay
import com.chickengal.run.presentation.screens.game.components.PlayerCharacter
import com.chickengal.run.presentation.screens.game.components.SwipeDetector
import com.chickengal.run.presentation.screens.settings.SettingsViewModel
import com.chickengal.run.presentation.screens.shop.ShopViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun GameScreen(
    onHomeClick: () -> Unit,
    shopViewModel: ShopViewModel = koinViewModel(),
    settingsViewModel: SettingsViewModel,
    gameViewModel: GameViewModel = koinViewModel()
) {
    val currentSkin by shopViewModel.currentSkin.collectAsState()
    val gameUiState by gameViewModel.uiState.collectAsState()
    val density = LocalDensity.current

    BackHandler {
        gameViewModel.pauseGame()
    }

    SwipeDetector(
        onSwipeUp = {
            settingsViewModel.playClickSound()
            gameViewModel.onSwipeUp()
        },
        onSwipeDown = {
            settingsViewModel.playClickSound()
            gameViewModel.onSwipeDown()
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .onSizeChanged { size ->
                    with(density) {
                        gameViewModel.onScreenSizeChanged(
                            width = size.width.toDp().value,
                            height = size.height.toDp().value
                        )
                    }
                }
                .windowInsetsPadding(WindowInsets.systemBars)
        ) {
            gameUiState.coins.forEach { coin ->
                CoinItem(
                    coin = coin,
                    modifier = Modifier
                )
            }

            gameUiState.obstacles.forEach { obstacle ->
                ObstacleItem(
                    obstacle = obstacle,
                    modifier = Modifier
                )
            }

            PlayerCharacter(
                player = gameUiState.player,
                gameState = gameUiState.gameState,
                currentSkin = currentSkin,
                modifier = Modifier
            )

            GameUI(
                currentRunCoins = gameUiState.currentRunRegularCoins,
                currentScore = gameUiState.score,
                warpDashCount = gameUiState.warpDash.count,
                photonPulseCount = gameUiState.photonPulse.count,
                laserBlastCount = gameUiState.laserBlast.count,
                isWarpDashActive = gameUiState.isWarpDashActive,
                isPhotonPulseActive = gameUiState.isPhotonPulseActive,
                isLaserBlastActive = gameUiState.isLaserBlastActive,
                isPaused = gameUiState.gameState == GameState.PAUSED,
                onPause = { gameViewModel.pauseGame() },
                onWarpDashClick = { gameViewModel.activateWarpDash() },
                onPhotonPulseClick = { gameViewModel.activatePhotonPulse() },
                onLaserBlastClick = { gameViewModel.activateLaserBlast() }
            )
        }
    }

    when (gameUiState.gameState) {
        GameState.PAUSED -> {
            PauseOverlay(
                onResumeClick = { gameViewModel.resumeGame() },
                onRestartClick = { gameViewModel.restartGame() },
                onMainMenuClick = onHomeClick
            )
        }

        GameState.GAME_OVER -> {
            GameOverOverlay(
                collectedCoins = gameUiState.currentRunRegularCoins,
                finalScore = gameUiState.score,
                onRestartClick = { gameViewModel.restartGame() },
                onMainMenuClick = onHomeClick
            )
        }

        GameState.PLAYING -> {}
    }
}