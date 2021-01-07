package com.example.capstone.adapter

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.capstone.R
import com.example.capstone.model.Review
import kotlinx.android.synthetic.main.fragment_review_item.view.*


class ReviewAdapter(private val review: List<Review>)
    : RecyclerView.Adapter<ReviewAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
                LayoutInflater.from(context).inflate(R.layout.fragment_review_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return review.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(review: Review) {
            itemView.movieComment.text = review.comment
            itemView.ratingBar_review.numStars = review.rating.toInt()
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(review[position])
    }
}