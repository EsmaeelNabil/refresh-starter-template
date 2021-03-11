package com.pixiedia.pixicommerce.data.local.db

import com.pixiedia.pixicommerce.data.local.models.AppConfigurationsEntity
import com.pixiedia.pixicommerce.data.local.models.CountriesEntity
import com.pixiedia.pixicommerce.data.local.models.GenderTypesEntity
import com.pixiedia.pixicommerce.data.local.models.StoreViewsEntity
import com.pixiedia.pixicommerce.data.remote.models.AppInitResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class LocalDataSourceImpl(
    private val db: AppLocalDatabase,
    private val gson: Gson
) : LocalDataSource {

    override suspend fun saveAppConfiguration(appConfigurationsEntity: AppConfigurationsEntity) {
        db.localConfigDao().saveAppConfiguration(appConfigurationsEntity)
    }

    override suspend fun saveCountries(countries: CountriesEntity) {
        db.localConfigDao().saveCountries(countries)
    }

    override suspend fun saveGenderTypes(genderTypesEntity: GenderTypesEntity) {
        db.localConfigDao().saveGenderTypes(genderTypesEntity)
    }

    override suspend fun saveStoreViews(storeViewsEntity: StoreViewsEntity) {
        db.localConfigDao().saveStoreViews(storeViewsEntity)
    }

    override suspend fun getCountries(): List<AppInitResponse.AppInitResponseItem.Country>? {
        val countriesJson = db.localConfigDao().getCountries()

        val listType =
            object : TypeToken<List<AppInitResponse.AppInitResponseItem.Country>>() {}.type

        return gson.fromJson(
            countriesJson.json,
            listType
        )
    }

    override suspend fun getAppConfiguration(): AppInitResponse.AppInitResponseItem.AppConfiguration? {
        val appConfigEntity = db.localConfigDao().getAppConfiguration()

        val listType =
            object : TypeToken<AppInitResponse.AppInitResponseItem.AppConfiguration>() {}.type

        return gson.fromJson<AppInitResponse.AppInitResponseItem.AppConfiguration>(
            appConfigEntity.json,
            listType
        )
    }

    override suspend fun getGenderTypes(): List<AppInitResponse.AppInitResponseItem.GenderType>? {
        val genderTypesEntity = db.localConfigDao().getGenderTypes()

        val listType =
            object : TypeToken<List<AppInitResponse.AppInitResponseItem.GenderType>>() {}.type

        return gson.fromJson<List<AppInitResponse.AppInitResponseItem.GenderType>>(
            genderTypesEntity.json,
            listType
        )
    }

    override suspend fun getStoreViews(): List<AppInitResponse.AppInitResponseItem.StoreView>? {
        val storeViewsEntity = db.localConfigDao().getStoreViews()

        val listType =
            object : TypeToken<List<AppInitResponse.AppInitResponseItem.StoreView>>() {}.type

        return gson.fromJson<List<AppInitResponse.AppInitResponseItem.StoreView>>(
            storeViewsEntity.json,
            listType
        )
    }

    override suspend fun saveAppInitRemoteConfig(remoteConfig: AppInitResponse) {

        saveAppConfiguration(
            AppConfigurationsEntity(
                json = gson.toJson(
                    remoteConfig[0].appConfigurations?.get(0)
                )
            )
        )

        saveCountries(
            CountriesEntity(
                json = gson.toJson(
                    remoteConfig[0].countries
                )
            )
        )

        saveGenderTypes(
            GenderTypesEntity(
                json = gson.toJson(
                    remoteConfig[0].genderTypes
                )
            )
        )

        saveStoreViews(
            StoreViewsEntity(
                json = gson.toJson(
                    remoteConfig[0].storeViews
                )
            )
        )
    }
}