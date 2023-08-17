package ua.mrrobot1413.movies.base

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import ua.mrrobot1413.movies.BuildConfig

abstract class Reducer<S : UiState, E : UiEvent>(initialVal: S) {

    private val _state: MutableStateFlow<S> = MutableStateFlow(initialVal)
    val state: StateFlow<S>
        get() = _state

    val stateViewer: StateViewer<S> = StateViewerCapsule<S> { storedState ->
        _state.tryEmit(storedState)
    }

    init {
        stateViewer.addState(initialVal)
    }

    fun sendEvent(event: E) {
        reduce(_state.value, event)
    }

    fun setState(newState: S) {
        val success = _state.tryEmit(newState)

        if (BuildConfig.DEBUG && success) {
            stateViewer.addState(newState)
        }
    }

    abstract fun reduce(oldState: S, event: E)
}

interface UiState

interface UiEvent
