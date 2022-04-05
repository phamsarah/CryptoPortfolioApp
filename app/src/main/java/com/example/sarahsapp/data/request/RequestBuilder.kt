package com.example.sarahsapp.data.request

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 *  Coinbase Pro API GET request documentation: https://docs.cloud.coinbase.com/exchange/reference/exchangerestapi_getaccounts
 *
 */

object RequestBuilder {
    private const val COINBASEPRO_URL = "https://api.exchange.coinbase.com/"

    // Http Client
    private val okHttpClient = OkHttpClient.Builder()

    // Retrofit builder with JSON output
    private val jsonRequestBuilder = Retrofit.Builder().baseUrl(COINBASEPRO_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient.build())

    fun <T> buildJsonRequest(requestType: Class<T>): T {
        return jsonRequestBuilder.build().create(requestType)
    }
}