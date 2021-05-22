package com.esmaeel.usecases.di

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

@Singleton
open class ContextProviders @Inject constructor() {
    open val Main: CoroutineContext = Dispatchers.Main
    open val IO: CoroutineContext = Dispatchers.IO
}
