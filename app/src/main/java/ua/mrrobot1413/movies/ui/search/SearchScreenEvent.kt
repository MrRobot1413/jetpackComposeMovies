package ua.mrrobot1413.movies.ui.search

import ua.mrrobot1413.movies.base.UiEvent
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.ui.home.HomeScreenEvent

sealed class SearchScreenEvent: UiEvent {
    data class ShowData(val items: List<Movie>, val page: Int, val endReached: Boolean, val query: String): SearchScreenEvent()
    data class Load(val isLoading: Boolean): SearchScreenEvent()
    data class NetworkChange(val isConnected: Boolean): SearchScreenEvent()
    object Error: SearchScreenEvent()
}