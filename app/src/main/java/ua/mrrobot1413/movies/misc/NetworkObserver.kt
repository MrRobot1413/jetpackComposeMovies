package ua.mrrobot1413.movies.misc

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import ua.mrrobot1413.movies.di.MainDispatcher
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NetworkObserver @Inject constructor(
    @ApplicationContext
    private val context: Context,
    @MainDispatcher
    private val mainDispatcher: CoroutineDispatcher
) {

    fun onNetworkStatusChange(onChange: suspend (connected: Boolean) -> Unit) {
        Utils.onNetworkStatusChange(context, onChange = {
            withContext(mainDispatcher) {
                onChange(it)
            }
        })
    }
}