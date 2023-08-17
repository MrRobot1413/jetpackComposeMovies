package ua.mrrobot1413.movies.data.network.api

import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import ua.mrrobot1413.movies.BuildConfig
import ua.mrrobot1413.movies.data.network.model.DetailedMovieResponse
import ua.mrrobot1413.movies.data.network.model.GetMoviesResponse
import ua.mrrobot1413.movies.data.network.model.Movie
import java.util.Locale

interface Api {

    @GET("movie/popular")
    suspend fun getPopularMovies(@Query("page") page: Int): GetMoviesResponse

    @GET("movie/now_playing")
    suspend fun getNewestMovies(@Query("page") page: Int): GetMoviesResponse

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(@Query("page") page: Int): GetMoviesResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(@Path("movie_id") id: Int): DetailedMovieResponse

    @GET("movie/{movie_id}/similar")
    suspend fun getSimilarMovies(@Path("movie_id") id: Int): GetMoviesResponse

    @GET("search/movie")
    suspend fun searchMovies(@Query("query") query: String, @Query("page") page: Int): GetMoviesResponse
}