package com.pixiedia.pixicommerce.ui.main.splash

import com.pixiedia.pixicommerce.data.remote.models.AppInitResponse
import com.pixiedia.pixicommerce.ui.base.ViewState

sealed class SplashViewState() : ViewState() {
    data class OnAppInitResponse(val response: AppInitResponse) :
        Loaded<AppInitResponse>(response)
}