package com.pixiedia.pixicommerce.ui.main.splash

import com.pixiedia.pixicommerce.data.repositories.AppRepository
import com.pixiedia.pixicommerce.ui.base.BaseViewModel
import com.pixiedia.pixicommerce.utils.ContextProviders
import kotlinx.coroutines.flow.collect

class SplashViewModel(
    private val appRepository: AppRepository,
    contextProviders: ContextProviders
) : BaseViewModel(contextProviders) {

    fun getAppInit() = launchBlock {
        appRepository
            .getAppInit()
            .collect {
                setState(SplashViewState.OnAppInitResponse(it))
            }
    }
}