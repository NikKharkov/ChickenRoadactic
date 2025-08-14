package com.chickengal.run.domain.game

data class Coin(
    val id: String,
    val lane: Lane,
    val x: Float,
    val y: Float,
    val size: Float = 50f
)