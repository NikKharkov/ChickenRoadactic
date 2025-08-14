package com.chickengal.run.presentation.screens.game

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chickengal.run.domain.game.Coin
import com.chickengal.run.domain.game.GameState
import com.chickengal.run.domain.game.GameUiState
import com.chickengal.run.domain.game.Lane
import com.chickengal.run.domain.game.Modifier
import com.chickengal.run.domain.game.ModifierType
import com.chickengal.run.domain.game.Obstacle
import com.chickengal.run.domain.game.ObstacleType
import com.chickengal.run.domain.game.Player
import com.chickengal.run.domain.shop.SkinType
import com.chickengal.run.managers.SettingsManager
import com.chickengal.run.managers.VibrationManager
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock

class GameViewModel(private val settingsManager: SettingsManager) : ViewModel() {
    private val _uiState = MutableStateFlow(
        GameUiState(
            bestScore = settingsManager.bestScore,
            regularCoins = settingsManager.regularCoins,
            warpDash = Modifier(ModifierType.WARP_DASH, settingsManager.warpDashCount),
            photonPulse = Modifier(ModifierType.PHOTON_PULSE, settingsManager.photonPulseCount),
            laserBlast = Modifier(ModifierType.LASER_BLAST, settingsManager.laserBlastCount),
            currentSkin = settingsManager.currentSkin.ordinal,
            unlockedSkins = getSkinsPurchasedSet()
        )
    )
    val uiState: StateFlow<GameUiState> = _uiState.asStateFlow()

    private var gameJob: Job? = null
    private var lastCoinTime = 0L
    private var lastObstacleTime = 0L
    private var gameStartTime = 0L

    private fun getSkinsPurchasedSet(): Set<Int> {
        val purchasedSkins = mutableSetOf<Int>()
        SkinType.entries.forEachIndexed { index, skinType ->
            if (settingsManager.isSkinPurchased(skinType)) {
                purchasedSkins.add(index)
            }
        }
        return purchasedSkins
    }

