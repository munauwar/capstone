package com.example.capstone

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.adapter.MoviesAdapter
import com.example.capstone.adapter.ReviewAdapter
import com.example.capstone.model.Movie
import com.example.capstone.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_movies.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class MoviesFragment : Fragment() {
    private val movies = arrayListOf<Movie>()
    private val topRatedMovies = arrayListOf<Movie>()
    private val moviesAdapter = MoviesAdapter(movies, ::getDetail)
    private val topRatedMoviesAdapter = MoviesAdapter(topRatedMovies, ::getDetail)
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.getPopularMovies()
        viewModel.getTopRatedMovies()
        initView()
        observeMovie()
    }

    private fun initView() {
        rvMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvMovies.adapter = moviesAdapter
        rvTopRated.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        rvTopRated.adapter = topRatedMoviesAdapter
    }

    private fun observeMovie() {
        viewModel.movies.observe(viewLifecycleOwner, Observer {
            movies.clear()
            movies.addAll(it)
            moviesAdapter.notifyDataSetChanged()
        })

        viewModel.topRatedMovies.observe(viewLifecycleOwner, Observer {
            topRatedMovies.clear()
            topRatedMovies.addAll(it)
            topRatedMoviesAdapter.notifyDataSetChanged()
        })

        viewModel.errorText.observe(viewLifecycleOwner, Observer {

        })
    }

    private fun getDetail(movie: Movie) {
        setFragmentResult(REQ_MOVIE_KEY, bundleOf(Pair(BUNDLE_MOVIE_KEY, movie)))
        findNavController().navigate(
                R.id.action_FirstFragment_to_SecondFragment
        )
    }
}