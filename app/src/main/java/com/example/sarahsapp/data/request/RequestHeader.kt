package com.example.sarahsapp.data.request

import com.example.sarahsapp.data.model.CoinbaseData
import com.example.sarahsapp.data.model.CoinbaseProData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header

interface RequestHeader {
    @GET("accounts")
    fun accounts(
        @Header("cb-access-key") cbAccessKey: String,
        @Header("cb-access-passphrase") cbAccessPassphrase: String,
        @Header("cb-access-sign") cbAccessSign: String,
        @Header("cb-access-timestamp") cbAccessTimestamp: String
    ): Call<List<CoinbaseProData>>

    @GET("/v2/accounts?limit=100")
    fun getCoinbaseHeaders(
        @Header("CB-ACCESS-KEY") accessKey: String,
        @Header("CB-ACCESS-SIGN") accessSign: String,
        @Header("CB-ACCESS-TIMESTAMP") accessTimestamp: String
    ): Call<CoinbaseData>
}