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

    protected fun setState(state: ViewState) {
        internalState.value = state
    }

    val state: StateFlow<ViewState> = internalState

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        internalState.value = ViewState.Error(throwable.message)
    }

    fun launchBlock(emitLoading: Boolean = true, block: suspend CoroutineScope.() -> Unit) {
        if (emitLoading)
            setState(ViewState.Loading)
        viewModelScope.launch(contextProvider.Main + coroutineExceptionHandler) {
            block.invoke(this)
        }
    }
}
