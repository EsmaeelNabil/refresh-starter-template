package com.pixiedia.pixicommerce.ui.base


abstract class ViewState {
    object Initial : ViewState()
    object Loading : ViewState()
    data class Error(val error: String?) : ViewState()
    abstract class Loaded<out T>(val result: T) : ViewState()
    object Empty : ViewState()
}
