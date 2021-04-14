package com.pixiedia.pixicommerce.utils.intents

import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityOptionsCompat
import androidx.core.app.ShareCompat
import androidx.core.util.Pair
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.pixiedia.pixicommerce.ui.WebActivity.WebActivity
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.pixiedia.pixicommerce.R
import com.pixiedia.pixicommerce.utils.ktx.doSafely
import com.pixiedia.pixicommerce.utils.ktx.isUrl

fun Fragment.makeSafeCall(phoneNumber: String?) {
    phoneNumber?.let {
        doSafely {
            context?.startActivity(
                Intent(Intent.ACTION_VIEW).setData(Uri.parse("tel:$phoneNumber"))
            )
        }
    }
}

fun Fragment.openMobileMap(location: String?) {
    location?.let {
        doSafely {
            context?.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(location)
                )
            )
        }
    }
}

fun FragmentActivity.makeSafeCall(phoneNumber: String?) {
    phoneNumber?.let {
        doSafely {
            startActivity(Intent(Intent.ACTION_CALL, Uri.parse("tel:$phoneNumber")))
        }
    }
}

fun Fragment.sendEmailTo(email: String?) {
    email?.let {
        doSafely {
            ShareCompat.IntentBuilder.from(requireActivity())
                .setType("message/rfc822")
                .addEmailTo(email)
                .setSubject("")
                .setText("")
                .setChooserTitle("")
                .startChooser()
        }
    }
}

fun FragmentActivity.sendEmailTo(email: String?) {
    email?.let {
        doSafely {
            ShareCompat.IntentBuilder.from(this)
                .setType("message/rfc822")
                .addEmailTo(email)
                .setSubject("")
                .setText("")
                .setChooserTitle("")
                .startChooser()
        }
    }
}

/**
 * opens an activity that share the same transitionName in xml attrs with
 * the starter activity
 */
fun <T> Fragment.openActivityWithTransition(
    to: Class<*>?,
    data: T? = null,
    vararg animatedViews: View,
    finish: Boolean = false,
    finishAffinity: Boolean = false
) {

    activity?.let { activity ->
        if (animatedViews.isNotEmpty()) {

            // get images and their transition names into a list
            var viewsAndTransitionNamesList: ArrayList<Pair<View, String>> = arrayListOf()
            for (view in animatedViews) {
                viewsAndTransitionNamesList.add(
                    Pair(view, (ViewCompat.getTransitionName(view) ?: "empty_transition_name"))
                )
            }

            // create the option
            val options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity,
                *viewsAndTransitionNamesList.toTypedArray()
            )

            val intent = Intent(activity.applicationContext, to)
            if (data != null) {
                intent.putExtraJson(data)
            }

            // start the activity
            activity.startActivity(
                intent,
                options.toBundle()
            )

            // finish if needed
            if (finishAffinity)
                activity.finishAffinity()
            if (finish)
                activity.finish()
        }
    }
}

/**
 *  open an Activity with the ability of finishing and finishAffinity
 *  From an Activity
 */
fun FragmentActivity.openActivity(
    to: Class<*>?,
    finish: Boolean = false,
    finishAffinity: Boolean = false,
    outerIntent: Intent? = null
) {
    val intent = Intent(this, to)
    outerIntent?.extras?.let {
        intent.putExtras(it)
    }
    startActivity(intent)

    if (finishAffinity)
        this.finishAffinity()
    if (finish)
        this.finish()
}

/**
 * opens an activity that share the same transitionName in xml attrs with
 * the starter activity
 */
fun FragmentActivity.openActivityWithTransition(
    to: Class<*>?,
    animatedView: View,
    finish: Boolean = false,
    finishAffinity: Boolean = false
) {

    startActivity(
        Intent(this, to),
        ActivityOptionsCompat.makeSceneTransitionAnimation(
            this,
            animatedView,
            ViewCompat.getTransitionName(animatedView)!!
        ).toBundle()
    )

    if (finishAffinity)
        this.finishAffinity()
    if (finish)
        this.finish()
}

/**
 *  open an Activity with the ability of finishing and finishAffinity
 *  From a Fragment
 */

fun Fragment.openActivity(to: Class<*>?, finish: Boolean = false, finishAffinity: Boolean = false) {
    activity?.let {
        startActivity(Intent(it, to))

        if (finishAffinity)
            it.finishAffinity()
        if (finish)
            it.finish()
    }
}

/**
 *  opens WebActivity with URL if is valid url else shown a toast with invalid url message
 *  From a Fragment
 */
fun Fragment.openUrlInApp(to: String) {
    if (to.isUrl()) {
        activity?.let {
            startActivity(
                Intent(this.context, WebActivity::class.java)
                    .putExtra(WebActivity.URL, to)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            )
        }
    } else Toast.makeText(context, getString(R.string.invalid_url), Toast.LENGTH_SHORT).show()
}

/**
 *  opens WebActivity with URL if is valid url else shown a toast with invalid url message
 *  From an Activity
 */
fun FragmentActivity.openUrlInApp(to: String) {
    if (to.isUrl())
        startActivity(
            Intent(this, WebActivity::class.java)
                .putExtra(WebActivity.URL, to)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    else Toast.makeText(applicationContext, getString(R.string.invalid_url), Toast.LENGTH_SHORT)
        .show()
}

fun FragmentActivity.openAppInStore(appId: String) {
    if (appId.isNotEmpty()) {

        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(appId)
            ).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        )
    }
}

object IntentUtil {
    @Suppress("SpellCheckingInspection")
    val gson: Gson = GsonBuilder().create()
}

fun Intent.putExtraJson(name: String, src: Any) {
    putExtra(name, IntentUtil.gson.toJson(src))
}

const val DEFAULT_NAME = "CONST_NAME_FOR_VALUES"

fun Intent.putExtraJson(src: Any) {
    putExtra(DEFAULT_NAME, IntentUtil.gson.toJson(src))
}

fun <T> Intent.getJsonExtra(name: String, `class`: Class<T>): T? {
    val stringExtra = getStringExtra(name)
    if (stringExtra != null) {
        return IntentUtil.gson.fromJson<T>(stringExtra, `class`)
    }
    return null
}

fun <T> Intent.getJsonExtra(`class`: Class<T>): T? {
    val stringExtra = getStringExtra(DEFAULT_NAME)
    if (stringExtra != null) {
        return IntentUtil.gson.fromJson<T>(stringExtra, `class`)
    }
    return null
}
