package com.example.capstone.viewmodel

import android.app.Application
import android.util.Log
import android.widget.EditText
import android.widget.RatingBar
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.capstone.model.Movie
import com.example.capstone.model.Review
import com.example.capstone.repository.MoviesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MovieViewModel(application: Application): AndroidViewModel(application) {
    private val moviesRepository = MoviesRepository()
    val movies = moviesRepository.movies
    val reviews = moviesRepository.reviews
    val topRatedMovies = moviesRepository.topRatedMovies
    val searchMovies = moviesRepository.searchMovies
    private val _errorText: MutableLiveData<String> = MutableLiveData()
    private val TAG = "FIRESTORE"

    val errorText: LiveData<String>
        get() = _errorText

    fun getPopularMovies() {
        viewModelScope.launch(Dispatchers.IO)  {
            try {
                moviesRepository.getPopularMovies()
            } catch (error: MoviesRepository.PopularMoviesFetchError) {
                _errorText.value = error.message
                Log.e("Fetch error", error.cause.toString())
            }
        }
    }

    fun getTopRatedMovies() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                moviesRepository.getTopRatedMovies()
            } catch (error: MoviesRepository.TopRatedMoviesFetchError) {
                _errorText.value = error.message
                Log.e("Fetch error", error.cause.toString())
            }
        }
    }

    fun createReview(title: String, comment: String, ratingBar: Float) {
        val review = Review(comment, ratingBar)
        viewModelScope.launch(Dispatchers.Main) {
            try {
                moviesRepository.createReview(title, review)
            } catch (ex: MoviesRepository.CreateReviewError) {
                val errorMsg = "Something went wrong while saving review"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.value = errorMsg
            }
        }
    }

    fun getReview(title: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                moviesRepository.getReview(title)
            } catch (ex: MoviesRepository.GetReviewError) {
                val errorMsg = "Something went wrong while retrieving review"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.postValue(errorMsg)
            }
        }
    }

    fun getSearchMovies(movie: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                moviesRepository.getSearchMovies(movie)
            } catch (ex: MoviesRepository.SearchMoviesError) {
                val errorMsg = "Something went wrong while searching movies"
                Log.e(TAG, ex.message ?: errorMsg)
                _errorText.postValue(errorMsg)
            }
        }
    }
}