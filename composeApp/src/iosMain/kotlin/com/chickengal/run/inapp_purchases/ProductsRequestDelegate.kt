package com.chickengal.run.inapp_purchases

import platform.Foundation.NSError
import platform.StoreKit.SKProduct
import platform.StoreKit.SKProductsRequest
import platform.StoreKit.SKProductsRequestDelegateProtocol
import platform.StoreKit.SKProductsResponse
import platform.StoreKit.SKRequest
import platform.darwin.NSObject

class ProductsRequestDelegate(
    private val onResponse: (List<SKProduct>, List<String>) -> Unit
) : NSObject(), SKProductsRequestDelegateProtocol {

    override fun productsRequest(request: SKProductsRequest, didReceiveResponse: SKProductsResponse) {
        val products = didReceiveResponse.products.map { it as SKProduct }
        val invalidIds = didReceiveResponse.invalidProductIdentifiers.map { it as String }

        onResponse(products, invalidIds)
    }

    override fun request(request: SKRequest, didFailWithError: NSError) {
        println("iOS: Ошибка запроса продуктов: ${didFailWithError.localizedDescription}")
        onResponse(emptyList(), emptyList())
    }
}