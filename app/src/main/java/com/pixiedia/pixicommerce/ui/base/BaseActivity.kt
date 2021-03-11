package com.pixiedia.pixicommerce.ui.base

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.lifecycleScope
import com.akexorcist.localizationactivity.ui.LocalizationActivity
import kotlinx.coroutines.flow.collect


/**
 *  used in an activity with no heavy business logic
 */
abstract class BaseActivity : LocalizationActivity() {
    private lateinit var loader: LoaderDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loader = LoaderDialog[this]
    }

    open fun showLoading() {
        loader.show()
    }

    open fun hideLoading() {
        loader.hide()
    }

    open fun showError(errorModel: String?) {
        hideLoading()
        Toast.makeText(applicationContext, errorModel, Toast.LENGTH_SHORT).show()
    }
}

/**
 *  used in an activity with heavy business logic
 */
abstract class BaseActivityWithBusiness<T : ViewDataBinding, VM : BaseViewModel>(
    private val layoutId: Int
) : LocalizationActivity() {

    protected abstract val viewModel: VM

    /**
     *  in case we needed to access the views
     */
    lateinit var binder: T

    private lateinit var loader: LoaderDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binder = DataBindingUtil.setContentView(this, layoutId)
        loader = LoaderDialog[this]
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
            is ViewState.DefaultLoading -> showLoading()
            is ViewState.Initial -> hideLoading()
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
        Toast.makeText(applicationContext, errorModel, Toast.LENGTH_SHORT).show()
    }
}
