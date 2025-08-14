package com.chickengal.run.managers

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.chickengal.run.R
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

actual class MusicManager actual constructor() : KoinComponent {
    private val context: Context by inject()
    private val settings: SettingsManager by inject()
    private var mediaPlayer: MediaPlayer? = null
    private var soundPool: SoundPool? = null
    private var clickSoundId: Int = -1

    init {
        setupAudio()
    }

    private fun setupAudio() {
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5)
            .setAudioAttributes(audioAttributes)
            .build()

        clickSoundId = soundPool?.load(context, R.raw.click_sound, 1) ?: -1
    }

    actual fun playBackgroundMusic() {
        if (!settings.isMusicEnabled) return

        try {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(context, R.raw.background_music)
                mediaPlayer?.isLooping = true
                mediaPlayer?.setVolume(0.7f, 0.7f)
            }

            if (mediaPlayer?.isPlaying != true) {
                mediaPlayer?.start()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun stopBackgroundMusic() {
        try {
            mediaPlayer?.stop()
            mediaPlayer?.release()
            mediaPlayer = null
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun playClickSound() {
        if (!settings.isSoundEnabled || clickSoundId == -1) return

        try {
            soundPool?.play(clickSoundId, 1.0f, 1.0f, 1, 0, 1.0f)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    actual fun setMusicEnabled(enabled: Boolean) {
        settings.isMusicEnabled = enabled
    }

    actual fun setSoundEnabled(enabled: Boolean) {
        settings.isSoundEnabled = enabled
    }

    actual fun release() {
        stopBackgroundMusic()
        soundPool?.release()
        soundPool = null
    }
}