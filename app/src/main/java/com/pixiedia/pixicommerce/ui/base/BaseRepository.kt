package com.pixiedia.pixicommerce.ui.base


import com.pixiedia.pixicommerce.data.remote.models.ErrorModel
import com.pixiedia.pixicommerce.utils.ContextProviders
import com.pixiedia.pixicommerce.utils.ResourcesHandler
import com.google.gson.Gson
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException


abstract class BaseRepository(private val contextProviders: ContextProviders) : KoinComponent {

    private val resourcesHandler: ResourcesHandler by inject()
    open val UNKNOWN_ERROR = /*resourcesHandler.getString(R.string.error)*/ "UNKNOWN_ERROR"
    val NETWORK_ERROR = /*resourcesHandler.getString(R.string.internet_error_message)*/
        "NETWORK_ERROR"
    val NETWORK_ERROR_TIMEOUT = /*resourcesHandler.getString(R.string.time_out_message)*/
        "NETWORK_TIME_OUT_ERROR"

    fun <T> networkHandler(fetch: suspend () -> T) = flow {
        try {
            emit(fetch.invoke())
        } catch (throwable: Throwable) {
            when (throwable) {
                is TimeoutCancellationException -> {
                    throw Exception(NETWORK_ERROR_TIMEOUT)
                }
                is IOException -> {
                    throw Exception(NETWORK_ERROR)
                }
                is HttpException -> {
                    throw Exception(getErrorFrom(throwable))
                }
                else -> {
                    throw Exception(throwable.message)
                }
            }
        }

    }.flowOn(contextProviders.IO)

    private fun getErrorFrom(throwable: HttpException): String {
        return try {
            Gson().fromJson(
                throwable.response()?.errorBody()?.string(),
                ErrorModel::class.java
            ).message
        } catch (exception: Exception) {
            Timber.e(exception)
            UNKNOWN_ERROR
        }
    }
}
