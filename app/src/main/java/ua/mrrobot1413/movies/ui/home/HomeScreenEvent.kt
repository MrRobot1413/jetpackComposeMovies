package ua.mrrobot1413.movies.ui.home

import ua.mrrobot1413.movies.base.UiEvent
import ua.mrrobot1413.movies.data.network.model.Movie

sealed class HomeScreenEvent: UiEvent {
    data class Load(val isLoadingPopular: Boolean, val isLoadingNewest: Boolean, val isLoadingUpcoming: Boolean, val isFirstLoad: Boolean): HomeScreenEvent()
    data class ShowMovies(val popularItems: List<Movie>, val newestItems: List<Movie>, val upcomingItems: List<Movie>): HomeScreenEvent()
    data class NetworkChange(val isConnected: Boolean): HomeScreenEvent()
    object Error: HomeScreenEvent()
}