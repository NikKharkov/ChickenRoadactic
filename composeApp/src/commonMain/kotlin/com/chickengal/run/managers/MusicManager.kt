package com.chickengal.run.managers

expect class MusicManager() {
    fun playBackgroundMusic()
    fun stopBackgroundMusic()
    fun playClickSound()
    fun setMusicEnabled(enabled: Boolean)
    fun setSoundEnabled(enabled: Boolean)
    fun release()
}