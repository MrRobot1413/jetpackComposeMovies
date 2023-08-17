package ua.mrrobot1413.movies.ui.search

import ua.mrrobot1413.movies.base.Reducer

class SearchScreenReducer(initial: SearchScreenState) :
    Reducer<SearchScreenState, SearchScreenEvent>(initial) {
    override fun reduce(oldState: SearchScreenState, event: SearchScreenEvent) {
        when (event) {
            is SearchScreenEvent.ShowData -> {
                setState(
                    oldState.copy(
                        items = if(oldState.query == event.query) oldState.items + event.items else event.items,
                        error = null,
                        isLoading = false,
                        page =if(oldState.query == event.query) event.page else 0
                    )
                )
            }

            is SearchScreenEvent.Load -> {
                setState(oldState.copy(isLoading = event.isLoading))
            }

            is SearchScreenEvent.NetworkChange -> {
                setState(oldState.copy(isConnected = event.isConnected))
            }

            is SearchScreenEvent.Error -> {
                setState(oldState.copy(error = ""))
            }
        }
    }
}