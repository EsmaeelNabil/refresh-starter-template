package com.esmaeel.usecases.di

import android.content.Context
import com.esmaeel.usecases.data.local.db.UsersDao
import com.esmaeel.usecases.data.local.db.LocalDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext appContext: Context): LocalDatabase {
        return LocalDatabase.getLocalDatabase(appContext)
    }

    @Provides
    fun provideUsersDao(database: LocalDatabase): UsersDao {
        return database.usersDao()
    }

}