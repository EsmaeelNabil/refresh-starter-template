package com.esmaeel.usecases.di

import android.content.Context
import com.esmaeel.usecases.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ResourcesHandler @Inject constructor(@ApplicationContext val context: Context) {
    fun getString(stringId: Int) = context.getString(stringId)

    val UNKNOWN_ERROR = getString(R.string.error)
    val NETWORK_ERROR = getString(R.string.internet_error_message)
    val NETWORK_ERROR_TIMEOUT = getString(R.string.time_out_message)
}