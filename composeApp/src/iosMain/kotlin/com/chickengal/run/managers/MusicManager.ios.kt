package com.chickengal.run.managers

import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.ObjCObjectVar
import kotlinx.cinterop.alloc
import kotlinx.cinterop.memScoped
import kotlinx.cinterop.ptr
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import platform.AVFAudio.AVAudioPlayer
import platform.AVFAudio.AVAudioSession
import platform.AVFAudio.AVAudioSessionCategoryPlayback
import platform.AVFAudio.setActive
import platform.Foundation.NSBundle
import platform.Foundation.NSError
import platform.Foundation.NSURL
import kotlin.getValue

actual class MusicManager actual constructor() : KoinComponent {
    private val settings: SettingsManager by inject()
    private var backgroundMusicPlayer: AVAudioPlayer? = null
    private var clickSoundPlayer: AVAudioPlayer? = null

    init {
        setupAudio()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun setupAudio() {
        val audioSession = AVAudioSession.sharedInstance()

        memScoped {
            val errorPtr = alloc<ObjCObjectVar<NSError?>>()
            audioSession.setCategory(AVAudioSessionCategoryPlayback, error = errorPtr.ptr)
            audioSession.setActive(true, error = errorPtr.ptr)
        }

        setupBackgroundMusic()
        setupClickSound()
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun setupBackgroundMusic() {
        val bundle = NSBundle.mainBundle
        val musicPath = bundle.pathForResource("background_music", "mp3")

        if (musicPath != null) {
            val musicUrl = NSURL.fileURLWithPath(musicPath)

            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                try {
                    backgroundMusicPlayer = AVAudioPlayer(musicUrl, errorPtr.ptr)
                    backgroundMusicPlayer?.numberOfLoops = -1
                    backgroundMusicPlayer?.volume = 0.7f
                    backgroundMusicPlayer?.prepareToPlay()
                } catch (e: Exception) {
                    println("Error setting up background music: $e")
                }
            }
        }
    }

    @OptIn(ExperimentalForeignApi::class)
    private fun setupClickSound() {
        val bundle = NSBundle.mainBundle
        val soundPath = bundle.pathForResource("click_sound", "mp3")

        if (soundPath != null) {
            val soundUrl = NSURL.fileURLWithPath(soundPath)

            memScoped {
                val errorPtr = alloc<ObjCObjectVar<NSError?>>()
                try {
                    clickSoundPlayer = AVAudioPlayer(soundUrl, errorPtr.ptr)
                    clickSoundPlayer?.volume = 1.0f
                    clickSoundPlayer?.prepareToPlay()
                } catch (e: Exception) {
                    println("Error setting up click sound: $e")
                }
            }
        }
    }

    actual fun playBackgroundMusic() {
        if (!settings.isMusicEnabled) return

        try {
            if (backgroundMusicPlayer?.playing != true) {
                backgroundMusicPlayer?.play()
            }
        } catch (e: Exception) {
            println("Error playing background music: $e")
        }
    }

    actual fun stopBackgroundMusic() {
        try {
            backgroundMusicPlayer?.stop()
            backgroundMusicPlayer?.currentTime = 0.0
        } catch (e: Exception) {
            println("Error stopping background music: $e")
        }
    }

    actual fun playClickSound() {
        if (!settings.isSoundEnabled) return

        try {
            clickSoundPlayer?.stop()
            clickSoundPlayer?.currentTime = 0.0
            clickSoundPlayer?.play()
        } catch (e: Exception) {
            println("Error playing click sound: $e")
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
        backgroundMusicPlayer = null
        clickSoundPlayer = null
    }
}