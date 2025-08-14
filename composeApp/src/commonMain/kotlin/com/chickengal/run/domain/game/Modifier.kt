package com.chickengal.run.domain.game

data class Modifier(
    val type: ModifierType,
    val count: Int = 0,
    val isActive: Boolean = false,
    val duration: Long = 0L,
    val maxDuration: Long = when(type) {
        ModifierType.WARP_DASH -> 0L
        ModifierType.PHOTON_PULSE -> 5000L
        ModifierType.LASER_BLAST -> 0L
    }
)
