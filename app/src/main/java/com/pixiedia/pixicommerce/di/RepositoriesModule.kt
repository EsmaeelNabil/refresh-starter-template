package com.pixiedia.pixicommerce.di

import com.pixiedia.pixicommerce.data.repositories.AppRepository
import org.koin.dsl.module


val repositoryModule = module {
    single { AppRepository(get(), get(), get(),get(),get()) }
}
