package com.esmaeel.usecases.domain.repositories

import com.esmaeel.usecases.common.base.BaseRepository
import com.esmaeel.usecases.ui.userFeatures.User
import com.esmaeel.usecases.data.local.db.ILocalUserDataSource
import com.esmaeel.usecases.data.mapToLocalUsers
import com.esmaeel.usecases.data.mapToUsers
import com.esmaeel.usecases.data.remote.IRemoteUserDataSource
import com.esmaeel.usecases.di.ContextProviders
import kotlinx.coroutines.flow.Flow

class UsersRepository(
    private val localUserDataSource: ILocalUserDataSource,
    private val remoteUserDataSource: IRemoteUserDataSource,
    contextProviders: ContextProviders
) : BaseRepository(contextProviders), IUsersRepository {


    override fun getUsers(): Flow<List<User>> = networkHandler {

        // get network data
        val networkUsers = remoteUserDataSource.getUsers()

        // cache users
        // return all users from cache
        with(localUserDataSource) {
            saveUsers(*networkUsers.mapToLocalUsers().toTypedArray())
            getCachedUsers().mapToUsers()
        }

    }

}