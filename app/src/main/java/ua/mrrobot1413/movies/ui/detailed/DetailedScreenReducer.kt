package ua.mrrobot1413.movies.ui.detailed

import ua.mrrobot1413.movies.base.Reducer

class DetailedScreenReducer(initial: DetailedScreenState) :
    Reducer<DetailedScreenState, DetailedScreenEvent>(initial) {
    override fun reduce(oldState: DetailedScreenState, event: DetailedScreenEvent) {
        when (event) {
            is DetailedScreenEvent.Load -> {
                setState(oldState.copy(isLoading = true))
            }
            is DetailedScreenEvent.ShowData -> {
                setState(oldState.copy(movie = event.movie, similarMovies = event.similarMovies, isLoading = false))
            }
            is DetailedScreenEvent.Error -> {
                setState(oldState.copy(error = event.error, isLoading = false))
            }
            is DetailedScreenEvent.NetworkChange -> {
                setState(oldState.copy(isConnected = event.isConnected))
            }
        }
    }
}