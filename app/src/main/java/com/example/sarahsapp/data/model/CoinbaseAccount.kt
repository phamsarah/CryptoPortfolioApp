package com.example.sarahsapp.data.model

import java.util.*

data class CoinbaseAccount(
    val pagination: Pagination,
    val data: List<Data>
)

data class Pagination(
    val ending_before: String,
    val starting_after: String,
    val previous_ending_before: String,
    val next_starting_after: String,
    val limit: Int,
    val order: String,
    val previous_uri: String,
    val next_uri: String
)

data class Balance(
    val amount: String,
    val currency: String
)

data class Currency(
    val code: String,
    val name: String,
    val color: String,
    val sort_index: Int,
    val exponent: Int,
    val type: String,
    val address_regex: String,
    val asset_id: String,
    val slug: String
)

data class Data(
    val id: String,
    val name: String,
    val primary: Boolean,
    val type: String,
    val currency: Currency,
    val balance: Balance,
    val created_at: Date,
    val updated_at: Date,
    val resource: String,
    val resource_path: String,
    val allow_deposits: Boolean,
    val allow_withdrawals: Boolean,
    val native_balance: Balance,
)