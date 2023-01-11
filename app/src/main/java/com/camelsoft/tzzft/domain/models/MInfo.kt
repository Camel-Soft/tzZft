package com.camelsoft.tzzft.domain.models

import kotlinx.serialization.Serializable

@Serializable
data class MInfo (
    val number: Number,
    val scheme: String,
    val type: String,
    val brand: String,
    val prepaid: Boolean,
    val country: Country,
    val bank: Bank
)

@Serializable
data class Bank (
    val name: String,
    val url: String,
    val phone: String,
    val city: String
)

@Serializable
data class Country (
    val numeric: String,
    val alpha2: String,
    val name: String,
    val emoji: String,
    val currency: String,
    val latitude: Int,
    val longitude: Int
)

@Serializable
data class Number (
    val length: Int,
    val luhn: Boolean
)
