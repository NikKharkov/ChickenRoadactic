package com.chickengal.run.inapp_purchases

import com.chickengal.run.domain.shop.CurrencyPack

object Products {
    const val PACK_SMALL = "currency_pack_50"
    const val PACK_MEDIUM = "currency_pack_120"
    const val PACK_LARGE = "currency_pack_300"

    val ALL_PACKS = listOf(
        CurrencyPack(PACK_SMALL, 50),
        CurrencyPack(PACK_MEDIUM, 120),
        CurrencyPack(PACK_LARGE, 300)
    )
}