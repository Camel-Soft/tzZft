package com.camelsoft.tzzft.data.net

import com.camelsoft.tzzft.domain.models.MInfo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface NetApi {
    @GET("/{bin}")
    suspend fun getResponseInfo(@Path("bin") bin: String): Response<MInfo>
}
