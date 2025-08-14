package com.chickengal.run.presentation.shared

sealed class Screen(val route: String) {
    data object SplashScreen : Screen("splash")
    data object MenuScreen : Screen("menu")
    data object SkinsScreen : Screen("skins")
    data object SettingsScreen : Screen("settings")
    data object RecordsScreen : Screen("records")
    data object ShopScreen : Screen("shop")
    data object GameScreen : Screen("game")
}