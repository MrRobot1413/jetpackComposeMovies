package ua.mrrobot1413.movies.domain.repository

import ua.mrrobot1413.movies.data.network.model.DetailedMovieResponse
import ua.mrrobot1413.movies.data.network.model.GetMoviesResponse

interface DetailedRepository {

    suspend fun getMovieDetails(id: Int): Result<DetailedMovieResponse>
    suspend fun getSimilarMovies(id: Int): Result<GetMoviesResponse>
}