package com.pixiedia.pixicommerce

import coil.Coil
import coil.ImageLoader
import com.akexorcist.localizationactivity.ui.LocalizationApplication
import com.google.firebase.FirebaseApp
import com.pixiedia.pixicommerce.data.repositories.FirestoreListener
import com.pixiedia.pixicommerce.di.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
import java.util.*

class AppInstance : LocalizationApplication() {

    private val imageLoader: ImageLoader by inject()
    private val firestoreListener: FirestoreListener by inject()

    override fun getDefaultLanguage(): Locale {
        return Locale.getDefault()
    }

    override fun onCreate() {
        super.onCreate()
        initLogging()
        initKoin()
        initCoil()
        initFireStoreRemoteConfig()
    }

    private fun initFireStoreRemoteConfig() {
        FirebaseApp.initializeApp(applicationContext)
        firestoreListener.registerRemoteConfigUpdate(localDatabaseAutoUpdate = true)
    }

    private fun initCoil() {
        // to support loading Gif images
        Coil.setImageLoader(imageLoader)
    }

    private fun initLogging() {
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
    }

    private fun initKoin() {
        startKoin {
            androidLogger(Level.DEBUG)
            androidContext(this@AppInstance)
            modules(
                listOf(
                    repositoryModule,
                    viewModelModule,
                    firestoreModule,
                    encryptedPrefsModule,
                    AppLocalDatabaseModule,
                    networkModule,
                    apisModule,
                    generalModule
                )
            )
        }
    }
}