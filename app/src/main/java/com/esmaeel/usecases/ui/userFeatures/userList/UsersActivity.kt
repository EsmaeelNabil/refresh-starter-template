package com.esmaeel.usecases.ui.userFeatures.userList

import androidx.activity.viewModels
import com.esmaeel.usecases.R
import com.esmaeel.usecases.common.base.BaseActivityWithBusiness
import com.esmaeel.usecases.common.base.ViewState
import com.esmaeel.usecases.databinding.UsersActivityBinding
import com.esmaeel.usecases.ui.userFeatures.User
import com.esmaeel.usecases.utils.intents.openAppInStore
import com.esmaeel.usecases.utils.ktx.showToast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UsersActivity :
    BaseActivityWithBusiness<UsersActivityBinding, UsersViewModel>(R.layout.users_activity) {

    override val viewModel: UsersViewModel by viewModels()
    lateinit var usersListAdapter: UsersListAdapter

    override fun setup() {
        usersListAdapter = UsersListAdapter { item: User, _: Int ->
            showToast(item.name)
        }
        binder.recycler.adapter = usersListAdapter
        viewModel.getUsersList()
    }

    override fun render(state: ViewState) {
        when (state) {
            is UsersListViewState.OnUserListResponse -> bindUsersList(state.response)
        }
    }

    private fun bindUsersList(response: List<User>) {
        usersListAdapter.submitList(response)
    }

}