package com.camelsoft.tzzft.data.net

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

object HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        return chain.proceed(request = addHeader(request))
    }

    private fun addHeader(request: Request): Request {
        return request.newBuilder()
            .header("Accept-Version", "3")
            .build()
    }
}
