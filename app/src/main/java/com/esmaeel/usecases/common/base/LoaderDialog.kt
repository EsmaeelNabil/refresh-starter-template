package com.esmaeel.usecases.common.base

import android.app.Activity
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import androidx.appcompat.app.AlertDialog
import com.esmaeel.usecases.R
import com.wang.avi.AVLoadingIndicatorView

class LoaderDialog private constructor(internal var activity: Activity) {

    internal var isShowing = false
    internal var loadingView: AVLoadingIndicatorView? = null
    internal var alertDialog: AlertDialog? = null

    fun show(): LoaderDialog {
        if (!isShowing && alertDialog != null) {
            isShowing = true
            try {
                alertDialog!!.window!!.setBackgroundDrawable(ColorDrawable(0))
            } catch (e: Exception) {
            }

            alertDialog!!.show()
            if (loadingView != null)
                loadingView!!.smoothToShow()
        }
        return this
    }

    fun hide(): LoaderDialog {
        isShowing = false
        if (loadingView != null)
            loadingView!!.smoothToHide()
        if (alertDialog != null)
            alertDialog!!.dismiss()
        return this
    }

    companion object {

        operator fun get(activity: Activity): LoaderDialog {
            val dialog = LoaderDialog(activity)
            val builder = AlertDialog.Builder(dialog.activity)
            val v = LayoutInflater.from(dialog.activity).inflate(R.layout.loading_view, null)
            dialog.loadingView = v.findViewById(R.id.loading_view)
            builder.setView(v)
            builder.setCancelable(false)
            dialog.alertDialog = builder.create()
            return dialog
        }
    }
}
