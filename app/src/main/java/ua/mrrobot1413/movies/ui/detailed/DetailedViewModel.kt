package ua.mrrobot1413.movies.ui.detailed

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.base.BaseViewModel
import ua.mrrobot1413.movies.data.network.model.DetailedMovieResponse
import ua.mrrobot1413.movies.di.MainDispatcher
import ua.mrrobot1413.movies.domain.repository.DetailedRepository
import ua.mrrobot1413.movies.misc.NetworkObserver
import ua.mrrobot1413.movies.ui.home.HomeScreenEvent
import javax.inject.Inject

@HiltViewModel
class DetailedViewModel @Inject constructor(
    private val detailedRepository: DetailedRepository,
    private val reducer: DetailedScreenReducer,
    private val networkObserver: NetworkObserver
) : BaseViewModel<DetailedScreenState, DetailedScreenEvent>() {

    override val state: StateFlow<DetailedScreenState>
        get() = reducer.state

    init {
        networkObserver.onNetworkStatusChange {
            reducer.sendEvent(DetailedScreenEvent.NetworkChange(isConnected = it))
        }
    }

    fun getMovieDetails(id: Int) {
        viewModelScope.launch {
            reducer.sendEvent(DetailedScreenEvent.Load(isLoading = true))
            val details = detailedRepository.getMovieDetails(id)
            val similar = detailedRepository.getSimilarMovies(id)
            println(details)
            if (details.isFailure || similar.isFailure) {
                reducer.sendEvent(DetailedScreenEvent.Error(error = ""))
            } else {
                reducer.sendEvent(
                    DetailedScreenEvent.ShowData(
                        movie = details.getOrThrow(),
                        similarMovies = similar.getOrThrow()
                    )
                )
            }
        }
    }
}