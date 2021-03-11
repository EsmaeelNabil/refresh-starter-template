package com.pixiedia.pixicommerce.data.local.db

import androidx.room.*
import com.pixiedia.pixicommerce.data.local.models.AppConfigurationsEntity
import com.pixiedia.pixicommerce.data.local.models.CountriesEntity
import com.pixiedia.pixicommerce.data.local.models.GenderTypesEntity
import com.pixiedia.pixicommerce.data.local.models.StoreViewsEntity

@Dao
interface LocalConfigDao {

    /*-------------------------------------- save -----------------------------*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAppConfiguration(countries: AppConfigurationsEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCountries(countries: CountriesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveGenderTypes(countries: GenderTypesEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveStoreViews(countries: StoreViewsEntity)


    /*------------------------------------- get -----------------------------*/

    @Query("SELECT * FROM countries_table")
    suspend fun getCountries(): CountriesEntity

    @Query("SELECT * FROM app_configurations_table")
    suspend fun getAppConfiguration(): AppConfigurationsEntity

    @Query("SELECT * FROM gender_types_table")
    suspend fun getGenderTypes(): GenderTypesEntity

    @Query("SELECT * FROM store_views_table")
    suspend fun getStoreViews(): StoreViewsEntity
}