package com.esmaeel.usecases.data.local.db

import com.esmaeel.usecases.data.local.models.UserLocalEntity


class LocalUserDataSource constructor(private val userDao: UsersDao) : ILocalUserDataSource {

    override suspend fun saveUsers(vararg user: UserLocalEntity) = userDao.saveUsers(*user)

    override suspend fun getCachedUsers(): List<UserLocalEntity> = userDao.getUsers()

}