package com.chickengal.run.domain.shop

sealed class PurchaseResult {
    object Success : PurchaseResult()
    object Cancelled : PurchaseResult()
    data class Error(val message: String) : PurchaseResult()
}