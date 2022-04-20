package com.rizqi.myapplication.orm

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rizqi.myapplication.model.UserEntity

@Dao
interface UserDao {
    @Query("SELECT EXISTS(SELECT * FROM UserEntity WHERE username = :username and password = :password)")
    fun checkUser(username: String, password: String): Boolean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser (userEntity: UserEntity):Long
}