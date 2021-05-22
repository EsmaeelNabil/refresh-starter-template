package com.esmaeel.usecases.ui.userFeatures.userList

import com.esmaeel.usecases.common.base.BaseViewModel
import com.esmaeel.usecases.domain.usecases.users.GetUsersUsecase
import com.esmaeel.usecases.di.ContextProviders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class UsersViewModel @Inject constructor(
    private val getUsersUsecase: GetUsersUsecase,
    contextProviders: ContextProviders
) : BaseViewModel(contextProviders) {

    fun getUsersList() = launchBlock {
        getUsersUsecase.invoke().collect {
            setState(UsersListViewState.OnUserListResponse(it))
        }
    }
}