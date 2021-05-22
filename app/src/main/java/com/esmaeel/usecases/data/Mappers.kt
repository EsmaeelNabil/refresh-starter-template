package com.esmaeel.usecases.data

import com.esmaeel.usecases.data.local.models.UserLocalEntity
import com.esmaeel.usecases.data.remote.models.UserNetworkEntity
import com.esmaeel.usecases.ui.userFeatures.User

fun UserNetworkEntity.mapToLocalUser() = UserLocalEntity(id, name, avatar, createdAt)
fun UserLocalEntity.mapToUser() = User(id, name, avatar, createdAt)

fun List<UserNetworkEntity>.mapToLocalUsers() = this.map { it.mapToLocalUser() }
fun List<UserLocalEntity>.mapToUsers() = this.map { it.mapToUser() }