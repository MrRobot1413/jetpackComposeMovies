package ua.mrrobot1413.movies.data.network.model

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("adult") var adult: Boolean?,
    @SerializedName("genre_ids") var genreIds: ArrayList<Int>,
    @SerializedName("id") var id: Int?,
    @SerializedName("poster_path") var posterPath: String?
)