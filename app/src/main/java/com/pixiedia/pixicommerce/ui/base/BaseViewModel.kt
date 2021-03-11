package com.pixiedia.pixicommerce.ui.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixiedia.pixicommerce.utils.ContextProviders
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

abstract class BaseViewModel(private val contextProvider: ContextProviders) : ViewModel() {

    private val internalState = MutableStateFlow<ViewState>(ViewState.Initial)

    fun setState(state: ViewState) {
        internalState.value = state
    }

    val state: StateFlow<ViewState> = internalState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        internalState.value = ViewState.Error(throwable.message)
    }

    fun launchBlock(
        emitDefaultLoading: Boolean = false,
        unitLoading: ViewState = ViewState.Initial,
        block: suspend CoroutineScope.() -> Unit
    ) {
        // emits either default loading or specific component loading if default loading is false
        if (emitDefaultLoading)
            setState(if (emitDefaultLoading) ViewState.DefaultLoading else unitLoading)

        viewModelScope.launch(contextProvider.Main + coroutineExceptionHandler) {
            block.invoke(this)
        }
    }
}
