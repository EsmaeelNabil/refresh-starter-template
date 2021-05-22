package com.esmaeel.usecases.data.remote

import com.esmaeel.usecases.data.remote.models.UserNetworkEntity
import retrofit2.http.GET

interface UsersService {

    @GET("dummyList")
    suspend fun getUsers(): List<UserNetworkEntity>

}



