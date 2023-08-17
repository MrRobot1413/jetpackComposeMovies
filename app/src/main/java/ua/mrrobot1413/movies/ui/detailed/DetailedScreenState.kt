package ua.mrrobot1413.movies.ui.detailed

import ua.mrrobot1413.movies.base.UiState
import ua.mrrobot1413.movies.data.network.model.DetailedMovieResponse
import ua.mrrobot1413.movies.data.network.model.Genre
import ua.mrrobot1413.movies.data.network.model.GetMoviesResponse

data class DetailedScreenState(
    val error: String? = null,
    val isLoading: Boolean = false,
    val isConnected: Boolean = true,
    val movie: DetailedMovieResponse = DetailedMovieResponse(),
    val similarMovies: GetMoviesResponse = GetMoviesResponse()
): UiState