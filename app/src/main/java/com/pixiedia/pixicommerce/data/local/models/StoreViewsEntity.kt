package com.pixiedia.pixicommerce.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.pixiedia.pixicommerce.common.Constants

@Entity(tableName = Constants.StoreViewsTableName)
data class StoreViewsEntity(@PrimaryKey val json: String)