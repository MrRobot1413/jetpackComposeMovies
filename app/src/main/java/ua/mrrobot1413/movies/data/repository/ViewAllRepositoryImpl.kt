package ua.mrrobot1413.movies.data.repository

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.data.network.api.Api
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.di.IoDispatcher
import ua.mrrobot1413.movies.domain.repository.ViewAllRepository
import ua.mrrobot1413.movies.ui.viewAll.ListType
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ViewAllRepositoryImpl @Inject constructor(
    private val api: Api,
    @IoDispatcher
    private val dispatcher: CoroutineDispatcher
): ViewAllRepository {

    override suspend fun getMovies(page: Int, type: ListType): Result<List<Movie>> {
        return withContext(dispatcher) {
            try {
                when(type) {
                    ListType.POPULAR -> {
                        val res = api.getPopularMovies(page).results.toMutableList()
                        res.forEachIndexed { ind, movie ->
                            res.forEachIndexed { i, it ->
                                if(movie.id == 0 || movie.posterPath == null) res.remove(it)
                                if(ind != i && movie.id == it.id) res.remove(it)
                            }
                        }
                        Result.success(res)
                    }
                    ListType.NEWEST -> {
                        val res = api.getNewestMovies(page).results.toMutableList()
                        res.forEachIndexed { ind, movie ->
                            res.forEachIndexed { i, it ->
                                if(movie.id == 0 || movie.posterPath == null) res.remove(it)
                                if(ind != i && movie.id == it.id) res.remove(it)
                            }
                        }
                        Result.success(res)
                    }
                    ListType.UPCOMING -> {
                        Result.success(api.getUpcomingMovies(page).results)
                    }
                }
            } catch (e: Exception) {
                println(e)
                Result.failure(e)
            }
        }
    }
}