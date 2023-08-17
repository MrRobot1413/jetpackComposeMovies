package ua.mrrobot1413.movies.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.api.Api
import ua.mrrobot1413.movies.data.network.model.GetMoviesResponse
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repository.HomeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HomeRepositoryImpl @Inject constructor(
    private val api: Api,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
) : HomeRepository {

    override suspend fun getPopularMovies(page: Int): Result<List<Movie>> {
        return withContext(dispatcher) {
            try {
                Result.success(api.getPopularMovies(page).results)
            } catch (e: Exception) {
                println(e)
                Result.failure(e)
            }
        }
    }

    override suspend fun getNewestMovies(page: Int): Result<List<Movie>> {
        return withContext(dispatcher) {
            try {
                Result.success(api.getNewestMovies(page).results)
            } catch (e: Exception) {
                println(e)
                Result.failure(e)
            }
        }
    }

    override suspend fun getUpcomingMovies(page: Int): Result<List<Movie>> {
        return withContext(dispatcher) {
            try {
                Result.success(api.getUpcomingMovies(page).results)
            } catch (e: Exception) {
                println(e)
                Result.failure(e)
            }
        }
    }
}