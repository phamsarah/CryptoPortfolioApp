package com.example.sarahsapp.data.request

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  Coinbase Pro API GET request documentation: https://docs.cloud.coinbase.com/exchange/reference/exchangerestapi_getaccounts
 *
 */

object RequestBuilder {
    // Http Client
    private val okHttpClient = OkHttpClient.Builder()

    fun <T> buildJsonRequest(requestType: Class<T>, position: Int): T {
        var apiUrl = ""

        when(position){
            0 -> apiUrl = "https://api.exchange.coinbase.com/"
            1 -> apiUrl = "https://api.coinbase.com/"
            2 -> apiUrl = "https://api2.nicehash.com/"
        }

        // Retrofit builder with JSON output
       val jsonRequestBuilder = Retrofit.Builder().baseUrl(apiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())

        return jsonRequestBuilder.build().create(requestType)
    }
}