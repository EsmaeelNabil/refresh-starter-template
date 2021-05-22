package com.esmaeel.usecases.domain.usecases.users

import com.esmaeel.usecases.ui.userFeatures.User
import com.esmaeel.usecases.domain.repositories.IUsersRepository
import com.esmaeel.usecases.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetUsersUsecase @Inject constructor(
    private val usersRepository: IUsersRepository
) : UseCase<Flow<List<User>>> {

    override fun invoke(): Flow<List<User>> = usersRepository.getUsers()

}