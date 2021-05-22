package com.esmaeel.usecases.ui.userFeatures.userList

import com.esmaeel.usecases.common.base.ViewState
import com.esmaeel.usecases.ui.userFeatures.User

sealed class UsersListViewState() : ViewState() {
    data class OnUserListResponse(val response: List<User>) : Loaded<List<User>>(response)
}