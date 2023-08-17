package ua.mrrobot1413.movies.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ua.mrrobot1413.movies.data.repository.DetailedRepositoryImpl
import ua.mrrobot1413.movies.data.repository.HomeRepositoryImpl
import ua.mrrobot1413.movies.data.repository.SearchRepositoryImpl
import ua.mrrobot1413.movies.data.repository.ViewAllRepositoryImpl
import ua.mrrobot1413.movies.domain.repository.DetailedRepository
import ua.mrrobot1413.movies.domain.repository.HomeRepository
import ua.mrrobot1413.movies.domain.repository.SearchRepository
import ua.mrrobot1413.movies.domain.repository.ViewAllRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideHomeRepository(homeRepositoryImpl: HomeRepositoryImpl): HomeRepository

    @Binds
    abstract fun provideViewAllRepository(viewAll: ViewAllRepositoryImpl): ViewAllRepository

    @Binds
    abstract fun provideDetailedRepository(detailedRepositoryImpl: DetailedRepositoryImpl): DetailedRepository

    @Binds
    abstract fun provideSearchRepository(searchRepositoryImpl: SearchRepositoryImpl): SearchRepository
}