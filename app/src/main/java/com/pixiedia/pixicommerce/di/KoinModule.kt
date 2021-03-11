package com.pixiedia.pixicommerce.di

import android.content.Context
import android.os.Build
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pixiedia.pixicommerce.BuildConfig
import com.pixiedia.pixicommerce.data.local.db.AppLocalDatabase
import com.pixiedia.pixicommerce.data.local.db.LocalDataSource
import com.pixiedia.pixicommerce.data.local.db.LocalDataSourceImpl
import com.pixiedia.pixicommerce.data.local.encryptedSharedPrefs.PreferenceManager
import com.pixiedia.pixicommerce.data.local.encryptedSharedPrefs.PreferenceManagerImpl
import com.pixiedia.pixicommerce.data.remote.ADMIN_TOKEN_REQUIRED
import com.pixiedia.pixicommerce.data.remote.AppService
import com.pixiedia.pixicommerce.data.remote.GUEST_TOKEN_REQUIRED
import com.pixiedia.pixicommerce.data.remote.USER_TOKEN_REQUIRED
import com.pixiedia.pixicommerce.data.repositories.FirestoreListener
import com.pixiedia.pixicommerce.utils.ContextProviders
import com.pixiedia.pixicommerce.utils.ResourcesHandler
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Invocation
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val generalModule = module {
    single { ResourcesHandler(get()) }
    single { ContextProviders() }

    single {
        ImageLoader
            .Builder(get())
            .componentRegistry {
                if (Build.VERSION.SDK_INT >= 28) {
                    add(ImageDecoderDecoder())
                } else {
                    add(GifDecoder())
                }
            }.build()
    }

}

val firestoreModule = module {
    fun provideFirestoreListener(
        gson: Gson,
        localDataSource: LocalDataSource
    ): FirestoreListener {
        return FirestoreListener(gson, localDataSource)
    }

    single { provideFirestoreListener(get(), get()) }
}

val encryptedPrefsModule = module {
    fun providePrefsManager(context: Context): PreferenceManager {
        return PreferenceManagerImpl(context)
    }
    single { providePrefsManager(get()) }
}

val AppLocalDatabaseModule = module {

    fun provideAppLocalDatabase(context: Context): AppLocalDatabase {
        return AppLocalDatabase.getAppDataBase(context)
    }

    fun provideLocalDataSource(appLocalDatabase: AppLocalDatabase, gson: Gson): LocalDataSource {
        return LocalDataSourceImpl(appLocalDatabase, gson)
    }

    single { provideLocalDataSource(get(), get()) }

    single { provideAppLocalDatabase(get()) }


}


val apisModule = module {

    fun provideAppService(retrofit: Retrofit): AppService {
        return retrofit.create(AppService::class.java)
    }

    single { provideAppService(get()) }
}

val networkModule = module {

    fun provideGson() = GsonBuilder().create()
    single { provideGson() }

    fun provideRetrofit(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(client)
            .build()
    }

    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    fun provideKeysInjectionInterceptor(prefs: PreferenceManager): Interceptor {
        return Interceptor { chain: Interceptor.Chain ->
            val original = chain.request()
            val method = chain.request().tag(Invocation::class.java)!!.method()
            val builder = original.newBuilder()

            when {
                method.isAnnotationPresent(ADMIN_TOKEN_REQUIRED::class.java) -> {
                    builder.addHeader(BuildConfig.TOKEN_HEADER_NAME, prefs.getAdminToken())
                }
                method.isAnnotationPresent(USER_TOKEN_REQUIRED::class.java) -> {
                    builder.addHeader(BuildConfig.TOKEN_HEADER_NAME, prefs.getUserToken())
                }
                method.isAnnotationPresent(GUEST_TOKEN_REQUIRED::class.java) -> {
                    builder.addHeader(BuildConfig.TOKEN_HEADER_NAME, prefs.getGuestToken())
                }
            }

            chain.proceed(
                builder.method(original.method, original.body)
                    .build()
            )
        }
    }

    fun provideHttpClient(
        logger: HttpLoggingInterceptor,
        keysInjectionInterceptor: Interceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(keysInjectionInterceptor)
            .addInterceptor(logger)
            .build()
    }

    single { provideRetrofit(get(), get()) }
    single { provideHttpClient(get(), get()) }
    single { provideLoggingInterceptor() }
    single { provideKeysInjectionInterceptor(get()) }
}
