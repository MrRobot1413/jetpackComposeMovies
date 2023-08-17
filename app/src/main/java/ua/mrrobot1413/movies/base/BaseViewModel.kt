package ua.mrrobot1413.movies.base

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class BaseViewModel<T : UiState, in E : UiEvent> : ViewModel() {
    abstract val state: StateFlow<T>
}