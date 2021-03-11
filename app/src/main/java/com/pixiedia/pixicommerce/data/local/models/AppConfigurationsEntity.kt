package com.pixiedia.pixicommerce.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pixiedia.pixicommerce.common.Constants

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

@Entity(tableName = Constants.AppConfigurationsTableName)
data class AppConfigurationsEntity(@PrimaryKey val json: String)
