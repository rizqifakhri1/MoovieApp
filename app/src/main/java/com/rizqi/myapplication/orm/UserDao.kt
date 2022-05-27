package com.rizqi.myapplication.orm

import androidx.room.*
import com.rizqi.myapplication.model.UserEntity
import retrofit2.http.GET

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE username = :username and password = :password)")
    suspend fun checkUser(username: String, password: String): Boolean

    @Query("SELECT * FROM UserEntity WHERE username = :username and password = :password")
    suspend fun getUser(username: String, password: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser (userEntity: UserEntity):Long

    @Update
    suspend fun updateUser (userEntity: UserEntity):Int
}