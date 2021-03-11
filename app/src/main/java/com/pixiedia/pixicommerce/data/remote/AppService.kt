package com.pixiedia.pixicommerce.data.remote

import com.pixiedia.pixicommerce.data.remote.models.AppInitResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface AppService {

    @GUEST_TOKEN_REQUIRED
    @POST("init")
    suspend fun getAppInit(
        @Body hashMap: HashMap<String, String>
    ): AppInitResponse

}

