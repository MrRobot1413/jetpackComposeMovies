package ua.mrrobot1413.movies.data.paging

import ua.mrrobot1413.movies.ui.viewAll.ListType

class ViewAllPaginator<Item, Key>(
    private val initialKey: Key,
    private var isFirstLoad: Boolean = true,
    private inline val firstLoad: (Boolean) -> Unit,
    private inline val onLoadUpdated: (Boolean) -> Unit,
    private inline val onRequest: suspend (nextKey: Key, type: ListType) -> Result<List<Item>>,
    private inline val getNextKey: suspend (List<Item>) -> Key,
    private inline val onError: suspend (Throwable?) -> Unit,
    private inline val onSuccess: suspend (items: List<Item>, newKey: Key) -> Unit
) : Paginator<Key, Item> {

    private var currKey = initialKey
    private var isMakingRequest = false

    override suspend fun loadNextItems(type: ListType) {
        if(isFirstLoad) {
            isFirstLoad = true
            firstLoad(isFirstLoad)
        }
        if (isMakingRequest) return
        isMakingRequest = true
        onLoadUpdated(true)
        val result = onRequest(currKey, type)
        isMakingRequest = false
        val items = result.getOrElse {
            onError(it)
            onLoadUpdated(false)
            return
        }
        currKey = getNextKey(items)
        onSuccess(items, currKey)
        isFirstLoad = false
        onLoadUpdated(false)
    }

    override suspend fun reset() {
        currKey = initialKey
    }
}