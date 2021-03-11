package com.pixiedia.pixicommerce.data.local.encryptedSharedPrefs

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.pixiedia.pixicommerce.common.Constants


class PreferenceManagerImpl(applicationContext: Context) : PreferenceManager {

    var prefs: SharedPreferences

    init {
        val mainKey = MasterKey.Builder(applicationContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        prefs = EncryptedSharedPreferences.create(
            applicationContext,
            Constants.PIXI_PREFS,
            mainKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

    }

    override fun saveUserToken(userToken: String) = prefs.edit {
        putString(USER_TOKEN, userToken)
    }

    override fun getUserToken(): String =
        prefs.getString(USER_TOKEN, DEFUALT_VALUE) ?: DEFUALT_VALUE

    override fun saveAdminToken(adminToken: String) = prefs.edit {
        putString(ADMIN_TOKEN, adminToken)
    }

    override fun getAdminToken(): String =
        prefs.getString(ADMIN_TOKEN, DEFUALT_VALUE) ?: DEFUALT_VALUE

    override fun saveFirstOpenApp(isFirstOpen: Boolean) = prefs.edit {
        putBoolean(IS_FIRST_OPEN, isFirstOpen)
    }

    override fun isFirstOpenApp(): Boolean = prefs.getBoolean(IS_FIRST_OPEN, false)

    override fun saveGuestToken(guestToken: String) = prefs.edit {
        putString(GUEST_TOKEN, guestToken)
    }

    override fun getGuestToken(): String =
        prefs.getString(GUEST_TOKEN, DEFUALT_VALUE) ?: DEFUALT_VALUE

    companion object {
        const val DEFUALT_VALUE = "EMPTY_VALUE"
        const val IS_FIRST_OPEN = "IS_FIRST_OPEN"
        const val USER_TOKEN = "USER_TOKEN"
        const val GUEST_TOKEN = "GUEST_TOKEN"
        const val ADMIN_TOKEN = "ADMIN_TOKEN"
    }


}