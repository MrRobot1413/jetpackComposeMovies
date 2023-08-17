package ua.mrrobot1413.movies.data.paging

import ua.mrrobot1413.movies.ui.viewAll.ListType

class DefaultPaginator<Item, Key>(
    private val initialKey: Key,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems(type: ListType) {
        if (isMakingRequest) return
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currKey)
        isMakingRequest = false
        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }
        currKey = getNextKey(items)
        onSuccess(items, currKey)
        onLoadUpdated(false)
    }

    override suspend fun reset() {
        currKey = initialKey
    }
}