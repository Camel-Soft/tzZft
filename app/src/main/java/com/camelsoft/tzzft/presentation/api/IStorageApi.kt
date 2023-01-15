package com.camelsoft.tzzft.presentation.api

import com.camelsoft.tzzft.domain.models.MInfo

interface IStorageApi {
    suspend fun insertInfo(mInfo: MInfo)
    suspend fun deleteInfo(mInfo: MInfo)
    suspend fun updateInfo(mInfo: MInfo)
    suspend fun getAllInfo(): List<MInfo>
}
