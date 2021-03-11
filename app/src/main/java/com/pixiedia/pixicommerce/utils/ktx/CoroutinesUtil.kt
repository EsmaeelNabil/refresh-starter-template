package com.pixiedia.pixicommerce.utils.ktx

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

fun onMainThread(givenFunction: suspend (() -> Unit)) {
    CoroutineScope(Dispatchers.Main).launch {
        givenFunction()
    }
}

fun onIOThread(givenFunction: suspend (() -> Unit)) {
    CoroutineScope(Dispatchers.IO).launch {
        givenFunction()
    }
}