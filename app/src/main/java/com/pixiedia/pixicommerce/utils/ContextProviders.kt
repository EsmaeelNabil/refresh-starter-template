package com.pixiedia.pixicommerce.utils

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

open class ContextProviders {
    open val Main: CoroutineContext = Dispatchers.Main
    open val IO: CoroutineContext = Dispatchers.IO
}
