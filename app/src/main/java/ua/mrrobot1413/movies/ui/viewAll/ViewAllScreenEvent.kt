package ua.mrrobot1413.movies.ui.viewAll

import ua.mrrobot1413.movies.base.UiEvent
import ua.mrrobot1413.movies.data.network.model.Movie

sealed class ViewAllScreenEvent: UiEvent {
    data class ShowData(val items: List<Movie>, val page: Int, val endReached: Boolean): ViewAllScreenEvent()
    data class Load(val isLoading: Boolean, val isFirstLoad: Boolean = false): ViewAllScreenEvent()
    data class LoadNextItems(val items: List<Movie>): ViewAllScreenEvent()
    data class Error(val error: String): ViewAllScreenEvent()
    data class NetworkChange(val isConnected: Boolean): ViewAllScreenEvent()
}