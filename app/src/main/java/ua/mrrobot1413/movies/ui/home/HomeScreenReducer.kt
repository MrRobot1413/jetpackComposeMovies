package ua.mrrobot1413.movies.ui.home

import ua.mrrobot1413.movies.base.Reducer

class HomeScreenReducer(initial: HomeScreenState) :
    Reducer<HomeScreenState, HomeScreenEvent>(initial) {
    override fun reduce(oldState: HomeScreenState, event: HomeScreenEvent) {
        when (event) {
            is HomeScreenEvent.ShowMovies -> {
                setState(
                    oldState.copy(
                        popularItems = event.popularItems,
                        newestItems = event.newestItems,
                        upcomingItems = event.upcomingItems,
                        errorNewest = null,
                        errorPopular = null,
                        isFirstLoad = false,
                        isLoadingPopular = false,
                        isLoadingNewest = false
                    )
                )
            }

            is HomeScreenEvent.Load -> {
                setState(
                    oldState.copy(
                        isFirstLoad = event.isFirstLoad,
                        isLoadingPopular = event.isLoadingPopular,
                        isLoadingNewest = event.isLoadingNewest
                    )
                )
            }

            is HomeScreenEvent.NetworkChange -> {
                setState(oldState.copy(isConnected = event.isConnected))
            }

            is HomeScreenEvent.Error -> {
                setState(oldState.copy(isConnected = false, errorNewest = "", errorPopular = ""))
            }
        }
    }
}