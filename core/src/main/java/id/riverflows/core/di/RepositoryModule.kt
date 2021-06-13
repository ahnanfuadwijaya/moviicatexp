package id.riverflows.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.riverflows.core.data.MoviiCatRepository
import id.riverflows.core.domain.repository.IFavoriteRepository
import id.riverflows.core.domain.repository.IMovieRepository
import id.riverflows.core.domain.repository.ITvRepository

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(repository: MoviiCatRepository): IMovieRepository
    @Binds
    abstract fun provideTvRepository(repository: MoviiCatRepository): ITvRepository
    @Binds
    abstract fun provideFavoriteRepository(repository: MoviiCatRepository): IFavoriteRepository
}