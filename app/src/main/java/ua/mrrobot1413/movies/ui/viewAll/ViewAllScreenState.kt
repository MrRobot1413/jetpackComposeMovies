package ua.mrrobot1413.movies.ui.viewAll

import ua.mrrobot1413.movies.base.UiState
import ua.mrrobot1413.movies.data.network.model.Movie

data class ViewAllScreenState(
    val isFirstLoad: Boolean = true,
    val isLoading: Boolean = false,
    val items: List<Movie> = emptyList(),
    val error: String? = null,
    val endReached: Boolean = false,
    val page: Int = 1,
    val isConnected: Boolean = true
): UiState