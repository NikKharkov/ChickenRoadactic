package com.chickengal.run.domain.shop

import com.chickengal.run.domain.game.ModifierType

data class ModifierShopItem(
    val type: ModifierType,
    val price: Int
)