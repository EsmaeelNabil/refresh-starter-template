package com.pixiedia.pixicommerce.data.repositories

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.pixiedia.pixicommerce.data.local.db.LocalDataSource
import com.pixiedia.pixicommerce.utils.ktx.doSafely
import timber.log.Timber

class FirestoreListener(
    private val gson: Gson,
    private val localDataSource: LocalDataSource
) {

    // change this `ar` from remoteConfig api storeViews or user default or selected store and language.
    // should  have a defualt value
    // then passed from sharedPrefs or something
    private val arRemoteConfig = Firebase.firestore.collection("remoteConfig").document("ar")

    fun registerRemoteConfigUpdate(
        localDatabaseAutoUpdate: Boolean = true,
        onValueChange: ((DocumentSnapshot, FireStoreRemoteConfig) -> Unit)? = null
    ) {
        doSafely {
            arRemoteConfig
                .addSnapshotListener { value, error ->
                    error?.let {
                        Timber.e(it)
                    } ?: run {
                        val json = gson.toJson(value?.data)
                        val type = object : TypeToken<FireStoreRemoteConfig>() {}.type
                        val obj = gson.fromJson<FireStoreRemoteConfig>(json, type)
                        // actual value from firebase in a data class
                        value?.let { jsonResponse ->
                            // notify observers if not null
                            obj?.let { firestoreRemoteConfig ->
                                onValueChange?.let { callback ->
                                    callback(
                                        jsonResponse,
                                        firestoreRemoteConfig
                                    )
                                }

                                //TODO update database if LocalDatabaseAutoUpdate = true
                                // backend said he will update the response to be like the /init and /remoteConfig endpoint to
                                // be able to save them in ->
                                /*localDataSource.saveAppConfiguration(
                                    AppConfigurationsEntity(
                                        // used to be like this
                                        //gson.toJson(remoteConfig[0].appConfigurations?.get(0))
                                        // should be able to put it with the same structure using gson.toJson of firestoreRemoteConfig.
                                        json =
                                    )
                                )*/
                                // this way you will be able to get the remote config from anywhere in this model AppInitResponse
                            }
                        }
                    }
                }

        }
    }


}