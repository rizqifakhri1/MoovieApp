package com.rizqi.myapplication.service

import com.rizqi.myapplication.model.GetAllMoviePopular
import com.rizqi.myapplication.model.GetAllMovieUpcoming
import com.rizqi.myapplication.model.GetDetail
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("movie/popular?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    fun getAllMoviePopuler() : Call<GetAllMoviePopular>

    @GET("movie/upcoming?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    fun getAllMovieUpcoming() : Call<GetAllMovieUpcoming>

    @GET("movie/{movie_id}?api_key=b4c17e0cc787b3a9f80b08cf1300bd72")
    fun getDetail(@Path("movie_id")movie_id:Int) : Call<GetDetail>

}