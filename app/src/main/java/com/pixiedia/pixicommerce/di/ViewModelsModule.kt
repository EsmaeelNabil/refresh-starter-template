package com.pixiedia.pixicommerce.di

import com.pixiedia.pixicommerce.ui.main.onBoard.OnBoardViewModel
import com.pixiedia.pixicommerce.ui.main.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { OnBoardViewModel(get(), get()) }
    viewModel { SplashViewModel(get(), get()) }
}
