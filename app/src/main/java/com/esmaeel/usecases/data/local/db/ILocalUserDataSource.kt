package com.esmaeel.usecases.data.local.db

import com.esmaeel.usecases.data.local.models.UserLocalEntity

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

interface ILocalUserDataSource {
    suspend fun saveUsers(vararg user: UserLocalEntity)
    suspend fun getCachedUsers(): List<UserLocalEntity>
}

