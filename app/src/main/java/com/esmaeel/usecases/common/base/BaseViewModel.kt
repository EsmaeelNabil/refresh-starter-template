package com.esmaeel.usecases.common.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.esmaeel.usecases.di.ContextProviders
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

abstract class BaseViewModel(private val contextProvider: ContextProviders) : ViewModel() {

    private val internalState = MutableStateFlow<ViewState>(ViewState.Initial)
    private val mutex = Mutex()

    protected fun setState(state: ViewState) {
        viewModelScope.launch {
            mutex.withLock {
                internalState.value = state
            }
        }
    }

    val state: StateFlow<ViewState> = internalState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        when (throwable) {
            is AuthException -> ViewState.AuthRequired
            else -> setState(ViewState.Error(throwable.message))
        }
    }

    fun launchBlock(emitLoading: Boolean = true, block: suspend CoroutineScope.() -> Unit) {
        if (emitLoading)
            setState(ViewState.Loading)
        viewModelScope.launch(contextProvider.Main + coroutineExceptionHandler) {
            block.invoke(this)
        }
    }
}
