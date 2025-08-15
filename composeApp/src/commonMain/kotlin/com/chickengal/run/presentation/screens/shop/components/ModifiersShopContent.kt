package com.chickengal.run.presentation.screens.shop.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.chickengal.run.domain.game.ModifierType
import com.chickengal.run.domain.shop.ModifierShopItem

@Composable
fun ModifiersShopContent(
    modifiers: List<ModifierShopItem>,
    purchaseModifier: (ModifierType, Int) -> Boolean
) {
    Column (
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        modifiers.forEach { item ->
            ModifierCard(
                modifierType = item.type,
                onPurchase = {
                    purchaseModifier(item.type, item.price)
                }
            )
        }
    }
}