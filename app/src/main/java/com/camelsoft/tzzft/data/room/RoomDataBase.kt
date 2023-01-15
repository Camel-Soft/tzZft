package com.camelsoft.tzzft.data.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    version = 1,
    entities = [
        EInfo::class
    ]
)
abstract class RoomDataBase : RoomDatabase() {
    abstract fun getDaoRoom(): DaoRoom
}
