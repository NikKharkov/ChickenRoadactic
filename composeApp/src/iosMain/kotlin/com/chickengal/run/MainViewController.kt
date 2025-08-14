package com.chickengal.run

import androidx.compose.ui.window.ComposeUIViewController
import com.chickengal.run.di.initConfig

fun MainViewController() = ComposeUIViewController {
    initConfig()
    App()
}