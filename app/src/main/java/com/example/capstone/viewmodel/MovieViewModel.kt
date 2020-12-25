package com.example.capstone.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone.repository.MoviesRepository
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val moviesRepository = MoviesRepository()
    val movies = moviesRepository.movies
    val topRatedMovies = moviesRepository.topRatedMovies
    private val _errorText: MutableLiveData<String> = MutableLiveData()

    val errorText: LiveData<String>
        get() = _errorText

    fun getPopularMovies() {
        viewModelScope.launch {
            try {
                moviesRepository.getPopularMovies()
            } catch (error: MoviesRepository.PopularMoviesFetchError) {
                _errorText.value = error.message
                Log.e("Fetch error", error.cause.toString())
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch {
            try {
                moviesRepository.getTopRatedMovies()
            } catch (error: MoviesRepository.TopRatedMoviesFetchError) {
                _errorText.value = error.message
                Log.e("Fetch error", error.cause.toString())
            }
        }
    }


}