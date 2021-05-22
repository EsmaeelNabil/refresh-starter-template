package com.esmaeel.usecases.data.remote

import com.esmaeel.usecases.data.remote.models.UserNetworkEntity

class RemoteUserDataSource(private val usersService: UsersService) : IRemoteUserDataSource {

    override suspend fun saveUsers(vararg user: UserNetworkEntity) {}

    override suspend fun getUsers(): List<UserNetworkEntity> = usersService.getUsers()


}