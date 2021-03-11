package com.pixiedia.pixicommerce.ui.main.onBoard

import com.pixiedia.pixicommerce.data.repositories.AppRepository
import com.pixiedia.pixicommerce.ui.base.BaseViewModel
import com.pixiedia.pixicommerce.utils.ContextProviders

class OnBoardViewModel(
    private val repository: AppRepository,
    contextProviders: ContextProviders
) : BaseViewModel(contextProviders) {

    fun getAppInit() = launchBlock {
        repository.getAppInit()
//            .collect {
//                setState(UserProfileViewState.OnUserResponse(it))
//            }
    }
}