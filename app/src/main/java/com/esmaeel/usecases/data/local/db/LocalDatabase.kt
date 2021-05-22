package com.esmaeel.usecases.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.esmaeel.usecases.BuildConfig
import com.esmaeel.usecases.data.local.models.UserLocalEntity

/**
 * Created by Esmaeel Nabil on Feb, 2021
 */

@Database(entities = [UserLocalEntity::class], version = 1)
abstract class LocalDatabase : RoomDatabase() {

    abstract fun usersDao(): UsersDao

    companion object {
        fun getLocalDatabase(context: Context) = Room
            .databaseBuilder(
                context,
                LocalDatabase::class.java,
                BuildConfig.DATABASE_NAME
            ).build()

    }
}

