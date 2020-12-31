package com.example.capstone

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.capstone.adapter.ReviewAdapter
import com.example.capstone.model.Review
import com.example.capstone.viewmodel.MovieViewModel
import kotlinx.android.synthetic.main.fragment_reviews.*

class ReviewsFragment : Fragment() {
    private val reviews = arrayListOf<Review>()
    private val reviewsAdapter = ReviewAdapter(reviews)
    private val viewModel: MovieViewModel by viewModels()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reviews, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getReview(MoviesDetailFragment.title.toString())
        initView()
        observeReview()
    }

    private fun initView() {
        rvReviews.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rvReviews.adapter = reviewsAdapter
    }

    private fun observeReview() {
        viewModel.reviews.observe(viewLifecycleOwner, Observer {
            reviews.clear()
            reviews.addAll(it)
            reviewsAdapter.notifyDataSetChanged()
        })
    }

}