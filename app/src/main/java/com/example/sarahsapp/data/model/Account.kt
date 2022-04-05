package com.example.sarahsapp.data.model

/**
 *  Coinbase Pro API GET request documentation: https://docs.cloud.coinbase.com/exchange/reference/exchangerestapi_getaccounts
 *
 */

data class Account (
    val available: String,
    val balance: String,
    val currency: String,
    val hold: String,
    val id: String,
    val profile_id: String,
    val trading_enabled: Boolean
)