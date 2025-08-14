package com.chickengal.run.domain.game

data class GameUiState(
    val gameState: GameState = GameState.PLAYING,
    val player: Player = Player(),
    val obstacles: List<Obstacle> = emptyList(),
    val coins: List<Coin> = emptyList(),
    val bestScore: Int = 0,
    val bestTime: Long = 0L,
    val currentTime: Long = 0L,
    val score: Int = 0,
    val regularCoins: Int = 0,
    val speed: Float = 1f,
    val health: Int = 3,
    val screenWidth: Float = 0f,
    val screenHeight: Float = 0f,
    val baseSpeed: Float = 1f,
    val currentRunRegularCoins: Int = 0,
    val distanceTraveled: Float = 0f,

    val warpDash: Modifier = Modifier(ModifierType.WARP_DASH),
    val photonPulse: Modifier = Modifier(ModifierType.PHOTON_PULSE),
    val laserBlast: Modifier = Modifier(ModifierType.LASER_BLAST),

    val isWarpDashActive: Boolean = false,
    val isPhotonPulseActive: Boolean = false,
    val isLaserBlastActive: Boolean = false,
    val warpDashDuration: Long = 0L,
    val photonPulseDuration: Long = 0L,
    val laserBlastDuration: Long = 0L,

    val currentSkin: Int = 0,
    val unlockedSkins: Set<Int> = setOf(0)
) {
    companion object {
        const val FIELD_WIDTH = 390f
        const val FIELD_HEIGHT = 480f
        const val LANE_HEIGHT = 120f

        const val PLAYER_SIZE = 86f
        const val OBSTACLE_SIZE = 80f
        const val COIN_SIZE = 50f

        const val WARP_DASH_DURATION = 0L
        const val PHOTON_PULSE_DURATION = 5000L
        const val LASER_BLAST_DURATION = 0L

        const val PLAYER_X_POSITION = 50f
    }

    val lane1Y: Float
        get() = (screenHeight / 2f) - (FIELD_HEIGHT / 2f) + (LANE_HEIGHT / 2f) - (PLAYER_SIZE / 2f)

    val lane2Y: Float
        get() = (screenHeight / 2f) - (FIELD_HEIGHT / 2f) + (LANE_HEIGHT * 1.5f) - (PLAYER_SIZE / 2f)

    val lane3Y: Float
        get() = (screenHeight / 2f) - (FIELD_HEIGHT / 2f) + (LANE_HEIGHT * 2.5f) - (PLAYER_SIZE / 2f)

    val lane4Y: Float
        get() = (screenHeight / 2f) - (FIELD_HEIGHT / 2f) + (LANE_HEIGHT * 3.5f) - (PLAYER_SIZE / 2f)

    val fieldCenterX: Float
        get() = screenWidth / 2f

    val fieldCenterY: Float
        get() = screenHeight / 2f

    val currentSpeed: Float
        get() = baseSpeed
}