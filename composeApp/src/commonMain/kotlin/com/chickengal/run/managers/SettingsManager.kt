package com.chickengal.run.managers

import com.chickengal.run.domain.game.ModifierType
import com.chickengal.run.domain.shop.SkinType
import com.russhwolf.settings.Settings

class SettingsManager {
    private val settings = Settings()

    var playerName: String
        get() = settings.getString(KEY_PLAYER_NAME, "")
        set(value) = settings.putString(KEY_PLAYER_NAME, value)

    var isMusicEnabled: Boolean
        get() = settings.getBoolean(KEY_MUSIC_ENABLED, true)
        set(value) = settings.putBoolean(KEY_MUSIC_ENABLED, value)

    var isSoundEnabled: Boolean
        get() = settings.getBoolean(KEY_SOUND_ENABLED, true)
        set(value) = settings.putBoolean(KEY_SOUND_ENABLED, value)

    var isVibrationEnabled: Boolean
        get() = settings.getBoolean(KEY_VIBRATION_ENABLED, true)
        set(value) = settings.putBoolean(KEY_VIBRATION_ENABLED, value)

    var bestScore: Int
        get() = settings.getInt(KEY_BEST_SCORE, 0)
        set(value) = settings.putInt(KEY_BEST_SCORE, value)

    var regularCoins: Int
        get() = settings.getInt(KEY_REGULAR_COINS, 0)
        set(value) = settings.putInt(KEY_REGULAR_COINS, value)

    var warpDashCount: Int
        get() = settings.getInt(KEY_WARP_DASH_COUNT, 0)
        set(value) = settings.putInt(KEY_WARP_DASH_COUNT, value)

    var photonPulseCount: Int
        get() = settings.getInt(KEY_PHOTON_PULSE_COUNT, 0)
        set(value) = settings.putInt(KEY_PHOTON_PULSE_COUNT, value)

    var laserBlastCount: Int
        get() = settings.getInt(KEY_LASER_BLAST_COUNT, 0)
        set(value) = settings.putInt(KEY_LASER_BLAST_COUNT, value)

    var currentSkin: SkinType
        get() = SkinType.valueOf(settings.getString(KEY_CURRENT_SKIN, SkinType.SKIN_1.name))
        set(value) = settings.putString(KEY_CURRENT_SKIN, value.name)

    var skin1Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_1, true)
        set(value) = settings.putBoolean(KEY_SKIN_1, value)

    var skin2Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_2, false)
        set(value) = settings.putBoolean(KEY_SKIN_2, value)

    var skin3Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_3, false)
        set(value) = settings.putBoolean(KEY_SKIN_3, value)

    var skin4Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_4, false)
        set(value) = settings.putBoolean(KEY_SKIN_4, value)

    var skin5Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_5, false)
        set(value) = settings.putBoolean(KEY_SKIN_5, value)

    var skin6Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_6, false)
        set(value) = settings.putBoolean(KEY_SKIN_6, value)

    var skin7Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_7, false)
        set(value) = settings.putBoolean(KEY_SKIN_7, value)

    var skin8Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_8, false)
        set(value) = settings.putBoolean(KEY_SKIN_8, value)

    var skin9Purchased: Boolean
        get() = settings.getBoolean(KEY_SKIN_9, false)
        set(value) = settings.putBoolean(KEY_SKIN_9, value)

    fun purchaseModifier(type: ModifierType, price: Int): Boolean {
        if (regularCoins < price) return false

        when (type) {
            ModifierType.WARP_DASH -> warpDashCount++
            ModifierType.PHOTON_PULSE -> photonPulseCount++
            ModifierType.LASER_BLAST -> laserBlastCount++
        }

        regularCoins -= price
        return true
    }

    fun useModifier(type: ModifierType): Boolean {
        val hasModifier = when (type) {
            ModifierType.WARP_DASH -> warpDashCount > 0
            ModifierType.PHOTON_PULSE -> photonPulseCount > 0
            ModifierType.LASER_BLAST -> laserBlastCount > 0
        }

        if (!hasModifier) return false

        when (type) {
            ModifierType.WARP_DASH -> warpDashCount--
            ModifierType.PHOTON_PULSE -> photonPulseCount--
            ModifierType.LASER_BLAST -> laserBlastCount--
        }

        return true
    }

    fun purchaseSkin(type: SkinType, price: Int): Boolean {
        if (regularCoins < price) return false

        when (type) {
            SkinType.SKIN_1 -> skin1Purchased = true
            SkinType.SKIN_2 -> skin2Purchased = true
            SkinType.SKIN_3 -> skin3Purchased = true
            SkinType.SKIN_4 -> skin4Purchased = true
            SkinType.SKIN_5 -> skin5Purchased = true
            SkinType.SKIN_6 -> skin6Purchased = true
            SkinType.SKIN_7 -> skin7Purchased = true
            SkinType.SKIN_8 -> skin8Purchased = true
            SkinType.SKIN_9 -> skin9Purchased = true
        }

        regularCoins -= price
        return true
    }

    fun applySkin(type: SkinType) {
        currentSkin = type
    }

    fun isSkinPurchased(type: SkinType): Boolean {
        return when (type) {
            SkinType.SKIN_1 -> skin1Purchased
            SkinType.SKIN_2 -> skin2Purchased
            SkinType.SKIN_3 -> skin3Purchased
            SkinType.SKIN_4 -> skin4Purchased
            SkinType.SKIN_5 -> skin5Purchased
            SkinType.SKIN_6 -> skin6Purchased
            SkinType.SKIN_7 -> skin7Purchased
            SkinType.SKIN_8 -> skin8Purchased
            SkinType.SKIN_9 -> skin9Purchased
        }
    }

    companion object {
        private const val KEY_PLAYER_NAME = "player_name"
        private const val KEY_MUSIC_ENABLED = "music_enabled"
        private const val KEY_SOUND_ENABLED = "sound_enabled"
        private const val KEY_VIBRATION_ENABLED = "vibration_enabled"
        private const val KEY_BEST_SCORE = "best_score"
        private const val KEY_REGULAR_COINS = "regular_coins"
        private const val KEY_WARP_DASH_COUNT = "warp_dash_count"
        private const val KEY_PHOTON_PULSE_COUNT = "photon_pulse_count"
        private const val KEY_LASER_BLAST_COUNT = "laser_blast_count"
        private const val KEY_CURRENT_SKIN = "current_skin"
        private const val KEY_SKIN_1 = "skin_1_purchased"
        private const val KEY_SKIN_2 = "skin_2_purchased"
        private const val KEY_SKIN_3 = "skin_3_purchased"
        private const val KEY_SKIN_4 = "skin_4_purchased"
        private const val KEY_SKIN_5 = "skin_5_purchased"
        private const val KEY_SKIN_6 = "skin_6_purchased"
        private const val KEY_SKIN_7 = "skin_7_purchased"
        private const val KEY_SKIN_8 = "skin_8_purchased"
        private const val KEY_SKIN_9 = "skin_9_purchased"
    }
}