package com.esmaeel.usecases.data.local.db

import androidx.room.*
import com.esmaeel.usecases.data.local.models.UserLocalEntity

@Dao
interface UsersDao {

    /*-------------------------------------- save -----------------------------*/

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveUsers(vararg user: UserLocalEntity)

    /*------------------------------------- get -----------------------------*/

    @Query("SELECT * FROM users")
    suspend fun getUsers(): List<UserLocalEntity>
}