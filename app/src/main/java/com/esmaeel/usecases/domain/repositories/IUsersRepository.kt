package com.esmaeel.usecases.domain.repositories

import com.esmaeel.usecases.ui.userFeatures.User
import kotlinx.coroutines.flow.Flow

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

interface IUsersRepository {
    fun getUsers(): Flow<List<User>>
}