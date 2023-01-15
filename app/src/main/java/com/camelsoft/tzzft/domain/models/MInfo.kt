package com.camelsoft.tzzft.domain.models

import com.camelsoft.tzzft.R
import com.camelsoft.tzzft._common.App.Companion.getAppContext
import com.camelsoft.tzzft._common.Constants.Companion.FLAG_COORDINATES
import com.camelsoft.tzzft._common.Constants.Companion.FLAG_PHONE
import com.camelsoft.tzzft._common.Constants.Companion.FLAG_URL
import com.camelsoft.tzzft.presentation.utils.toDateTime
import kotlinx.serialization.Serializable

@Serializable
data class MInfo (
    var dateTime: Long = 0L,
    val scheme: String? = null,
    val type: String? = null,
    val brand: String? = null,
    val prepaid: Boolean? = null,
    val bank: Bank? = null,
    val country: Country? = null,
    val number: Number? = null,
) {
    fun toPair() = Pair(
        first = dateTime.toDateTime(),
        second = "$brand ${country?.numeric} ${country?.name} ${country?.alpha2}"
    )

    fun toTrippleList(): ArrayList<Triple<String, String, String?>> {
        val result = ArrayList<Triple<String, String, String?>>()
        scheme?.let {
            result.add(Triple(
                getAppContext().resources.getString(R.string.card_scheme), it, null))
        }
        type?.let {
            result.add(Triple(
                getAppContext().resources.getString(R.string.card_type), it, null))
        }
        brand?.let {
            result.add(Triple(
                getAppContext().resources.getString(R.string.card_brand), it, null))
        }
        prepaid?.let { prepaid->
            if (prepaid)
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_prepaid),
                    getAppContext().resources.getString(R.string.yes),
                    null
                ))
            else
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_prepaid),
                    getAppContext().resources.getString(R.string.no),
                    null
                ))
        }
        bank?.let { bank->
            bank.name?.let {
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_bank_name), it, null))
            }
            bank.url?.let {
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_bank_url), it, FLAG_URL))
            }
            bank.phone?.let {
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_bank_phone), it, FLAG_PHONE))
            }
            bank.city?.let {
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_bank_city), it, null))
            }
        }
        number?.let { number->
            number.length?.let {
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_number_length),
                    it.toString(),
                    null))
            }
            number.luhn?.let { luhn->
                if (luhn)
                    result.add(Triple(
                        getAppContext().resources.getString(R.string.card_number_luhn),
                        getAppContext().resources.getString(R.string.yes),
                        null))
                else
                    result.add(Triple(
                        getAppContext().resources.getString(R.string.card_number_luhn),
                        getAppContext().resources.getString(R.string.no),
                        null)) }
            }
        country?.let { country ->
            result.add(Triple(
                getAppContext().resources.getString(R.string.card_country),
                "${country.numeric?:""} ${country.name?:""} ${country.alpha2?:""}",
                null))
            country.currency?.let {
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_country_currency),
                    it,
                    null))
            }
            if (country.latitude != null && country.longitude != null) {
                result.add(Triple(
                    getAppContext().resources.getString(R.string.card_country_coord),
                    "${country.latitude},${country.longitude}",
                    FLAG_COORDINATES))
            }
        }
        return result
    }
}

@Serializable
data class Bank (
    val name: String? = null,
    val url: String? = null,
    val phone: String? = null,
    val city: String? = null
)

@Serializable
data class Country (
    val numeric: String? = null,
    val alpha2: String? = null,
    val name: String? = null,
    val emoji: String? = null,
    val currency: String? = null,
    val latitude: Int? = null,
    val longitude: Int? = null
)

@Serializable
data class Number (
    val length: Int? = null,
    val luhn: Boolean? = null
)
