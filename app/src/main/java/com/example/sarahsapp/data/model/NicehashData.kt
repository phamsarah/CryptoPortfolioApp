package com.example.sarahsapp.data.model

/**
 * NiceHash Rest Response Documentation: https://www.nicehash.com/docs/rest
 */
data class NicehashData(
    val total: Total,
    val currencies: List<Currencies>
)

data class Total(
    val currency: String,
    val totalBalance: Any,
    val available: Any,
    val debt: Any,
    val pending: Any,
)

data class Currencies(
    val active: Boolean,
    val currency: String,
    val totalBalance: String,
    val available: Any,
    val debt: Any,
    val pending: Any,
    val btcRate: Float
)

data class NicehashTime (
    val serverTime: Long
)