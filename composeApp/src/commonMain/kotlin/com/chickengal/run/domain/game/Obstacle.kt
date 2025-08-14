package com.chickengal.run.domain.game

data class Obstacle(
    val id: String,
    val lane: Lane,
    val x: Float,
    val y: Float,
    val size: Float = 80f,
    val type: ObstacleType = ObstacleType.TYPE1
)