package com.example.capstone.api

import com.example.capstone.model.ApiResponse
import retrofit2.http.GET
import retrofit2.http.Query


interface MoviesApiService {
    @GET("3/movie/popular?api_key=fbfbc8554469c19f217d943c4e7d4b23&page=1")
    suspend fun getPopularMovies(): ApiResponse.Result

    @GET("3/movie/top_rated?api_key=fbfbc8554469c19f217d943c4e7d4b23&page=1")
    suspend fun getTopRatedMovies(): ApiResponse.Result

    @GET("3/search/movie?api_key=fbfbc8554469c19f217d943c4e7d4b23&&")
    suspend fun getSearchMovies(@Query("query") movie: String): ApiResponse.Result
}