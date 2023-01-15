package com.camelsoft.tzzft._common.di

import com.camelsoft.tzzft.data.net.NetApi
import com.camelsoft.tzzft.domain.api.IRoom
import com.camelsoft.tzzft.domain.use_cases.use_case_info.UseCaseInfoImpl
import com.camelsoft.tzzft.domain.use_cases.use_case_room.UseCaseStorageImpl
import com.camelsoft.tzzft.presentation.api.IInfoApi
import com.camelsoft.tzzft.presentation.api.IStorageApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object DataModuleVm {

    @Provides
    @ViewModelScoped
    fun provideUseCaseInfo(netApi: NetApi): IInfoApi {
        return UseCaseInfoImpl(netApi = netApi)
    }

    @Provides
    @ViewModelScoped
    fun provideUseCaseStorage(iRoom: IRoom): IStorageApi {
        return UseCaseStorageImpl(iRoom = iRoom)
    }
}
