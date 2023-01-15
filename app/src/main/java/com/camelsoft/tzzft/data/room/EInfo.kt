package com.camelsoft.tzzft.data.room

import androidx.room.*
import com.camelsoft.tzzft.domain.models.Bank
import com.camelsoft.tzzft.domain.models.Country
import com.camelsoft.tzzft.domain.models.MInfo
import com.camelsoft.tzzft.domain.models.Number

@Entity(
    tableName = "table_info",
    indices = [
        Index("scheme", unique = false)
    ]
)
data class EInfo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Long,
    @ColumnInfo(name = "date_time") val dateTime: Long,
    @ColumnInfo(name = "scheme") val scheme: String?,
    @ColumnInfo(name = "type") val type: String?,
    @ColumnInfo(name = "brand") val brand: String?,
    @ColumnInfo(name = "prepaid") val prepaid: Boolean?,
    @ColumnInfo(name = "bank_name") val bankName: String?,
    @ColumnInfo(name = "bank_url") val bankUrl: String?,
    @ColumnInfo(name = "bank_phone") val bankPhone: String?,
    @ColumnInfo(name = "bank_city") val bankCity: String?,
    @ColumnInfo(name = "country_numeric") val countryNumeric: String?,
    @ColumnInfo(name = "country_alpha2") val countryAlpha2: String?,
    @ColumnInfo(name = "country_name") val countryName: String?,
    @ColumnInfo(name = "country_emoji") val countryEmoji: String?,
    @ColumnInfo(name = "country_currency") val countryCurrency: String?,
    @ColumnInfo(name = "country_latitude") val countryLatitude: Int?,
    @ColumnInfo(name = "country_longitude") val countryLongitude: Int?,
    @ColumnInfo(name = "number_length") val numberLength: Int?,
    @ColumnInfo(name = "number_luhn") val numberLuhn: Boolean?
) {
    fun toMInfo() = MInfo(
        dateTime = dateTime,
        scheme = scheme,
        type = type,
        brand = brand,
        prepaid = prepaid,
        bank = Bank(
            name = bankName,
            url = bankUrl,
            phone = bankPhone,
            city = bankCity
        ),
        country = Country(
            numeric = countryNumeric,
            alpha2 = countryAlpha2,
            name = countryName,
            emoji = countryEmoji,
            currency = countryCurrency,
            latitude = countryLatitude,
            longitude = countryLongitude
        ),
        number = Number(
            length = numberLength,
            luhn = numberLuhn
        )
    )

    companion object {
        fun MInfo.toEInfo() = EInfo(
            id = 0,
            dateTime = this.dateTime,
            scheme = this.scheme,
            type = this.type,
            brand = this.brand,
            prepaid = this.prepaid,
            bankName = this.bank?.name,
            bankUrl = this.bank?.url,
            bankPhone = this.bank?.phone,
            bankCity = this.bank?.city,
            countryNumeric = this.country?.numeric,
            countryAlpha2 = this.country?.alpha2,
            countryName = this.country?.name,
            countryEmoji = this.country?.emoji,
            countryCurrency = this.country?.currency,
            countryLatitude = this.country?.latitude,
            countryLongitude = this.country?.longitude,
            numberLength = this.number?.length,
            numberLuhn = this.number?.luhn
        )
    }
}
