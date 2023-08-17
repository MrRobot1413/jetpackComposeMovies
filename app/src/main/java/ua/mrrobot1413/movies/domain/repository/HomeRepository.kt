package ua.mrrobot1413.movies.domain.repository

import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow
import ua.mrrobot1413.movies.data.network.model.GetMoviesResponse
import ua.mrrobot1413.movies.data.network.model.Movie

interface HomeRepository {

    suspend fun getPopularMovies(page: Int): Result<List<Movie>>
    suspend fun getNewestMovies(page: Int): Result<List<Movie>>
    suspend fun getUpcomingMovies(page: Int): Result<List<Movie>>
}