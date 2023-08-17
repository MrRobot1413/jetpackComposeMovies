package ua.mrrobot1413.movies.data.network.model

import com.google.gson.annotations.SerializedName

data class GetMoviesResponse(
    @SerializedName("page") var page: Int = 0,
    @SerializedName("results") var results: List<Movie> = listOf(),
    @SerializedName("total_pages") var totalPages: Int = 0,
    @SerializedName("total_results") var totalResults: Int = 0
)