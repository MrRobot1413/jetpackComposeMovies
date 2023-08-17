package ua.mrrobot1413.movies.data.network.model

import com.google.gson.annotations.SerializedName

data class DetailedMovieResponse(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("title")
    val title: String = "",
    @SerializedName("runtime")
    val runtime: String = "",
    @SerializedName("release_date")
    val releaseDate: String = "",
    @SerializedName("overview")
    val overview: String = "",
    @SerializedName("original_title")
    val originalTitle: String = "",
    @SerializedName("original_language")
    val originalLanguage: String = "",
    @SerializedName("poster_path")
    val posterPath: String = "",
    @SerializedName("genres")
    val genres: List<Genre> = listOf()
)