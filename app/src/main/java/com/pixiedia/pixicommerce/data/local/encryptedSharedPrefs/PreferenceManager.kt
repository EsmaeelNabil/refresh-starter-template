package com.pixiedia.pixicommerce.data.local.encryptedSharedPrefs

interface PreferenceManager {
    fun saveUserToken(userToken: String)
    fun getUserToken(): String

    fun saveGuestToken(guestToken: String)
    fun getGuestToken(): String

    fun saveAdminToken(adminToken: String)
    fun getAdminToken(): String

    fun saveFirstOpenApp(isFirstOpen: Boolean)
    fun isFirstOpenApp(): Boolean
}