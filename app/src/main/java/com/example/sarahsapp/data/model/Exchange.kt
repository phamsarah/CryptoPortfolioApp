package com.example.sarahsapp.data.model

import com.example.sarahsapp.R

/**
 *  Exchange: Coinbase Pro, Coinbase, NiceHash
 *  Each service has a portfolio for the user to views
 */
object Exchange {
    var exchangeList = listOf(
        Pair(R.string.coinbase_pro, R.drawable.coinbasepro),
        Pair(R.string.coinbase, R.drawable.coinbase),
        Pair(R.string.nicehash, R.drawable.nicehash))
}