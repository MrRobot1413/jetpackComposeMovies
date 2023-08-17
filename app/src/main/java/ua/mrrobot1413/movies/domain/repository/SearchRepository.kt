package ua.mrrobot1413.movies.domain.repository

import ua.mrrobot1413.movies.data.network.model.Movie

interface SearchRepository {

    suspend fun searchMovies(query: String, page: Int): Result<List<Movie>>
}