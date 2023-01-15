package com.camelsoft.tzzft.data.room

import androidx.room.*

@Dao
interface DaoRoom {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInfo(eInfo: EInfo)

    @Delete
    suspend fun deleteInfo(eInfo: EInfo)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateInfo(eInfo: EInfo)

    @Query("SELECT * FROM table_info")
    suspend fun getAllInfo(): List<EInfo>
}
