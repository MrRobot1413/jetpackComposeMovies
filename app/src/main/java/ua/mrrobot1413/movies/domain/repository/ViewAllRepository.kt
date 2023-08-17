package ua.mrrobot1413.movies.domain.repository

import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.ui.viewAll.ListType

interface ViewAllRepository {

    suspend fun getMovies(page: Int, type: ListType): Result<List<Movie>>
}