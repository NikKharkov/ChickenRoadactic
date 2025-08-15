package com.chickengal.run.presentation.screens.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chickengal.run.domain.shop.ShopTab
import com.chickengal.run.presentation.screens.shop.components.ModifiersShopContent
import com.chickengal.run.presentation.screens.shop.components.ShopTabSwitcher
import com.chickengal.run.presentation.screens.shop.components.EggPacksShopContent
import com.chickengal.run.presentation.screens.shop.components.NotEnoughEggsDialog
import com.chickengal.run.presentation.shared.TopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ShopScreen(
    onBackClick: () -> Unit,
    shopViewModel: ShopViewModel = koinViewModel()
) {
    val eggs by shopViewModel.regularCoins.collectAsState()
    val eggPacks by shopViewModel.currencyPacks.collectAsState()
    val currentTab by shopViewModel.currentTab.collectAsState()
    val modifiers = shopViewModel.modifierItems

    var showNotEnoughEggsDialog by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        TopBar(
            screenTitle = "Shop",
            onBackClick = onBackClick,
            eggs = eggs,
            showEggs = true
        )

        ShopTabSwitcher(
            currentTab = currentTab,
            onTabSelected = { tab -> shopViewModel.switchTab(tab) }
        )

        when (currentTab) {
            ShopTab.MODIFIERS -> {
                ModifiersShopContent(
                    modifiers = modifiers,
                    purchaseModifier = { type, price ->
                        val success = shopViewModel.purchaseModifier(type, price)
                        if (!success) {
                            showNotEnoughEggsDialog = true
                        }
                        success
                    }
                )
            }

            ShopTab.EGG_PACKS -> {
                EggPacksShopContent(
                    packs = eggPacks,
                    purchaseModifier = { pack ->
                        shopViewModel.purchaseCurrencyPack(pack)
                    }
                )
            }
        }
    }

    if (showNotEnoughEggsDialog) {
        NotEnoughEggsDialog(
            onDismiss = { showNotEnoughEggsDialog = false }
        )
    }
}