package com.camelsoft.tzzft.domain.api

import com.camelsoft.tzzft.domain.models.MInfo

interface IRoom {
    suspend fun insertInfo(mInfo: MInfo)
    suspend fun deleteInfo(mInfo: MInfo)
    suspend fun updateInfo(mInfo: MInfo)
    suspend fun getAllInfo(): List<MInfo>
}
