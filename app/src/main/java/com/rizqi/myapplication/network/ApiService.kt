package com.rizqi.myapplication.network

import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.GetAllMovieUpcoming
import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    @GET("movie/popular?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    fun getAllMoviePopuler() : Call<GetAllMoviePopular>

    @GET("movie/upcoming?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    fun getAllMovieUpcoming() : Call<GetAllMovieUpcoming>

}