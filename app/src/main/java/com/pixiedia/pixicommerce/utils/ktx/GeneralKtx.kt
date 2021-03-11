package com.pixiedia.pixicommerce.utils.ktx

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.fragment.app.Fragment
import coil.Coil
import coil.request.CachePolicy
import coil.request.ErrorResult
import coil.request.ImageRequest
import coil.request.SuccessResult
import timber.log.Timber

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */
fun getBitmapFromUrlWithCoil(
    context: Context,
    url: String?,
    onStart: (() -> Unit?)? = null,
    onResponse: (isSuccessful: Boolean, error: String, bitmap: Bitmap?) -> Unit,
) {
    val imageLoader = Coil.imageLoader(context)
    onStart?.invoke()
    onIOThread {
        val request = ImageRequest.Builder(context)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .data(url)
            .allowHardware(false)
            .build()
        val response = imageLoader.execute(request)

        // network might not be working or slow, so an exception might been caught
        // so we check for if the drawable is null or not
        if (response.drawable != null) {
            val result = (response as SuccessResult).drawable
            val bitmap = (result as BitmapDrawable).bitmap
            onMainThread {
                onResponse.invoke(true, "", bitmap)
            }
        } else {
            // the exception has been caught so we cast the response to
            // ErrorResult that has the throwable of what happened
            val result = (response as ErrorResult).throwable
            onMainThread {
                val errorMessage = result.message ?: "Unknown Error"
                onResponse.invoke(false, errorMessage, null)
            }
        }
    }
}

fun doSafely(function: () -> Unit) {
    try {
        function.invoke()
    }catch (e:Exception){
        Timber.e(e)
    }
}

fun Fragment.getBitmapFromUrl(
    url: String?,
    onStart: () -> Unit,
    onResponse: (isSuccessful: Boolean, error: String, bitmap: Bitmap?) -> Unit,
) {
    getBitmapFromUrlWithCoil(requireContext(), url, onStart, onResponse)
}

fun Activity.getBitmapFromUrl(
    url: String?,
    onStart: () -> Unit,
    onResponse: (isSuccessful: Boolean, error: String, bitmap: Bitmap?) -> Unit,
) {
    getBitmapFromUrlWithCoil(applicationContext, url, onStart, onResponse)
}