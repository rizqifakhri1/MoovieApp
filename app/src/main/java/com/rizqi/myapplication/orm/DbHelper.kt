package com.rizqi.myapplication.orm

import com.rizqi.myapplication.model.UserEntity
import javax.inject.Inject

class DbHelper @Inject constructor(private val userDao: UserDao) {
    suspend fun addUser(user: UserEntity): Long = userDao.addUser(user)
    suspend fun getUser(username: String, password: String): UserEntity = userDao.getUser(username, password)
    suspend fun updateUser(user: UserEntity):Int = userDao.updateUser(user)
}