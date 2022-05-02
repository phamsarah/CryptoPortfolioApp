package com.example.sarahsapp.data.model

import com.example.sarahsapp.R

/**
 *  Exchange: Coinbase Pro, Coinbase, NiceHash
 *  Each service has a portfolio for the user to views
 */
class Exchange(position: Int) {
    var name: Int = 0
    var image: Int = 0

    init {
        when(position) {
            0 -> {
                name = R.string.coinbase_pro
                image = R.drawable.coinbasepro
            }
            1 -> {
                name = R.string.coinbase
                image = R.drawable.coinbase
            }
            2 -> {
                name = R.string.nicehash
                image = R.drawable.nicehash
            }
            3 -> {
                name = R.string.lofi
                image = R.drawable.lofi
            }
        }
    }

}