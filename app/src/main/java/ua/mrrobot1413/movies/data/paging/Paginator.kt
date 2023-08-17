package ua.mrrobot1413.movies.data.paging

import ua.mrrobot1413.movies.ui.viewAll.ListType

interface Paginator<Key, Item> {
    suspend fun loadNextItems(type: ListType = ListType.POPULAR)
    suspend fun reset()
}