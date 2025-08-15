package com.chickengal.run.presentation.screens.shop

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.chickengal.run.domain.shop.SkinType
import com.chickengal.run.presentation.screens.shop.components.NotEnoughEggsDialog
import com.chickengal.run.presentation.screens.shop.components.SkinCard
import com.chickengal.run.presentation.shared.TopBar
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SkinsScreen(
    onBackClick: () -> Unit,
    shopViewModel: ShopViewModel = koinViewModel()
) {
    val coins by shopViewModel.regularCoins.collectAsState()
    val currentSkin by shopViewModel.currentSkin.collectAsState()

    var showNotEnoughEggsDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.systemBars)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBar(
                onBackClick = onBackClick,
                screenTitle = "Select Your Cosmic Chicken",
                fontSize = 16.sp,
                showEggs = true,
                eggs = coins
            )

            Spacer(modifier = Modifier.height(24.dp))

            LazyVerticalGrid(
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                contentPadding = PaddingValues(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(SkinType.entries.size) { index ->
                    val skinType = SkinType.entries[index]
                    val price = if (skinType == SkinType.SKIN_1) 0 else 25

                    SkinCard(
                        skinType = skinType,
                        isPurchased = shopViewModel.isSkinPurchased(skinType),
                        isApplied = currentSkin == skinType,
                        onPurchase = {
                            val success = shopViewModel.purchaseSkin(skinType, price)
                            if (!success) {
                                showNotEnoughEggsDialog = true
                            }
                            success
                        },
                        onApply = { shopViewModel.applySkin(skinType) }
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
}