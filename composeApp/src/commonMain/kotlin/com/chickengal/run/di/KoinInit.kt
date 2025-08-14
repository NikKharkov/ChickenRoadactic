package com.chickengal.run.di

import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun initConfig(config: KoinAppDeclaration? = null) {
    startKoin {
        config?.invoke(this)
        modules(appModule)
    }
}