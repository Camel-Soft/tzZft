package com.camelsoft.tzzft.data.net

import com.camelsoft.tzzft._common.Constants.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

class RetroMy (private val headerInterceptor: HeaderInterceptor) {
    fun makeRetrofit(): Retrofit {
        try {
//            val httpLoggingInterceptor = HttpLoggingInterceptor()
//            httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY

            val okHttpClient = OkHttpClient.Builder()
                .connectTimeout(3, TimeUnit.SECONDS)
                .callTimeout(3, TimeUnit.SECONDS)
                .followRedirects(true)
                .followSslRedirects(true)
                .hostnameVerifier { hostName, sslSession -> hostName.equals(sslSession.peerHost) }
//                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(headerInterceptor)
                .build()

            val json = Json {
                ignoreUnknownKeys = true
            }

            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(json.asConverterFactory("application/json".toMediaType()))
                .client(okHttpClient)
                .build()
        }
        catch (e: Exception) {
            e.printStackTrace()
            throw Exception("[RetroMy.makeRetrofit] ${e.message}")
        }
    }
}
