package com.example.sarahsapp.data.request

import com.example.sarahsapp.data.model.Account
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
    ): Call<List<Account>>
}