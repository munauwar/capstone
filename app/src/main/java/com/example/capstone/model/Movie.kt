package com.example.capstone.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    @SerializedName("id") var id: Int,
    @SerializedName("title") var title: String,
    @SerializedName("overview") var overview: String,
    @SerializedName("poster_path") var poster_path: String,
    @SerializedName("backdrop_path") var backdrop_path: String,
    @SerializedName("release_date") var releaseDate: String
) : Parcelable  {
    fun getPosterPath() = "https://image.tmdb.org/t/p/original/$poster_path"
    fun getBackdropPath() = "https://image.tmdb.org/t/p/original/$backdrop_path"
}