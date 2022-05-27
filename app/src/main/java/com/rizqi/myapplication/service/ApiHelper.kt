package com.rizqi.myapplication.service

class ApiHelper(private val apiService: ApiService) {
    suspend fun getPopularMovies() = apiService.getPopularMovie()

    suspend fun getUpcomingMovies() = apiService.getUpcomingMovie()

    suspend fun getMovieById(movie_id: Int) = apiService.getMovieById(movie_id)
}