    fun startGame() {
        gameStartTime = Clock.System.now().toEpochMilliseconds()
        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            while (_uiState.value.gameState == GameState.PLAYING) {
                updateGame()
                delay(16)
            }
        }
    }

    private fun updateGame() {
        val currentState = _uiState.value
        val currentTime = Clock.System.now().toEpochMilliseconds()

        val timeInSeconds = (currentTime - gameStartTime) / 1000f
        val currentScore = (timeInSeconds * 10).toInt()

        val updatedState = updateModifierEffects(currentState, currentTime)
            .copy(score = currentScore, distanceTraveled = timeInSeconds * 10)

        val allCoins = generateCoins(updatedState)
        val allObstacles = generateObstacles(updatedState.copy(coins = allCoins))

        val movedCoins = moveCoins(allCoins, updatedState.currentSpeed)
        var movedObstacles = moveObstacles(allObstacles, updatedState.currentSpeed)

        if (updatedState.isLaserBlastActive) {
            movedObstacles = movedObstacles.filter { it.lane != updatedState.player.lane }
            _uiState.value = updatedState.copy(isLaserBlastActive = false)
        }

        val (collectedCoins, remainingCoins) = checkCoinCollisions(movedCoins, updatedState.player)
        val hasObstacleCollision = if (updatedState.isPhotonPulseActive) {
            false
        } else {
            checkObstacleCollisions(movedObstacles, updatedState.player)
        }

        var newCurrentRunRegularCoins = updatedState.currentRunRegularCoins

        collectedCoins.forEach { coin ->
            newCurrentRunRegularCoins++
        }

        if (hasObstacleCollision) {
            gameOver()
            return
        }

        _uiState.value = updatedState.copy(
            coins = remainingCoins,
            obstacles = movedObstacles,
            score = currentScore,
            distanceTraveled = timeInSeconds * 10,
            currentRunRegularCoins = newCurrentRunRegularCoins
        )
    }

    private fun updateModifierEffects(state: GameUiState, currentTime: Long): GameUiState {
        var newState = state

        if (state.isPhotonPulseActive && currentTime > state.photonPulseDuration) {
            newState = newState.copy(
                isPhotonPulseActive = false,
                photonPulseDuration = 0L
            )
        }

        return newState
    }

    private fun generateObstacles(state: GameUiState): List<Obstacle> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val newObstacles = state.obstacles.toMutableList()

        val spawnInterval = (1500..3000).random()

        if (currentTime - lastObstacleTime > spawnInterval) {
            val lane = Lane.entries.random()

            val obstacle = Obstacle(
                id = "obstacle_$currentTime",
                lane = lane,
                x = state.screenWidth + 100f,
                y = getLaneY(lane, state),
                type = ObstacleType.entries.random()
            )

            newObstacles.add(obstacle)
            lastObstacleTime = currentTime
        }

        return newObstacles
    }

    private fun moveObstacles(obstacles: List<Obstacle>, speed: Float): List<Obstacle> {
        return obstacles.map { obstacle ->
            obstacle.copy(x = obstacle.x - (5f * speed))
        }.filter { it.x > -100f }
    }

    private fun checkObstacleCollisions(obstacles: List<Obstacle>, player: Player): Boolean {
        obstacles.forEach { obstacle ->
            val distanceX = kotlin.math.abs(obstacle.x - player.x)
            val distanceY = kotlin.math.abs(obstacle.y - player.y)

            val collisionDistance = 45f
            if (distanceX < collisionDistance && distanceY < collisionDistance) {
                VibrationManager.vibrate(100)
                return true
            }
        }
        return false
    }

    private fun generateCoins(state: GameUiState): List<Coin> {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        val newCoins = state.coins.toMutableList()

        if (currentTime - lastCoinTime > (2000..4000).random()) {
            val lane = Lane.entries.random()

            val coin = Coin(
                id = "coin_$currentTime",
                lane = lane,
                x = state.screenWidth + 100f,
                y = getLaneY(lane, state)
            )

            newCoins.add(coin)
            lastCoinTime = currentTime
        }

        return newCoins
    }

    private fun moveCoins(coins: List<Coin>, speed: Float): List<Coin> {
        return coins.map { coin ->
            coin.copy(x = coin.x - (5f * speed))
        }.filter { it.x > -100f }
    }

    private fun checkCoinCollisions(
        coins: List<Coin>,
        player: Player
    ): Pair<List<Coin>, List<Coin>> {
        val collected = mutableListOf<Coin>()
        val remaining = mutableListOf<Coin>()

        coins.forEach { coin ->
            val distanceX = kotlin.math.abs(coin.x - player.x)
            val distanceY = kotlin.math.abs(coin.y - player.y)

            val collisionDistance = 50f
            val isColliding = distanceX < collisionDistance && distanceY < collisionDistance

            if (isColliding) {
                collected.add(coin)
            } else {
                remaining.add(coin)
            }
        }

        return Pair(collected, remaining)
    }

    private fun getLaneY(lane: Lane, state: GameUiState): Float {
        return when (lane) {
            Lane.LANE1 -> state.lane1Y
            Lane.LANE2 -> state.lane2Y
            Lane.LANE3 -> state.lane3Y
            Lane.LANE4 -> state.lane4Y
        }
    }

    fun activateWarpDash() {
        val currentState = _uiState.value
        if (currentState.warpDash.count <= 0) return

        if (settingsManager.useModifier(ModifierType.WARP_DASH)) {
            val currentLaneIndex = currentState.player.lane.ordinal
            val newLaneIndex = minOf(currentLaneIndex + 2, 3)
            val newLane = Lane.entries[newLaneIndex]

            _uiState.value = currentState.copy(
                warpDash = currentState.warpDash.copy(count = currentState.warpDash.count - 1),
                player = currentState.player.copy(
                    lane = newLane,
                    y = getLaneY(newLane, currentState)
                )
            )
        }
    }

    fun activatePhotonPulse() {
        val currentState = _uiState.value
        if (currentState.photonPulse.count <= 0 || currentState.isPhotonPulseActive) return

        if (settingsManager.useModifier(ModifierType.PHOTON_PULSE)) {
            val currentTime = Clock.System.now().toEpochMilliseconds()
            _uiState.value = currentState.copy(
                photonPulse = currentState.photonPulse.copy(count = currentState.photonPulse.count - 1),
                isPhotonPulseActive = true,
                photonPulseDuration = currentTime + GameUiState.PHOTON_PULSE_DURATION
            )
        }
    }

    fun activateLaserBlast() {
        val currentState = _uiState.value
        if (currentState.laserBlast.count <= 0) return

        if (settingsManager.useModifier(ModifierType.LASER_BLAST)) {
            _uiState.value = currentState.copy(
                laserBlast = currentState.laserBlast.copy(count = currentState.laserBlast.count - 1),
                isLaserBlastActive = true
            )
        }
    }

    fun onScreenSizeChanged(width: Float, height: Float) {
        val newState = _uiState.value.copy(
            screenWidth = width,
            screenHeight = height
        )

        val playerY = getLaneY(newState.player.lane, newState)

        _uiState.value = newState.copy(
            player = newState.player.copy(
                x = GameUiState.PLAYER_X_POSITION,
                y = playerY
            )
        )

        if (_uiState.value.gameState == GameState.PLAYING) {
            startGame()
        }
    }

    fun onSwipeUp() {
        val currentState = _uiState.value
        if (currentState.gameState != GameState.PLAYING) return

        val currentLaneIndex = currentState.player.lane.ordinal
        if (currentLaneIndex > 0) {
            val newLane = Lane.entries[currentLaneIndex - 1]
            switchToLane(newLane)
        }
    }

    fun onSwipeDown() {
        val currentState = _uiState.value
        if (currentState.gameState != GameState.PLAYING) return

        val currentLaneIndex = currentState.player.lane.ordinal
        if (currentLaneIndex < 3) {
            val newLane = Lane.entries[currentLaneIndex + 1]
            switchToLane(newLane)
        }
    }

    private fun switchToLane(newLane: Lane) {
        val currentState = _uiState.value
        val newY = getLaneY(newLane, currentState)

        _uiState.value = currentState.copy(
            player = currentState.player.copy(
                lane = newLane,
                y = newY
            )
        )
    }

    fun pauseGame() {
        gameJob?.cancel()
        _uiState.value = _uiState.value.copy(gameState = GameState.PAUSED)
    }

    fun resumeGame() {
        _uiState.value = _uiState.value.copy(gameState = GameState.PLAYING)

        gameJob?.cancel()
        gameJob = viewModelScope.launch {
            while (_uiState.value.gameState == GameState.PLAYING) {
                updateGame()
                delay(16)
            }
        }
    }

    fun gameOver() {
        val currentScore = _uiState.value.score
        val currentBest = _uiState.value.bestScore
        val currentRunRegularCoins = _uiState.value.currentRunRegularCoins

        val newRegularCoins = _uiState.value.regularCoins + currentRunRegularCoins

        settingsManager.regularCoins = newRegularCoins

        var newBestScore = currentBest

        if (currentScore > currentBest) {
            settingsManager.bestScore = currentScore
            newBestScore = currentScore
        }

        _uiState.value = _uiState.value.copy(
            gameState = GameState.GAME_OVER,
            bestScore = newBestScore,
            regularCoins = newRegularCoins
        )
    }

    fun restartGame() {
        gameJob?.cancel()
        lastCoinTime = 0L
        lastObstacleTime = 0L
        gameStartTime = Clock.System.now().toEpochMilliseconds()

        val currentState = _uiState.value

        val newState = GameUiState(
            screenWidth = currentState.screenWidth,
            screenHeight = currentState.screenHeight,
            bestScore = currentState.bestScore,
            regularCoins = settingsManager.regularCoins,
            currentRunRegularCoins = 0,
            warpDash = Modifier(ModifierType.WARP_DASH, settingsManager.warpDashCount),
            photonPulse = Modifier(ModifierType.PHOTON_PULSE, settingsManager.photonPulseCount),
            laserBlast = Modifier(ModifierType.LASER_BLAST, settingsManager.laserBlastCount),
            currentSkin = settingsManager.currentSkin.ordinal,
            unlockedSkins = getSkinsPurchasedSet()
        )

        val playerY = getLaneY(Lane.LANE2, newState)

        _uiState.value = newState.copy(
            player = newState.player.copy(
                lane = Lane.LANE2,
                x = GameUiState.PLAYER_X_POSITION,
                y = playerY
            )
        )
        startGame()
    }
}