package com.example.capstone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.capstone.model.Movie
import kotlinx.android.synthetic.main.fragment_movies_detail.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
const val REQ_MOVIE_KEY = "req_movie"
const val BUNDLE_MOVIE_KEY = "bundle_movie"
class MoviesDetailFragment : Fragment() {

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeClickMovie()
    }

    private fun observeClickMovie() {
        setFragmentResultListener(REQ_MOVIE_KEY) { _ , bundle ->
            bundle.getParcelable<Movie>(BUNDLE_MOVIE_KEY)?.let {
                setDetailElements(it)
            }
        }
    }

    private fun setDetailElements(movie: Movie) {
        movie_title.text = movie.title
        movie_release_date.text = movie.releaseDate
        movie_overview.text = movie.overview
        movie_rating.rating
        context?.let {
            Glide.with(it).load(movie.getBackdropPath()).into(movie_backdrop)
            Glide.with(it).load(movie.getPosterPath()).into(movie_poster)
        }
    }
}