package com.rizqi.myapplication.service

import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.GetAllMovieUpcoming
import com.rizqi.myapplication.model.GetDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("movie/popular?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    suspend fun getUpcomingMovie(): GetAllMovieUpcoming

    @GET("movie/popular?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    suspend fun getPopularMovie(): GetAllMoviePopular

    @GET("movie/popular?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    suspend fun getMovieById(@Path("movie_id") movie_id: Int): GetDetail
}