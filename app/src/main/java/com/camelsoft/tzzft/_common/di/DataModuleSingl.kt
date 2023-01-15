package com.camelsoft.tzzft._common.di

import android.app.Application
import androidx.room.Room
import com.camelsoft.tzzft.data.net.HeaderInterceptor
import com.camelsoft.tzzft.data.net.NetApi
import com.camelsoft.tzzft.data.net.RetroMy
import com.camelsoft.tzzft.data.room.RoomDataBase
import com.camelsoft.tzzft.data.room.RoomImpl
import com.camelsoft.tzzft.domain.api.IRoom
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
    fun provideDataBase(app: Application): RoomDataBase {
        return Room.databaseBuilder(
            app,
            RoomDataBase::class.java,
            "db_room"
        ).build()
    }

    @Provides
    @Singleton
    fun provideRoom(db: RoomDataBase): IRoom {
        return RoomImpl(db.getDaoRoom())
    }

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
