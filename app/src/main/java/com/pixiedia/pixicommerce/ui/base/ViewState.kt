package com.pixiedia.pixicommerce.ui.base


abstract class ViewState {
    object Initial : ViewState()
    abstract class UnitLoading() : ViewState()
    object DefaultLoading : UnitLoading()
    abstract class Loaded<out T>(val result: T) : ViewState()
    object Empty : ViewState()
    data class Error(val error: String?) : ViewState()
}
