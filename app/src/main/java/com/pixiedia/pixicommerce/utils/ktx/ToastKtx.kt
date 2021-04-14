package com.pixiedia.pixicommerce.utils.ktx

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Fragment.showToast(message: String?) = message?.let {
    makeToast(requireContext(), message)
}

fun makeToast(context: Context, message: String?) =
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()

fun Activity.showToast(message: String?) = message?.let {
    makeToast(this, message)
}
