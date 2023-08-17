package ua.mrrobot1413.movies.ui.viewAll

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.base.BaseViewModel
import ua.mrrobot1413.movies.data.network.model.Movie
import ua.mrrobot1413.movies.data.paging.DefaultPaginator
import ua.mrrobot1413.movies.data.paging.ViewAllPaginator
import ua.mrrobot1413.movies.domain.repository.ViewAllRepository
import ua.mrrobot1413.movies.misc.NetworkObserver
import ua.mrrobot1413.movies.ui.home.HomeScreenState
import javax.inject.Inject

@HiltViewModel
class ViewAllViewModel @Inject constructor(
    private val viewAllRepository: ViewAllRepository,
    private val networkObserver: NetworkObserver,
    private val reducer: ViewAllReducer
) : BaseViewModel<ViewAllScreenState, ViewAllScreenEvent>() {

    override val state: StateFlow<ViewAllScreenState>
        get() = reducer.state

    private val paginator = ViewAllPaginator(
        firstLoad = {
            reducer.sendEvent(ViewAllScreenEvent.Load(isFirstLoad = it, isLoading = true))
        },
        initialKey = state.value.page,
        onLoadUpdated = {
            reducer.sendEvent(ViewAllScreenEvent.Load(isLoading = it))
        },
        onRequest = { nextPage, type ->
            println(nextPage)
            viewAllRepository.getMovies(nextPage, type)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = {
            reducer.sendEvent(ViewAllScreenEvent.Error(it?.message ?: ""))
        },
        onSuccess = { items, newKey ->
            println(items.count())
            reducer.sendEvent(ViewAllScreenEvent.ShowData(items = items, page = newKey, endReached = items.isEmpty()))
        }
    )

    init {
        networkObserver.onNetworkStatusChange {
            reducer.sendEvent(ViewAllScreenEvent.NetworkChange(it))
        }
    }

    fun loadNextItems(type: ListType) {
        viewModelScope.launch {
            paginator.loadNextItems(type)
        }
    }
}