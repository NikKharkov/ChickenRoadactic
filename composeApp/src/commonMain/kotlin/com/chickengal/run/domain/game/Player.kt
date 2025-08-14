package com.chickengal.run.domain.game

data class Player(
    val lane: Lane = Lane.LANE2,
    val x: Float = PLAYER_X_POSITION,
    val y: Float = 0f,
    val size: Float = 86f,
    val height: Float = 86f
) {
    companion object {
        const val PLAYER_X_POSITION = 50f
    }
}