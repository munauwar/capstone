package com.example.capstone.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.MoviesApi
import com.example.capstone.api.MoviesApiService
import com.example.capstone.model.Movie
import kotlinx.coroutines.withTimeout
import javax.security.auth.callback.Callback

class MoviesRepository {
    private val moviesApiService: MoviesApiService = MoviesApi.createApi()
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _topRatedMovies: MutableLiveData<List<Movie>> = MutableLiveData()


    val movies: LiveData<List<Movie>>
        get() = _movies

    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies


    suspend fun getPopularMovies() {
        try {
            val result = withTimeout(5000) {
                moviesApiService.getPopularMovies()
            }
            _movies.value = result.results
        } catch (error: Throwable) {
            throw PopularMoviesFetchError("Unable to fetch popular movies", error)
        }
    }

    suspend fun getTopRatedMovies() {
        try {
            val resultTopRated = withTimeout(5000) {
                moviesApiService.getTopRatedMovies()
            }
            _topRatedMovies.value = resultTopRated.results
        } catch (error: Throwable) {
            throw TopRatedMoviesFetchError("Unable to fetch top rated movies", error)
        }
    }

    class PopularMoviesFetchError(message: String, cause: Throwable): Throwable(message, cause)
    class TopRatedMoviesFetchError(message: String, cause: Throwable): Throwable(message, cause)
}
