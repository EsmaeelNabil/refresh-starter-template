package com.pixiedia.pixicommerce.utils.ktx

import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

fun FragmentActivity.appLocal(): String {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        applicationContext.resources.configuration.locales[0].toString()
    } else {
        applicationContext.resources.configuration.locale.toString()
    }
}

fun Fragment.appLocal(): String {
    context?.let {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            context!!.resources.configuration.locales[0].toString()
        } else {
            context!!.resources.configuration.locale.toString()
        }
    }
    return "ar" // default language
}

/**
 *  return true if app local is "ar"
 */
fun Fragment.isAr(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        context!!.resources.configuration.locales[0].toString().contains("ar", true)
    } else {
        context!!.resources.configuration.locale.toString().contains("ar", true)
    }
}

/**
 *  return true if app local is "ar"
 */
fun FragmentActivity.isAr(): Boolean {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        applicationContext.resources.configuration.locales[0].toString().contains("ar", true)
    } else {
        applicationContext.resources.configuration.locale.toString().contains("ar", true)
    }
}

fun Context.getLocalLanguage(): String? {
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        this.resources.configuration.locales[0].toString()
    } else {
        this.resources.configuration.locale.toString()
    }
}
