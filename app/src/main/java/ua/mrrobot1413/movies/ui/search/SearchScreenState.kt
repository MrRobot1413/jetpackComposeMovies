package ua.mrrobot1413.movies.ui.search

import ua.mrrobot1413.movies.base.UiState
import ua.mrrobot1413.movies.data.network.model.Movie

data class SearchScreenState(
    val isLoading: Boolean = false,
    val query: String? = null,
    val items: List<Movie> = emptyList(),
    val error: String? = null,
    val isConnected: Boolean = true,
    val endReached: Boolean = false,
    val page: Int = 1
): UiState