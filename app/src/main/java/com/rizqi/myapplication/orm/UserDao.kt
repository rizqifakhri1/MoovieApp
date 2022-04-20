package com.rizqi.myapplication.orm

import androidx.room.*
import com.rizqi.myapplication.model.UserEntity
import retrofit2.http.GET

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE username = :username and password = :password)")
    fun checkUser(username: String, password: String): Boolean

    @Query("SELECT * FROM UserEntity WHERE username = :username and password = :password")
    fun getUser(username: String, password: String): UserEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser (userEntity: UserEntity):Long

    @Update
    fun updateUser (ramadhanEntity: UserEntity):Int
}