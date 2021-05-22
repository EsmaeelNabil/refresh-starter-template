package com.esmaeel.usecases.di

import com.esmaeel.usecases.data.local.db.ILocalUserDataSource
import com.esmaeel.usecases.data.remote.IRemoteUserDataSource
import com.esmaeel.usecases.domain.repositories.IUsersRepository
import com.esmaeel.usecases.domain.repositories.UsersRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideUsersRepository(
        localUserDataSource: ILocalUserDataSource,
        remoteUserDataSource: IRemoteUserDataSource,
        contextProviders: ContextProviders
    ): IUsersRepository =
        UsersRepository(localUserDataSource, remoteUserDataSource, contextProviders)
}
