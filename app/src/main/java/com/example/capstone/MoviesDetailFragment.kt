package com.example.capstone

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.capstone.adapter.TabAdapter
import com.example.capstone.model.Movie
import com.example.capstone.viewmodel.MovieViewModel
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.fragment_movies_detail.*
import kotlinx.android.synthetic.main.fragment_movies_detail.view.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
const val REQ_MOVIE_KEY = "req_movie"
const val BUNDLE_MOVIE_KEY = "bundle_movie"
class MoviesDetailFragment : Fragment() {
    private val viewModel: MovieViewModel by viewModels()
    companion object {
        var title : String? = null
    }
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
        initTabs()
    }

    private fun observeClickMovie() {
        setFragmentResultListener(REQ_MOVIE_KEY) { _ , bundle ->
            bundle.getParcelable<Movie>(BUNDLE_MOVIE_KEY)?.let { movie ->
                button.setOnClickListener {
                    showRateDialog(movie.title)
                }
                title = movie.title
                setDetailElements(movie)
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
        }
        builder.show()
    }

    private fun initTabs() {
        vp_content.adapter = TabAdapter(activity?.supportFragmentManager, tab_layout.tabCount)
        tab_layout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {

            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {
                    vp_content.currentItem = tab.position
                }
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
                // Handle tab reselect
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
                // Handle tab unselect
            }
        })
    }
}