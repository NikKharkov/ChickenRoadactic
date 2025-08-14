package com.chickengal.run

import android.app.Application
import com.chickengal.run.di.initConfig
import org.koin.android.ext.koin.androidContext

class GameApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        initConfig {
            androidContext(this@GameApplication)
        }
    }
}