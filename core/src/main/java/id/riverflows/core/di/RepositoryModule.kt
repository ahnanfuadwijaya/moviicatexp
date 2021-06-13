package id.riverflows.core.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.riverflows.core.data.MoviiCatRepository
import id.riverflows.core.domain.repository.IMovieTvRepository

@Module(includes = [DatabaseModule::class, NetworkModule::class])
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun provideMovieRepository(repository: MoviiCatRepository): IMovieTvRepository
}