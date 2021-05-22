package com.esmaeel.usecases.data.local.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserLocalEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val name: String,
    val avatar: String,
    val createdAt: String
)