package com.camelsoft.tzzft._common.di

import com.camelsoft.tzzft.data.net.HeaderInterceptor
import com.camelsoft.tzzft.data.net.NetApi
import com.camelsoft.tzzft.data.net.RetroMy
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModuleSingl {

    @Provides
    @Singleton
    fun provideHeaderInterceptor(): HeaderInterceptor {
        return HeaderInterceptor
    }

    @Provides
    @Singleton
    fun provideNetApi(headerInterceptor: HeaderInterceptor): NetApi {
        val retroMy = RetroMy(headerInterceptor = headerInterceptor)
        return retroMy.makeRetrofit().create(NetApi::class.java)
    }
}
