package com.camelsoft.tzzft.domain.use_cases.use_case_room

import com.camelsoft.tzzft.domain.api.IRoom
import com.camelsoft.tzzft.domain.models.MInfo
import com.camelsoft.tzzft.presentation.api.IStorageApi

class UseCaseStorageImpl(private val iRoom: IRoom): IStorageApi {
    override suspend fun insertInfo(mInfo: MInfo) {
        iRoom.insertInfo(mInfo = mInfo)
    }

    override suspend fun deleteInfo(mInfo: MInfo) {
        iRoom.deleteInfo(mInfo = mInfo)
    }

    override suspend fun updateInfo(mInfo: MInfo) {
        iRoom.updateInfo(mInfo = mInfo)
    }

    override suspend fun getAllInfo(): List<MInfo> {
        return iRoom.getAllInfo()
    }
}
