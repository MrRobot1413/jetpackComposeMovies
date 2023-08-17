package ua.mrrobot1413.movies.ui.detailed

import ua.mrrobot1413.movies.base.UiEvent
import ua.mrrobot1413.movies.data.network.model.DetailedMovieResponse
import ua.mrrobot1413.movies.data.network.model.GetMoviesResponse

sealed class DetailedScreenEvent: UiEvent {
    data class Load(val isLoading: Boolean): DetailedScreenEvent()
    data class ShowData(val movie: DetailedMovieResponse, val similarMovies: GetMoviesResponse): DetailedScreenEvent()
    data class Error(val error: String = ""): DetailedScreenEvent()
    data class NetworkChange(val isConnected: Boolean): DetailedScreenEvent()
}