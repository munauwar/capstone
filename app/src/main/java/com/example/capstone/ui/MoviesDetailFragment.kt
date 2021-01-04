package com.example.capstone.ui

import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.Toast
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.adapter.ReviewAdapter
import com.example.capstone.model.Movie
import com.example.capstone.model.Review
import com.example.capstone.viewmodel.MovieViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_movies_detail.*
import kotlinx.android.synthetic.main.fragment_movies_detail.view.*
import kotlinx.coroutines.launch

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
const val REQ_MOVIE_KEY = "req_movie"
const val BUNDLE_MOVIE_KEY = "bundle_movie"
class MoviesDetailFragment : Fragment() {
    private val reviews = arrayListOf<Review>()
    private val reviewsAdapter = ReviewAdapter(reviews)
    private val viewModel: MovieViewModel by viewModels()

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
        observeReview()
        initView()
    }

    private fun initView() {
        rvReviews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvReviews.adapter = reviewsAdapter
    }

    private fun observeReview() {
        viewModel.reviews.observe(viewLifecycleOwner, Observer {
            reviews.clear()
            reviews.addAll(it)
            Log.e(TAG, it.toString())
            reviewsAdapter.notifyDataSetChanged()
        })
    }

    private fun observeClickMovie() {
        setFragmentResultListener(REQ_MOVIE_KEY) { _, bundle ->
            bundle.getParcelable<Movie>(BUNDLE_MOVIE_KEY)?.let { movie ->
                button.setOnClickListener {
                    showRateDialog(movie.title)
                }
                setDetailElements(movie)
                viewModel.getReview(movie.title)
            }
        }
    }

    private fun setDetailElements(movie: Movie) {
        movie_title.text = movie.title
        movie_release_date.text = movie.releaseDate
        movie_overview.text = movie.overview
        context?.let {
            Glide.with(it).load(movie.getBackdropPath()).into(movie_backdrop)
            Glide.with(it).load(movie.getPosterPath()).into(movie_poster)
        }
    }

    private fun showRateDialog(title: String) {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.reviews))
        val dialogLayout = layoutInflater.inflate(R.layout.add_rating_dialog, null)
        val comment = dialogLayout.findViewById<EditText>(R.id.txt_comment)
        val rating = dialogLayout.findViewById<RatingBar>(R.id.ratingBar)

        builder.setView(dialogLayout)
        builder.setPositiveButton(R.string.dialog_ok_btn) { _: DialogInterface, _: Int ->
            val commentSave = comment.text.toString()
            val ratingSave = rating.rating
            viewModel.createReview(title, commentSave, ratingSave)
            Snackbar.make(requireActivity().findViewById(android.R.id.content),
                    "Review saved!",
                    Snackbar.LENGTH_LONG)
                    .setActionTextColor(Color.WHITE)
                    .show()
        }
        builder.show()

    }
}