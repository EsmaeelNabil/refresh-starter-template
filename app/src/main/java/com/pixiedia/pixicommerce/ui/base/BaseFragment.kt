package com.pixiedia.pixicommerce.ui.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.collect

abstract class BaseFragment<T : ViewDataBinding, VM : BaseViewModel>(private val layoutId: Int) :
    Fragment() {

    protected abstract val viewModel: VM

    /**
     * // in case we needed access to the views
     * override lateinit var binder: T
     */
    lateinit var binder: T

    private lateinit var loader: LoaderDialog

    /*abstract fun getLayoutId(): Int*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: T = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )
        binder = binding
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.let {
            loader = LoaderDialog[it]
        }

        setup()
        lifecycleScope.launchWhenStarted {
            viewModel.state.collect {
                baseRender(it)
            }
        }
    }

    abstract fun setup()

    private fun baseRender(state: ViewState) {
        when (state) {
            is ViewState.Initial -> hideLoading()
            is ViewState.DefaultLoading -> showLoading()
            is ViewState.Error -> showError(state.error)
            else -> {
                hideLoading()
                render(state)
            }
        }
    }

    abstract fun render(state: ViewState)

    // not private for the sake of overriding in case of custom implementation for specific screens
    open fun showLoading() {
        loader.show()
    }

    // not private for the sake of overriding in case of custom implementation for specific screens
    open fun hideLoading() {
        loader.hide()
    }

    open fun showError(errorModel: String?) {
        hideLoading()
        Toast.makeText(context, errorModel, Toast.LENGTH_SHORT).show()
    }
}
