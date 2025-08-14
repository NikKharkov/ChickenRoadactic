package com.chickengal.run

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import chickenroadactic.composeapp.generated.resources.Res
import chickenroadactic.composeapp.generated.resources.background
import com.chickengal.run.presentation.screens.game.GameScreen
import com.chickengal.run.presentation.screens.leaderboard.LeaderboardScreen
import com.chickengal.run.presentation.screens.menu.MenuScreen
import com.chickengal.run.presentation.screens.settings.SettingsScreen
import com.chickengal.run.presentation.screens.settings.SettingsViewModel
import com.chickengal.run.presentation.screens.shop.ShopScreen
import com.chickengal.run.presentation.screens.shop.SkinsScreen
import com.chickengal.run.presentation.screens.splash.SplashScreen
import com.chickengal.run.presentation.shared.Screen
import com.chickengal.run.presentation.shared.Wallpapers
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun App() {
    val navController = rememberNavController()
    val settingsViewModel: SettingsViewModel = koinViewModel()

    Wallpapers(backgroundResImage = Res.drawable.background)
    NavHost(
        navController = navController,
        startDestination = Screen.SplashScreen.route
    ) {
        composable(Screen.SplashScreen.route) {
            SplashScreen(
                onTimeout = {
                    navController.navigate(Screen.MenuScreen.route) {
                        popUpTo(Screen.SplashScreen.route) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Screen.MenuScreen.route) {
            MenuScreen(
                onPlayClick = {
                    settingsViewModel.playClickSound()
                    navController.navigate(Screen.GameScreen.route) {
                        popUpTo(Screen.GameScreen.route) {
                            inclusive = true
                        }
                    }
                },
                onShopClick = {
                    settingsViewModel.playClickSound()
                    navController.navigate(Screen.ShopScreen.route)
                },
                onSkinsClick = {
                    settingsViewModel.playClickSound()
                    navController.navigate(Screen.SkinsScreen.route)
                },
                onRecordsClick = {
                    settingsViewModel.playClickSound()
                    navController.navigate(Screen.RecordsScreen.route)
                },
                onSettingsClick = {
                    settingsViewModel.playClickSound()
                    navController.navigate(Screen.SettingsScreen.route)
                }
            )
        }

        composable(Screen.SettingsScreen.route) {
            SettingsScreen(
                onBackClick = {
                    settingsViewModel.playClickSound()
                    navController.navigateUp()
                },
                settingsViewModel = settingsViewModel
            )
        }

        composable(Screen.RecordsScreen.route) {
            LeaderboardScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.GameScreen.route) {
            GameScreen(
                onHomeClick = {
                    settingsViewModel.playClickSound()
                    navController.navigate(Screen.MenuScreen.route) {
                        popUpTo(0)
                    }
                },
                settingsViewModel = settingsViewModel
            )
        }

        composable(Screen.SkinsScreen.route) {
            SkinsScreen(
                onBackClick = { navController.navigateUp() }
            )
        }

        composable(Screen.ShopScreen.route) {
            ShopScreen(
                onBackClick = { navController.navigateUp() }
            )
        }
    }
    LaunchedEffect(Unit) {
        settingsViewModel.startBackgroundMusic()
    }
}