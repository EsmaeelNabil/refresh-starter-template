package com.pixiedia.pixicommerce.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pixiedia.pixicommerce.BuildConfig
import com.pixiedia.pixicommerce.data.local.models.AppConfigurationsEntity
import com.pixiedia.pixicommerce.data.local.models.CountriesEntity
import com.pixiedia.pixicommerce.data.local.models.GenderTypesEntity
import com.pixiedia.pixicommerce.data.local.models.StoreViewsEntity

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

@Database(
    entities = [
        AppConfigurationsEntity::class,
        CountriesEntity::class,
        GenderTypesEntity::class,
        StoreViewsEntity::class
    ],
    version = 1
)
abstract class AppLocalDatabase : RoomDatabase() {
    abstract fun localConfigDao(): LocalConfigDao

    companion object {
        fun getAppDataBase(context: Context): AppLocalDatabase {
            return Room.databaseBuilder(
                context,
                AppLocalDatabase::class.java,
                BuildConfig.DATABASE_NAME
            ).build()
        }
    }
}

