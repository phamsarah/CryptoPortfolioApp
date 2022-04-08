package com.example.sarahsapp.data.model

/**
 * NiceHash Rest Response Documentation: https://www.nicehash.com/docs/rest
 */
data class NicehashData(
    val total: Total,
    val currencies: Currencies
)

data class Total(
    val active: Boolean,
    val currency: String,
    val totalBalance: Any,
    val available: Any,
    val debt: Any,
    val pending: Any,
    val pendingDetails: PendingDetails,
    val enabled: Boolean,
    val btcRate: Float,
    val fiatRate: Float
)

data class PendingDetails(
    val deposit: Any,
    val withdrawal: Any,
    val exchange: Any,
    val hashpowerOrders: Any,
    val unpaidMining: Any,
    val refMiningFee: Any
)

data class Currencies(
    val active: Boolean,
    val currency: String,
    val totalBalance: Any,
    val available: Any,
    val debt: Any,
    val pending: Any,
    val pendingDetails: PendingDetails,
    val enabled: Boolean,
    val btcRate: Float,
    val fiatRate: Float
)

data class NicehashTime (
    val serverTime: Long
)