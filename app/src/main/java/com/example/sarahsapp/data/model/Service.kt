package com.example.sarahsapp.data.model

import com.example.sarahsapp.R

/**
 *  Service: Coinbase Pro, Coinbase, NiceHash, or Robinhood
 *  Each service has a portfolio for the user to views
 */
class Service(position: Int) {
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
                name = R.string.robinhood
                image = R.drawable.robinhood
            }
            3 -> {
                name = R.string.nicehash
                image = R.drawable.nicehash
            }
        }
    }

}