package com.chickengal.run.inapp_purchases

import com.chickengal.run.domain.shop.CurrencyPack
import com.chickengal.run.domain.shop.PurchaseResult
import kotlinx.coroutines.CancellableContinuation
import platform.Foundation.NSError
import platform.Foundation.NSNumberFormatter
import platform.Foundation.NSNumberFormatterCurrencyStyle
import platform.Foundation.NSSet
import platform.Foundation.setWithArray
import platform.StoreKit.SKErrorCode
import platform.StoreKit.SKPayment
import platform.StoreKit.SKPaymentQueue
import platform.StoreKit.SKPaymentTransaction
import platform.StoreKit.SKProduct
import platform.StoreKit.SKProductsRequest
import kotlin.coroutines.suspendCoroutine

actual class InAppPurchaseManager actual constructor() {

    private var onInitializedCallback: ((List<CurrencyPack>) -> Unit)? = null
    private var isInitialized = false
    private val productIds = setOf(Products.PACK_SMALL, Products.PACK_MEDIUM, Products.PACK_LARGE)
    private var availableProducts: Map<String, SKProduct> = emptyMap()

    private val paymentQueue = SKPaymentQueue.defaultQueue()
    private val transactionObserver = TransactionObserver()

    init {
        paymentQueue.addTransactionObserver(transactionObserver)
        transactionObserver.purchaseManager = this
    }

    actual fun initialize(onInitialized: (List<CurrencyPack>) -> Unit) {
        onInitializedCallback = onInitialized

        if (!SKPaymentQueue.canMakePayments()) {
            println("Покупки недоступны на этом устройстве")
            onInitialized(emptyList())
            return
        }

        loadProducts()
    }

    private fun loadProducts() {
        val productIdentifiers = NSSet.setWithArray(productIds.toList())
        val request = SKProductsRequest(productIdentifiers)

        val delegate = ProductsRequestDelegate { products, invalidIds ->
            handleProductsResponse(products, invalidIds)
        }

        request.delegate = delegate
        request.start()
    }

    private fun handleProductsResponse(products: List<SKProduct>, invalidIds: List<String>) {
        availableProducts = products.associateBy { it.productIdentifier }

        if (invalidIds.isNotEmpty()) {
            println("Недопустимые ID продуктов: $invalidIds")
        }

        val currencyPacks = products.mapNotNull { product ->
            Products.ALL_PACKS.find { it.productId == product.productIdentifier }
        }

        isInitialized = true
        onInitializedCallback?.invoke(currencyPacks)
        println("iOS IAP инициализирован, доступно паков: ${currencyPacks.size}")
    }

    private fun formatPrice(product: SKProduct): String {
        val formatter = NSNumberFormatter()
        formatter.numberStyle = NSNumberFormatterCurrencyStyle
        formatter.locale = product.priceLocale
        return formatter.stringFromNumber(product.price) ?: "Неизвестно"
    }

    actual suspend fun purchase(productId: String): PurchaseResult {
        if (!isInitialized) {
            return PurchaseResult.Error("IAP не инициализирован")
        }

        if (!SKPaymentQueue.canMakePayments()) {
            return PurchaseResult.Error("Покупки недоступны")
        }

        val product = availableProducts[productId]
            ?: return PurchaseResult.Error("Продукт не найден")

        return suspendCoroutine { continuation ->
            transactionObserver.setPendingPurchase(productId,
                continuation as CancellableContinuation<PurchaseResult>
            )

            val payment = SKPayment.paymentWithProduct(product)
            paymentQueue.addPayment(payment)
        }
    }

    internal fun handleSuccessfulPurchase(transaction: SKPaymentTransaction) {
        val productId = transaction.payment.productIdentifier

        val pack = Products.ALL_PACKS.find { it.productId == productId }
        if (pack != null) {
            println("iOS: Начислено ${pack.currencyAmount} монет за покупку $productId")
        }

        paymentQueue.finishTransaction(transaction)
    }

    internal fun handleFailedPurchase(transaction: SKPaymentTransaction, error: NSError?) {
        val errorCode = error?.code ?: -1
        val errorMessage = error?.localizedDescription ?: "Неизвестная ошибка"

        when (errorCode) {
            SKErrorCode.SKErrorPaymentCancelled.value -> {
                println("iOS: Покупка отменена")
            }
            else -> {
                println("iOS: Ошибка покупки: $errorMessage")
            }
        }

        paymentQueue.finishTransaction(transaction)
    }

    fun cleanup() {
        paymentQueue.removeTransactionObserver(transactionObserver)
    }
}
