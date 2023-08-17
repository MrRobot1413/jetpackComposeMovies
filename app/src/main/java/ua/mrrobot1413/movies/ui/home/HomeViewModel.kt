package ua.mrrobot1413.movies.ui.home

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.base.BaseViewModel
import ua.mrrobot1413.movies.domain.repository.HomeRepository
import ua.mrrobot1413.movies.misc.NetworkObserver
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val networkObserver: NetworkObserver,
    private val reducer: HomeScreenReducer
): BaseViewModel<HomeScreenState, HomeScreenEvent>() {

    override val state: StateFlow<HomeScreenState>
        get() = reducer.state

    init {
        getMovies()
        networkObserver.onNetworkStatusChange {
            reducer.sendEvent(HomeScreenEvent.NetworkChange(isConnected = it))
        }
    }

    fun getMovies() {
        viewModelScope.launch {
            reducer.sendEvent(
                HomeScreenEvent.Load(
                    isLoadingPopular = true,
                    isLoadingNewest = true,
                    isLoadingUpcoming = true,
                    isFirstLoad = false
                )
            )
            val popular = homeRepository.getPopularMovies(1)
            val newest = homeRepository.getNewestMovies(1)
            val upcoming = homeRepository.getUpcomingMovies(1)

            if (popular.isFailure || newest.isFailure) {
                reducer.sendEvent(
                    HomeScreenEvent.Error
                )
            } else {
                reducer.sendEvent(
                    HomeScreenEvent.ShowMovies(
                        popular.getOrDefault(listOf()),
                        newest.getOrDefault(listOf()),
                        upcoming.getOrDefault(listOf())
                    )
                )
            }
        }
    }
}