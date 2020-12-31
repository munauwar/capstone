package com.example.capstone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.capstone.R
import com.example.capstone.model.Movie
import kotlinx.android.synthetic.main.item_movies.view.*

class MoviesAdapter(private val movies: List<Movie>,  val callback: (movie : Movie) -> Unit)
    : RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        return ViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_movies, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(movie: Movie) {
            itemView.tvMovieName.text = movie.title
            Glide.with(context).load(movie.getPosterPath()).into(itemView.ivPoster)
            itemView.setOnClickListener {
                callback(movie)
            }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(movies[position])
    }
}