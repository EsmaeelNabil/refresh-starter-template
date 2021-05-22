package com.esmaeel.usecases.data.remote

import com.esmaeel.usecases.data.remote.models.UserNetworkEntity

interface IRemoteUserDataSource {
    suspend fun saveUsers(vararg user: UserNetworkEntity)
    suspend fun getUsers(): List<UserNetworkEntity>
}