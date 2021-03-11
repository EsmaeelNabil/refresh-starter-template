package com.pixiedia.pixicommerce.data.local.db

import com.pixiedia.pixicommerce.data.local.models.AppConfigurationsEntity
import com.pixiedia.pixicommerce.data.local.models.CountriesEntity
import com.pixiedia.pixicommerce.data.local.models.GenderTypesEntity
import com.pixiedia.pixicommerce.data.local.models.StoreViewsEntity
import com.pixiedia.pixicommerce.data.remote.models.AppInitResponse

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

interface LocalDataSource {
    suspend fun saveAppConfiguration(appConfigurationsEntity: AppConfigurationsEntity)
    suspend fun saveCountries(countries: CountriesEntity)
    suspend fun saveGenderTypes(genderTypesEntity: GenderTypesEntity)
    suspend fun saveStoreViews(storeViewsEntity: StoreViewsEntity)
    suspend fun getCountries(): List<AppInitResponse.AppInitResponseItem.Country>?
    suspend fun getAppConfiguration(): AppInitResponse.AppInitResponseItem.AppConfiguration?
    suspend fun getGenderTypes(): List<AppInitResponse.AppInitResponseItem.GenderType>?
    suspend fun getStoreViews(): List<AppInitResponse.AppInitResponseItem.StoreView>?
    suspend fun saveAppInitRemoteConfig(remoteConfig: AppInitResponse)
}