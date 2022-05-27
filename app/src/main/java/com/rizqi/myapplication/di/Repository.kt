package com.rizqi.myapplication.di

import com.rizqi.myapplication.model.UserEntity
import com.rizqi.myapplication.orm.DbHelper;
import com.rizqi.myapplication.service.ApiHelper;

class Repository(
    private val apiHelper:ApiHelper,
    private val dbHelper:DbHelper
) {

    // Api
    suspend fun getPopularMovies() = apiHelper.getPopularMovies()

    suspend fun getUpcomingMovies() = apiHelper.getUpcomingMovies()

    suspend fun getMovieById(movie_id: Int) = apiHelper.getMovieById(movie_id)

    // Room
    suspend fun addUser(user: UserEntity): Long = dbHelper.addUser(user)

    suspend fun getUser(username: String, password: String): UserEntity {
        return dbHelper.getUser(username, password)
    }

    suspend fun updateUser(user: UserEntity): Int = dbHelper.updateUser(user)

}