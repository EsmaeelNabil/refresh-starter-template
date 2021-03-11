package com.pixiedia.pixicommerce.data.remote.models

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

class AppInitResponse : ArrayList<AppInitResponse.AppInitResponseItem>(){
    data class AppInitResponseItem(
        val adminToken: String? = "",
        val appConfigurations: List<AppConfiguration?>? = listOf(),
        val countries: List<Country?>? = listOf(),
        val genderTypes: List<GenderType?>? = listOf(),
        val guestQuote: String? = "",
        val storeViews: List<StoreView?>? = listOf()
    ) {
        data class AppConfiguration(
            val appConfigurations: AppConfigurations? = AppConfigurations()
        ) {
            data class AppConfigurations(
                val algoliasearchCredentials: AlgoliasearchCredentials? = AlgoliasearchCredentials(),
                val appConfigurations: AppConfigurations? = AppConfigurations(),
                val general: General? = General(),
                val payment: Payment? = Payment(),
                val settings: Settings? = Settings()
            ) {
                data class AlgoliasearchCredentials(
                    val credentials: Credentials? = Credentials()
                ) {
                    data class Credentials(
                        val apiKey: String? = "",
                        val applicationId: String? = "",
                        val enableFrontend: Boolean? = false,
                        val indexPrefix: String? = "",
                        val searchOnlyApiKey: String? = ""
                    )
                }
    
                data class AppConfigurations(
                    val onBoarding: OnBoarding? = OnBoarding()
                ) {
                    data class OnBoarding(
                        val firstPage: FirstPage? = FirstPage(),
                        val secoundPage: SecoundPage? = SecoundPage(),
                        val thirdPage: ThirdPage? = ThirdPage()
                    ) {
                        data class FirstPage(
                            val description: String? = "",
                            val image: String? = "",
                            val title: String? = ""
                        )
    
                        data class SecoundPage(
                            val description: String? = "",
                            val image: String? = "",
                            val title: String? = ""
                        )
    
                        data class ThirdPage(
                            val description: String? = "",
                            val image: String? = "",
                            val title: String? = ""
                        )
                    }
                }
    
                data class General(
                    val locale: Locale? = Locale(),
                    val region: Region? = Region()
                ) {
                    data class Locale(
                        val code: String? = "",
                        val timezone: String? = ""
                    )
    
                    data class Region(
                        val stateRequired: String? = ""
                    )
                }
    
                data class Payment(
                    val checkoutcomApplePay: CheckoutcomApplePay? = CheckoutcomApplePay(),
                    val checkoutcomGooglePay: CheckoutcomGooglePay? = CheckoutcomGooglePay(),
                    val checkoutcomMoto: CheckoutcomMoto? = CheckoutcomMoto(),
                    val checkoutcomVault: CheckoutcomVault? = CheckoutcomVault(),
                    val paytabs: Paytabs? = Paytabs(),
                    val tap: Tap? = Tap()
                ) {
                    data class CheckoutcomApplePay(
                        val active: Boolean? = false,
                        val merchantId: String? = ""
                    )
    
                    data class CheckoutcomGooglePay(
                        val active: Boolean? = false,
                        val merchantId: String? = "",
                        val title: String? = ""
                    )
    
                    data class CheckoutcomMoto(
                        val savedCardsEnabled: Boolean? = false
                    )
    
                    data class CheckoutcomVault(
                        val active: Boolean? = false
                    )
    
                    data class Paytabs(
                        val active: Boolean? = false
                    )
    
                    data class Tap(
                        val active: Boolean? = false,
                        val title: String? = ""
                    )
                }
    
                data class Settings(
                    val checkoutcomConfiguration: CheckoutcomConfiguration? = CheckoutcomConfiguration()
                ) {
                    data class CheckoutcomConfiguration(
                        val active: Boolean? = false,
                        val publicKey: String? = "",
                        val secretKey: String? = ""
                    )
                }
            }
        }
    
        data class Country(
            val availableCities: List<AvailableCity?>? = listOf(),
            val availableRegions: List<AvailableRegion?>? = listOf(),
            val countryName: String? = "",
            val countryTeleCode: String? = "",
            val flag: String? = "",
            val fullNameEnglish: String? = "",
            val fullNameLocale: String? = "",
            val id: String? = "",
            val message: String? = "",
            val status: Boolean? = false,
            val threeLetterAbbreviation: String? = "",
            val twoLetterAbbreviation: String? = ""
        ) {
            data class AvailableCity(
                val cityName: String? = "",
                val id: Int? = 0,
                val stateId: Int? = 0,
                val status: Boolean? = false,
                val storeId: Int? = 0
            )
    
            data class AvailableRegion(
                val code: String? = "",
                val countryId: String? = "",
                val id: Int? = 0,
                val name: String? = ""
            )
        }
    
        data class GenderType(
            val label: String? = "",
            val value: Int? = 0
        )
    
        data class StoreView(
            val code: String? = "",
            val countryData: List<CountryData?>? = listOf(),
            val id: Int? = 0,
            val isDefault: Boolean? = false,
            val name: String? = "",
            val stores: List<Store?>? = listOf()
        ) {
            data class CountryData(
                val countryId: String? = "",
                val flag: String? = "",
                val name: String? = ""
            )
    
            data class Store(
                val baseCurrencyCode: String? = "",
                val code: String? = "",
                val currencySymbol: String? = "",
                val currentCurrencyCode: String? = "",
                val currentCurrency_Rate: Int? = 0,
                val defaultCurrencyCode: String? = "",
                val defaultLang: String? = "",
                val id: Int? = 0,
                val isActive: Boolean? = false,
                val name: String? = "",
                val urlPrefix: String? = "",
                val websiteId: Int? = 0
            )
        }
    }
}