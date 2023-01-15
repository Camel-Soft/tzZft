package com.camelsoft.tzzft.data.room

import com.camelsoft.tzzft.data.room.EInfo.Companion.toEInfo
import com.camelsoft.tzzft.domain.api.IRoom
import com.camelsoft.tzzft.domain.models.MInfo

class RoomImpl(private val daoRoom: DaoRoom): IRoom {

    override suspend fun insertInfo(mInfo: MInfo) {
        daoRoom.insertInfo(mInfo.toEInfo())
    }

    override suspend fun deleteInfo(mInfo: MInfo) {
        daoRoom.deleteInfo(mInfo.toEInfo())
    }

    override suspend fun updateInfo(mInfo: MInfo) {
        daoRoom.updateInfo(mInfo.toEInfo())
    }

    override suspend fun getAllInfo(): List<MInfo> {
        return daoRoom.getAllInfo().map { it.toMInfo() }
    }
}
