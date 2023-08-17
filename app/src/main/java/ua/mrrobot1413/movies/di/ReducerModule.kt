package ua.mrrobot1413.movies.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.mrrobot1413.movies.ui.detailed.DetailedScreenReducer
import ua.mrrobot1413.movies.ui.detailed.DetailedScreenState
import ua.mrrobot1413.movies.ui.home.HomeScreenReducer
import ua.mrrobot1413.movies.ui.home.HomeScreenState
import ua.mrrobot1413.movies.ui.search.SearchScreenReducer
import ua.mrrobot1413.movies.ui.search.SearchScreenState
import ua.mrrobot1413.movies.ui.viewAll.ViewAllReducer
import ua.mrrobot1413.movies.ui.viewAll.ViewAllScreenState
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReducerModule {

    @Provides
    @Singleton
    fun provideHomeReducer(): HomeScreenReducer {
        return HomeScreenReducer(initial = HomeScreenState())
    }

    @Provides
    @Singleton
    fun provideViewAllReducer(): ViewAllReducer {
        return ViewAllReducer(initial = ViewAllScreenState())
    }

    @Provides
    @Singleton
    fun provideDetailedReducer(): DetailedScreenReducer {
        return DetailedScreenReducer(initial = DetailedScreenState())
    }

    @Provides
    @Singleton
    fun provideSearchReducer(): SearchScreenReducer {
        return SearchScreenReducer(initial = SearchScreenState())
    }
}