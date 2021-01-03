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
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.capstone.adapter.MoviesAdapter
import com.example.capstone.model.Movie
import com.example.capstone.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {
    private val movies = arrayListOf<Movie>()
    private val moviesAdapter = MoviesAdapter(movies, ::getDetail)
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        observeMovie()
        button.setOnClickListener {
            getUserInput()
        }
    }

    private fun initView() {
        rvSearchMovies.layoutManager = StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL)
        rvSearchMovies.adapter = moviesAdapter
    }

    private fun observeMovie() {
        viewModel.searchMovies.observe(viewLifecycleOwner, Observer {
            movies.clear()
            movies.addAll(it)
            moviesAdapter.notifyDataSetChanged()
        })
    }

    private fun getUserInput() {
        val movie = movie_input.text.toString()
        viewModel.getSearchMovies(movie)
    }

    private fun getDetail(movie: Movie) {
        setFragmentResult(REQ_MOVIE_KEY, bundleOf(Pair(BUNDLE_MOVIE_KEY, movie)))
        findNavController().navigate(
            R.id.action_searchFragment_to_SecondFragment
        )
    }
}