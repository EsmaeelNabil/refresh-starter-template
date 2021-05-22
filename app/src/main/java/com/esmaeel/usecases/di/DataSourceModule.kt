package com.esmaeel.usecases.di

import com.esmaeel.usecases.data.local.db.ILocalUserDataSource
import com.esmaeel.usecases.data.local.db.LocalUserDataSource
import com.esmaeel.usecases.data.local.db.UsersDao
import com.esmaeel.usecases.data.remote.IRemoteUserDataSource
import com.esmaeel.usecases.data.remote.RemoteUserDataSource
import com.esmaeel.usecases.data.remote.UsersService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DataSourceModule {

    @Provides
    @Singleton
    fun provideLocalDataSource(usersDao: UsersDao): ILocalUserDataSource {
        return LocalUserDataSource(usersDao)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(usersService: UsersService): IRemoteUserDataSource {
        return RemoteUserDataSource(usersService)
    }

}