package com.chickengal.run.inapp_purchases

import android.app.Activity
import android.content.Context
import com.android.billingclient.api.AcknowledgePurchaseParams
import com.android.billingclient.api.BillingClient
import com.android.billingclient.api.BillingClientStateListener
import com.android.billingclient.api.BillingFlowParams
import com.android.billingclient.api.BillingResult
import com.android.billingclient.api.PendingPurchasesParams
import com.android.billingclient.api.Purchase
import com.android.billingclient.api.PurchasesUpdatedListener
import com.android.billingclient.api.QueryProductDetailsParams
import com.chickengal.run.domain.shop.CurrencyPack
import com.chickengal.run.domain.shop.PurchaseResult
import com.chickengal.run.managers.SettingsManager
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

actual class InAppPurchaseManager actual constructor() : KoinComponent {
    private val context: Context by inject()
    private val settingsManager: SettingsManager by inject()
    private var onInitializedCallback: ((List<CurrencyPack>) -> Unit)? = null
    private var isInitialized = false
    private val productIds = listOf(Products.PACK_SMALL, Products.PACK_MEDIUM, Products.PACK_LARGE)

    private val purchasesUpdatedListener = PurchasesUpdatedListener { billingResult, purchases ->
        when (billingResult.responseCode) {
            BillingClient.BillingResponseCode.OK -> {
                purchases?.forEach { purchase ->
                    if (purchase.purchaseState == Purchase.PurchaseState.PURCHASED) {
                        handleSuccessfulPurchase(purchase)
                    }
                }
            }
            BillingClient.BillingResponseCode.USER_CANCELED -> {
                println("Покупка отменена пользователем")
            }
            else -> {
                println("Ошибка покупки: ${billingResult.debugMessage}")
            }
        }
    }

    private fun handleSuccessfulPurchase(purchase: Purchase) {
        val productId = purchase.products.firstOrNull() ?: return

        val pack = Products.ALL_PACKS.find { it.productId == productId }
        if (pack != null) {

            val currentCoins = settingsManager.regularCoins
            settingsManager.regularCoins = currentCoins + pack.currencyAmount

            println("Начислено ${pack.currencyAmount} монет! Было: $currentCoins, стало: ${settingsManager.regularCoins}")

            acknowledgePurchase(purchase)
        } else {
            println("Неизвестный продукт: $productId")
        }
    }

    private fun acknowledgePurchase(purchase: Purchase) {
        if (!purchase.isAcknowledged) {
            val acknowledgePurchaseParams = AcknowledgePurchaseParams.newBuilder()
                .setPurchaseToken(purchase.purchaseToken)
                .build()

            billingClient.acknowledgePurchase(acknowledgePurchaseParams) { billingResult ->
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    println("Покупка подтверждена успешно")
                } else {
                    println("Ошибка подтверждения покупки: ${billingResult.debugMessage}")
                }
            }
        }
    }

    private val billingClient = BillingClient.newBuilder(context)
        .setListener(purchasesUpdatedListener)
        .enablePendingPurchases(PendingPurchasesParams.newBuilder().enableOneTimeProducts().build())
        .enableAutoServiceReconnection()
        .build()

    actual fun initialize(onInitialized: (List<CurrencyPack>) -> Unit) {
        onInitializedCallback = onInitialized

        billingClient.startConnection(object : BillingClientStateListener {
            override fun onBillingSetupFinished(billingResult: BillingResult) {
                if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                    isInitialized = true
                    loadProducts()
                } else {
                    println("Ошибка инициализации биллинга: ${billingResult.debugMessage}")
                    onInitialized(emptyList())
                }
            }

            override fun onBillingServiceDisconnected() {
                isInitialized = false
                println("Биллинг отключился, попробуем переподключиться при следующем запросе")
            }
        })
    }

    private fun loadProducts() {
        val productList = productIds.map { productId ->
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        }

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        billingClient.queryProductDetailsAsync(params) { billingResult, productDetailsList ->
            if (billingResult.responseCode == BillingClient.BillingResponseCode.OK) {
                val currencyPacks = productDetailsList.productDetailsList.mapNotNull { productDetails ->
                    Products.ALL_PACKS.find { it.productId == productDetails.productId }
                }
                onInitializedCallback?.invoke(currencyPacks)
            } else {
                println("Ошибка загрузки продуктов: ${billingResult.debugMessage}")
                onInitializedCallback?.invoke(emptyList())
            }
        }
    }

    actual suspend fun purchase(productId: String): PurchaseResult {
        if (!isInitialized) {
            return PurchaseResult.Error("Биллинг не инициализирован")
        }

        val productList = listOf(
            QueryProductDetailsParams.Product.newBuilder()
                .setProductId(productId)
                .setProductType(BillingClient.ProductType.INAPP)
                .build()
        )

        val params = QueryProductDetailsParams.newBuilder()
            .setProductList(productList)
            .build()

        return suspendCoroutine { continuation ->
            billingClient.queryProductDetailsAsync(params) { billingResult, queryResult ->
                if (billingResult.responseCode != BillingClient.BillingResponseCode.OK) {
                    continuation.resume(PurchaseResult.Error("Ошибка получения деталей продукта: ${billingResult.debugMessage}"))
                    return@queryProductDetailsAsync
                }

                val productDetails = queryResult.productDetailsList.firstOrNull()
                if (productDetails == null) {
                    continuation.resume(PurchaseResult.Error("Продукт не найден"))
                    return@queryProductDetailsAsync
                }

                val activity = context as? Activity
                if (activity == null) {
                    continuation.resume(PurchaseResult.Error("Контекст не является Activity"))
                    return@queryProductDetailsAsync
                }

                val purchaseParams = BillingFlowParams.newBuilder()
                    .setProductDetailsParamsList(
                        listOf(
                            BillingFlowParams.ProductDetailsParams.newBuilder()
                                .setProductDetails(productDetails)
                                .build()
                        )
                    )
                    .build()

                val launchResult = billingClient.launchBillingFlow(activity, purchaseParams)

                when (launchResult.responseCode) {
                    BillingClient.BillingResponseCode.OK -> {
                        continuation.resume(PurchaseResult.Success)
                    }
                    BillingClient.BillingResponseCode.USER_CANCELED -> {
                        continuation.resume(PurchaseResult.Cancelled)
                    }
                    else -> {
                        continuation.resume(PurchaseResult.Error("Ошибка запуска покупки: ${launchResult.debugMessage}"))
                    }
                }
            }
        }
    }
}
