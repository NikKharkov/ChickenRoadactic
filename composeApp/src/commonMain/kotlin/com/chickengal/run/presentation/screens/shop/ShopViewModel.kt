package com.chickengal.run.presentation.screens.shop

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chickengal.run.domain.game.ModifierType
import com.chickengal.run.domain.shop.CurrencyPack
import com.chickengal.run.domain.shop.ModifierShopItem
import com.chickengal.run.domain.shop.PurchaseResult
import com.chickengal.run.domain.shop.ShopTab
import com.chickengal.run.domain.shop.SkinType
import com.chickengal.run.inapp_purchases.InAppPurchaseManager
import com.chickengal.run.managers.SettingsManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ShopViewModel(
    private val settingsManager: SettingsManager,
    private val inAppPurchaseManager: InAppPurchaseManager
) : ViewModel() {
    private val _regularCoins = MutableStateFlow(settingsManager.regularCoins)
    val regularCoins = _regularCoins.asStateFlow()

    private val _currentSkin = MutableStateFlow(settingsManager.currentSkin)
    val currentSkin = _currentSkin.asStateFlow()

    private val _skin1Purchased = MutableStateFlow(settingsManager.skin1Purchased)
    val skin1Purchased = _skin1Purchased.asStateFlow()

    private val _skin2Purchased = MutableStateFlow(settingsManager.skin2Purchased)
    val skin2Purchased = _skin2Purchased.asStateFlow()

    private val _skin3Purchased = MutableStateFlow(settingsManager.skin3Purchased)
    val skin3Purchased = _skin3Purchased.asStateFlow()

    private val _skin4Purchased = MutableStateFlow(settingsManager.skin4Purchased)
    val skin4Purchased = _skin4Purchased.asStateFlow()

    private val _skin5Purchased = MutableStateFlow(settingsManager.skin5Purchased)
    val skin5Purchased = _skin5Purchased.asStateFlow()

    private val _skin6Purchased = MutableStateFlow(settingsManager.skin6Purchased)
    val skin6Purchased = _skin6Purchased.asStateFlow()

    private val _skin7Purchased = MutableStateFlow(settingsManager.skin7Purchased)
    val skin7Purchased = _skin7Purchased.asStateFlow()

    private val _skin8Purchased = MutableStateFlow(settingsManager.skin8Purchased)
    val skin8Purchased = _skin8Purchased.asStateFlow()

    private val _skin9Purchased = MutableStateFlow(settingsManager.skin9Purchased)
    val skin9Purchased = _skin9Purchased.asStateFlow()

    private val _currentTab = MutableStateFlow(ShopTab.MODIFIERS)
    val currentTab = _currentTab.asStateFlow()
    private val _currencyPacks = MutableStateFlow<List<CurrencyPack>>(emptyList())
    val currencyPacks = _currencyPacks.asStateFlow()

    private val _isPurchasing = MutableStateFlow(false)
    val isPurchasing = _isPurchasing.asStateFlow()

    private val _purchaseError = MutableStateFlow<String?>(null)
    val purchaseError = _purchaseError.asStateFlow()

    val modifierItems = listOf(
        ModifierShopItem(ModifierType.WARP_DASH, 25),
        ModifierShopItem(ModifierType.PHOTON_PULSE, 25),
        ModifierShopItem(ModifierType.LASER_BLAST, 25)
    )

    init {
        updateStates()
        initializeIAP()
    }

    private fun initializeIAP() {
        inAppPurchaseManager.initialize { packs ->
            _currencyPacks.value = packs
            println("IAP инициализирован, доступно паков: ${packs.size}")
        }
    }

    fun switchTab(tab: ShopTab) {
        _currentTab.value = tab
    }

    fun purchaseModifier(type: ModifierType, price: Int): Boolean {
        if (_regularCoins.value < price) return false

        val success = settingsManager.purchaseModifier(type, price)
        if (success) {
            updateStates()
        }
        return success
    }

    fun purchaseSkin(type: SkinType, price: Int): Boolean {
        if (_regularCoins.value < price) return false

        val success = settingsManager.purchaseSkin(type, price)
        if (success) {
            updateStates()
        }
        return success
    }

    fun applySkin(type: SkinType) {
        settingsManager.applySkin(type)
        updateStates()
    }

    fun purchaseCurrencyPack(pack: CurrencyPack) {
        if (_isPurchasing.value) return

        viewModelScope.launch {
            _isPurchasing.value = true
            _purchaseError.value = null

            try {
                val result = inAppPurchaseManager.purchase(pack.productId)
                when (result) {
                    is PurchaseResult.Success -> {
                        updateStates()
                        println("Покупка успешна! Новый баланс: ${settingsManager.regularCoins}")
                    }
                    is PurchaseResult.Cancelled -> {
                        println("Покупка отменена")
                    }
                    is PurchaseResult.Error -> {
                        _purchaseError.value = result.message
                        println("Ошибка покупки: ${result.message}")
                    }
                }
            } catch (e: Exception) {
                _purchaseError.value = "Неожиданная ошибка: ${e.message}"
                println("Исключение при покупке: ${e.message}")
            } finally {
                _isPurchasing.value = false
            }
        }
    }

    fun clearPurchaseError() {
        _purchaseError.value = null
    }

    fun isSkinPurchased(skinType: SkinType): Boolean {
        return when (skinType) {
            SkinType.SKIN_1 -> _skin1Purchased.value
            SkinType.SKIN_2 -> _skin2Purchased.value
            SkinType.SKIN_3 -> _skin3Purchased.value
            SkinType.SKIN_4 -> _skin4Purchased.value
            SkinType.SKIN_5 -> _skin5Purchased.value
            SkinType.SKIN_6 -> _skin6Purchased.value
            SkinType.SKIN_7 -> _skin7Purchased.value
            SkinType.SKIN_8 -> _skin8Purchased.value
            SkinType.SKIN_9 -> _skin9Purchased.value
        }
    }

    private fun updateStates() {
        _regularCoins.value = settingsManager.regularCoins
        _currentSkin.value = settingsManager.currentSkin

        _skin1Purchased.value = settingsManager.skin1Purchased
        _skin2Purchased.value = settingsManager.skin2Purchased
        _skin3Purchased.value = settingsManager.skin3Purchased
        _skin4Purchased.value = settingsManager.skin4Purchased
        _skin5Purchased.value = settingsManager.skin5Purchased
        _skin6Purchased.value = settingsManager.skin6Purchased
        _skin7Purchased.value = settingsManager.skin7Purchased
        _skin8Purchased.value = settingsManager.skin8Purchased
        _skin9Purchased.value = settingsManager.skin9Purchased
    }
}