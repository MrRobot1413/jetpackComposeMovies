package ua.mrrobot1413.movies.ui.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import ua.mrrobot1413.movies.base.BaseViewModel
import ua.mrrobot1413.movies.data.paging.SearchPaginator
import ua.mrrobot1413.movies.data.paging.ViewAllPaginator
import ua.mrrobot1413.movies.domain.repository.SearchRepository
import ua.mrrobot1413.movies.misc.NetworkObserver
import ua.mrrobot1413.movies.ui.viewAll.ViewAllScreenEvent
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchRepository: SearchRepository,
    private val networkObserver: NetworkObserver,
    private val reducer: SearchScreenReducer
) : BaseViewModel<SearchScreenState, SearchScreenEvent>() {

    override val state: StateFlow<SearchScreenState>
        get() = reducer.state

    var query = ""

    // Todo
    // Different queries search fix
    private val paginator = SearchPaginator(
        firstLoad = {
            reducer.sendEvent(SearchScreenEvent.Load(isLoading = true))
        },
        initialKey = state.value.page,
        onLoadUpdated = {
            reducer.sendEvent(SearchScreenEvent.Load(isLoading = it))
        },
        onRequest = { query, nextPage, type ->
            println(nextPage)
            searchRepository.searchMovies(query, nextPage)
        },
        getNextKey = {
            state.value.page + 1
        },
        onError = {
            reducer.sendEvent(SearchScreenEvent.Error)
        },
        onSuccess = { items, newKey ->
            reducer.sendEvent(
                SearchScreenEvent.ShowData(
                    items = items,
                    page = newKey,
                    query = this.query,
                    endReached = items.isEmpty()
                )
            )
        }
    )

    init {
        networkObserver.onNetworkStatusChange {
            reducer.sendEvent(SearchScreenEvent.NetworkChange(it))
        }
    }

    fun searchMovies() {
        viewModelScope.launch {
            paginator.setQuery(query)
            paginator.loadNextItems()
        }
    }
}