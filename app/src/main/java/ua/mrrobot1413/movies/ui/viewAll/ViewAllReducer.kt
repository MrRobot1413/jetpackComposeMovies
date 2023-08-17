package ua.mrrobot1413.movies.ui.viewAll

import ua.mrrobot1413.movies.base.Reducer

class ViewAllReducer(initial: ViewAllScreenState):
    Reducer<ViewAllScreenState, ViewAllScreenEvent>(initial) {
    override fun reduce(oldState: ViewAllScreenState, event: ViewAllScreenEvent) {
        when(event) {
            is ViewAllScreenEvent.LoadNextItems -> {
                setState(oldState.copy(items = oldState.items + event.items))
            }
            is ViewAllScreenEvent.Load -> {
                setState(oldState.copy(isFirstLoad = event.isFirstLoad, isLoading = event.isLoading))
            }
            is ViewAllScreenEvent.Error -> {
                setState(oldState.copy(error = event.error))
            }
            is ViewAllScreenEvent.ShowData -> {
                setState(oldState.copy(items = oldState.items + event.items, page = event.page, endReached = event.endReached, isFirstLoad = false, isLoading = false, error = null))
            }
            is ViewAllScreenEvent.NetworkChange -> {
                setState(oldState.copy(isConnected = event.isConnected))
            }
        }
    }
}