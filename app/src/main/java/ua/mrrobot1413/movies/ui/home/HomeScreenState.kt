package ua.mrrobot1413.movies.ui.home

import ua.mrrobot1413.movies.base.UiState
import ua.mrrobot1413.movies.data.network.model.Movie

data class HomeScreenState(
    val isFirstLoad: Boolean = true,
    val isLoadingPopular: Boolean = false,
    val isLoadingNewest: Boolean = false,
    val isLoadingUpcoming: Boolean = false,
    val popularItems: List<Movie> = emptyList(),
    val newestItems: List<Movie> = emptyList(),
    val upcomingItems: List<Movie> = emptyList(),
    val errorPopular: String? = null,
    val errorNewest: String? = null,
    val errorUpcoming: String? = null,
    val isConnected: Boolean = true
): UiState