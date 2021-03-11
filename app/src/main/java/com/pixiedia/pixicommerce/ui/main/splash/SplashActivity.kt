package com.pixiedia.pixicommerce.ui.main.splash

import android.content.Intent
import com.pixiedia.pixicommerce.R
import com.pixiedia.pixicommerce.data.local.db.LocalDataSource
import com.pixiedia.pixicommerce.data.remote.models.AppInitResponse
import com.pixiedia.pixicommerce.data.repositories.AppRepository
import com.pixiedia.pixicommerce.databinding.SplashActivityBinding
import com.pixiedia.pixicommerce.ui.base.BaseActivityWithBusiness
import com.pixiedia.pixicommerce.ui.base.ViewState
import com.pixiedia.pixicommerce.ui.main.onBoard.OnBoardActivity
import org.koin.android.ext.android.inject
import timber.log.Timber

class SplashActivity :
    BaseActivityWithBusiness<SplashActivityBinding,
            SplashViewModel>(R.layout.splash_activity) {

    override val viewModel: SplashViewModel by inject()

    val localDataSourceImpl: LocalDataSource by inject()
    val appRepository: AppRepository by inject()
    private val TAG = "SplashActivity"

    override fun setup() {
        viewModel.getAppInit()

        binder.btn.setOnClickListener {
            startActivity(Intent(this@SplashActivity, OnBoardActivity::class.java))
        }
    }

    override fun render(state: ViewState) {
        when (state) {
            is SplashViewState.OnAppInitResponse -> handleAppInit(state.response)
        }
    }

    private fun handleAppInit(response: AppInitResponse) {
        Timber.e(response.toString())
    }
}