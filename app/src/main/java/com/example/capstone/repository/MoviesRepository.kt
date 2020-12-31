package com.example.capstone.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.capstone.api.MoviesApi
import com.example.capstone.api.MoviesApiService
import com.example.capstone.model.Movie
import com.example.capstone.model.Review
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withTimeout
import javax.security.auth.callback.Callback

class MoviesRepository {
    private var firestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val moviesApiService: MoviesApiService = MoviesApi.createApi()
    private val _movies: MutableLiveData<List<Movie>> = MutableLiveData()
    private val _reviews: MutableLiveData<ArrayList<Review>> = MutableLiveData()
    private val _topRatedMovies: MutableLiveData<List<Movie>> = MutableLiveData()


    val movies: LiveData<List<Movie>>
        get() = _movies

    val topRatedMovies: LiveData<List<Movie>>
        get() = _topRatedMovies

    val reviews: LiveData<ArrayList<Review>>
        get() = _reviews


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

    suspend fun createReview(title: String, review: Review) {
        try {
            withTimeout(5000) {
                firestore
                    .collection("Reviews")
                    .document(title)
                    .collection("Review")
                    .add(review)
                    .await()
            }
        }  catch (e : Exception) {
            throw CreateReviewError(e.message.toString(), e)
        }
    }

    suspend fun getReview(title: String) {
        try {
            withTimeout(5000) {
                val reviewsList : ArrayList<Review> = ArrayList()
                val data = firestore
                        .collection("Reviews")
                        .document(title)
                        .collection("Review")
                        .get()
                        .await()
                for (currentItem: DocumentSnapshot in data.documents) {
                    reviewsList.add(Review(
                            currentItem.getString("comment").toString(),
                            currentItem.getDouble("rating")!!.toFloat()))
                }
                _reviews.value?.clear()
                _reviews.value = reviewsList
            }
        }  catch (e : Exception) {
            throw GetReviewError(e.message.toString(), e)
        }
    }

    class PopularMoviesFetchError(message: String, cause: Throwable): Throwable(message, cause)
    class TopRatedMoviesFetchError(message: String, cause: Throwable): Throwable(message, cause)
    class CreateReviewError(message: String, cause: Throwable) : Exception(message, cause)
    class GetReviewError(message: String, cause: Throwable): Exception(message, cause)
}
