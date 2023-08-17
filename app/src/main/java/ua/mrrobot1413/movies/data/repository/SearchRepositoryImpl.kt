package ua.mrrobot1413.movies.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.api.Api
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repository.SearchRepository
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: Api,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
): SearchRepository {
    override suspend fun searchMovies(query: String, page: Int): Result<List<Movie>> {
        return withContext(dispatcher) {
            try {
                Result.success(api.searchMovies(query, page).results)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}