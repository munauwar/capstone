package com.example.capstone.model

import com.google.gson.annotations.SerializedName

class ApiResponse() {
    data class Result (
        @SerializedName("results") val results : List<Movie>
    )
}
