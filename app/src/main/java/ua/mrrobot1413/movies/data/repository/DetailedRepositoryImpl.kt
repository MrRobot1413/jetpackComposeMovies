package ua.mrrobot1413.movies.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.api.Api
import ua.mrrobot1413.movies.data.network.model.DetailedMovieResponse
import ua.mrrobot1413.movies.data.network.model.GetMoviesResponse
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repository.DetailedRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DetailedRepositoryImpl @Inject constructor(
    private val api: Api,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
): DetailedRepository {

    override suspend fun getMovieDetails(id: Int): Result<DetailedMovieResponse> {
        return withContext(dispatcher) {
            try {
                Result.success(api.getMovieDetails(id))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }

    override suspend fun getSimilarMovies(id: Int): Result<GetMoviesResponse> {
        return withContext(dispatcher) {
            try {
                Result.success(api.getSimilarMovies(id))
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}