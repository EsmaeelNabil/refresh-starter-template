package com.pixiedia.pixicommerce.data.repositories

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pixiedia.pixicommerce.BuildConfig
import com.pixiedia.pixicommerce.common.Constants
import com.pixiedia.pixicommerce.data.local.db.LocalDataSource
import com.pixiedia.pixicommerce.data.local.encryptedSharedPrefs.PreferenceManager
import com.pixiedia.pixicommerce.data.remote.AppService
import com.pixiedia.pixicommerce.ui.base.BaseRepository
import com.pixiedia.pixicommerce.utils.ContextProviders
import kotlinx.coroutines.flow.flow
import timber.log.Timber

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

class AppRepository(
    private val appService: AppService,
    private val localDataSource: LocalDataSource,
    private val preferenceManager: PreferenceManager,
    private val gson: Gson,
    contextProviders: ContextProviders
) : BaseRepository(contextProviders) {

    /**
     * get AppInit remote config from the server
     * then cache it.
     */
    fun getAppInit() = networkHandler {

        val response = appService.getAppInit(
            hashMapOf(
                Constants.USERNAME to BuildConfig.ADMIN_USERNAME,
                Constants.PASSWORD to BuildConfig.ADMIN_PASSWORD
            )
        )

        localDataSource.saveAppInitRemoteConfig(response)
        with(preferenceManager) {
            saveAdminToken(response[0].adminToken ?: "")
            saveGuestToken(response[0].guestQuote ?: "")
        }

        return@networkHandler response
    }

}


data class FireStoreRemoteConfig(
    val model: Model
)

data class Model(
    val algoliasearchCredentialsCredentialsApiKey: String,
    val algoliasearchCredentialsCredentialsApplicationId: String,
    val algoliasearchCredentialsCredentialsIndexPrefix: String,
    val algoliasearchCredentialsCredentialsSearchOnlyApiKey: String,
    val amstorecreditGeneralEnabled: String,
    val appConfigurationsAppColorsBase: String,
    val appConfigurationsAppColorsSecondary: String,
    val appConfigurationsAppDownloadLinksAndroidLink: String,
    val appConfigurationsAppDownloadLinksIosLink: String,
    val appConfigurationsPagesConfigurationsAboutUrl: String,
    val appConfigurationsPagesConfigurationsPrivacyPolicyUrl: String,
    val appConfigurationsPagesConfigurationsTermsOfUseUrl: String,
    val appConfigurationsPagesConfigurationsVatUrl: String,
    val appConfigurationsUiConfigurationsCategory: String,
    val cancelorderGeneralButtonLabel: String,
    val cancelorderGeneralEnabled: String,
    val cancelorderGeneralNotice: String,
    val catalogFrontendGridPerPage: String,
    val catalogFrontendGridPerPageValues: String,
    val catalogFrontendListPerPage: String,
    val catalogFrontendListPerPageValues: String,
    val catalogReviewActive: String,
    val catalogReviewAllowGuest: String,
    val customerAddressCompanyShow: String,
    val customerAddressDobShow: String,
    val customerAddressGenderShow: String,
    val customerAddressTelephoneShow: String,
    val dailydealGeneralCountdownTimerShowCountdownTimer: String,
    val dailydealGeneralDiscountLabelLabelBgColor: String,
    val dailydealGeneralDiscountLabelLabelTextColor: String,
    val freeShippingBarGeneralActive: String,
    val freeShippingBarGeneralCustomAmount: String,
    val freeShippingBarGeneralNotificationMessage: String,
    val freeShippingBarGeneralSuccessMessage: String,
    val paymentCashondeliveryPaymentFee: String,
    val paymentCheckoutcomApplePayActive: String,
    val paymentCheckoutcomCardPaymentSaveCardOption: String,
    val paymentCheckoutcomGooglePayActive: String,
    val paymentCheckoutcomVaultActive: String,
    val remoteConfigExportForceUpdate: String,
    val remoteConfigSocialMediaFacebook: String,
    val remoteConfigSocialMediaInstagram: String,
    val remoteConfigSocialMediaSnapchat: String,
    val remoteConfigSocialMediaTwitter: String,
    val remoteConfigSocialMediaWhatsappNumber: String,
    val settingsCheckoutcomConfigurationActive: String,
    val settingsCheckoutcomConfigurationEnvironment: String,
    val settingsCheckoutcomConfigurationPublicKey: String,
    val settingsCheckoutcomConfigurationSecretKey: String,
    val shopbybrandBrandpageName: String,
    val shopbybrandBrandpageSearchEnable: String,
    val shopbybrandBrandpageShowAlphabetView: String,
    val shopbybrandBrandviewBackgroundColor: String,
    val shopbybrandBrandviewShowBrandname: String,
    val shopbybrandBrandviewShowDescription: String,
    val shopbybrandGeneralEnabled: String,
    val socialloginAppleAppId: String,
    val socialloginAppleIsEnabled: String,
    val socialloginAppleSortOrder: String,
    val socialloginFacebookIsEnabled: String,
    val socialloginGoogleAppId: String,
    val socialloginGoogleAppSecret: String,
    val socialloginGoogleIsEnabled: String,
    val socialloginGoogleSortOrder: String,
    val socialloginTwitterIsEnabled: String,
    val socialloginTwitterSortOrder: String,
    val storecreditGeneralEnabled: String,
    val themeNewsletterPopupEnabled: String,
    val themeNewsletterPopupFormSubscribe: String,
    val themeNewsletterPopupHtml: String,
    val themeNewsletterPopupNewsletterVersion: String,
    val themeNewsletterPopupPopupDelay: String,
    val themeNewsletterPopupShowOnMobile: String,
    val themeNewsletterPopupTitle: String
)
