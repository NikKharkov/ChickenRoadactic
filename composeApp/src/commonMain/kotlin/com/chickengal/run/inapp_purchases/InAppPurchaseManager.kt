package com.chickengal.run.inapp_purchases

import com.chickengal.run.domain.shop.CurrencyPack
import com.chickengal.run.domain.shop.PurchaseResult

expect class InAppPurchaseManager() {

    fun initialize(onInitialized: (List<CurrencyPack>) -> Unit)

    suspend fun purchase(productId: String): PurchaseResult
}