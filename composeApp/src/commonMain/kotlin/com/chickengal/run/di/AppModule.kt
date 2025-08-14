package com.chickengal.run.di

import com.chickengal.run.inapp_purchases.InAppPurchaseManager
import com.chickengal.run.managers.MusicManager
import com.chickengal.run.managers.SettingsManager
import com.chickengal.run.presentation.screens.game.GameViewModel
import com.chickengal.run.presentation.screens.leaderboard.LeaderboardViewModel
import com.chickengal.run.presentation.screens.settings.SettingsViewModel
import com.chickengal.run.presentation.screens.shop.ShopViewModel
import org.koin.core.module.dsl.singleOf
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    singleOf(::SettingsManager)
    singleOf(::MusicManager)
    singleOf(::InAppPurchaseManager)
    viewModelOf(::SettingsViewModel)
    viewModelOf(::LeaderboardViewModel)
    viewModelOf(::GameViewModel)
    viewModelOf(::ShopViewModel)
}