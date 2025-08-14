package com.chickengal.run.inapp_purchases

import com.chickengal.run.domain.shop.PurchaseResult
import kotlinx.coroutines.CancellableContinuation
import platform.StoreKit.SKErrorCode
import platform.StoreKit.SKPaymentQueue
import platform.StoreKit.SKPaymentTransaction
import platform.StoreKit.SKPaymentTransactionObserverProtocol
import platform.StoreKit.SKPaymentTransactionState
import platform.darwin.NSObject
import kotlin.coroutines.resume

class TransactionObserver : NSObject(), SKPaymentTransactionObserverProtocol {
    var purchaseManager: InAppPurchaseManager? = null
    private val pendingPurchases = mutableMapOf<String, CancellableContinuation<PurchaseResult>>()

    fun setPendingPurchase(productId: String, continuation: CancellableContinuation<PurchaseResult>) {
        pendingPurchases[productId] = continuation
    }

    override fun paymentQueue(queue: SKPaymentQueue, updatedTransactions: List<*>) {
        updatedTransactions.forEach { transaction ->
            if (transaction is SKPaymentTransaction) {
                handleTransaction(transaction)
            }
        }
    }

    private fun handleTransaction(transaction: SKPaymentTransaction) {
        val productId = transaction.payment.productIdentifier
        val continuation = pendingPurchases.remove(productId)

        when (transaction.transactionState) {
            SKPaymentTransactionState.SKPaymentTransactionStatePurchased -> {
                purchaseManager?.handleSuccessfulPurchase(transaction)
                continuation?.resume(PurchaseResult.Success)
            }

            SKPaymentTransactionState.SKPaymentTransactionStateFailed -> {
                purchaseManager?.handleFailedPurchase(transaction, transaction.error)

                val error = transaction.error
                val result = if (error?.code == SKErrorCode.SKErrorPaymentCancelled.value) {
                    PurchaseResult.Cancelled
                } else {
                    PurchaseResult.Error(error?.localizedDescription ?: "Ошибка покупки")
                }
                continuation?.resume(result)
            }

            SKPaymentTransactionState.SKPaymentTransactionStateRestored -> {
                purchaseManager?.handleSuccessfulPurchase(transaction)
                continuation?.resume(PurchaseResult.Success)
            }

            SKPaymentTransactionState.SKPaymentTransactionStatePurchasing -> {
                println("iOS: Покупка в процессе...")
            }

            SKPaymentTransactionState.SKPaymentTransactionStateDeferred -> {
                println("iOS: Покупка отложена")
            }

            else -> {
                continuation?.resume(PurchaseResult.Error("Неизвестное состояние транзакции"))
            }
        }
    }
}
