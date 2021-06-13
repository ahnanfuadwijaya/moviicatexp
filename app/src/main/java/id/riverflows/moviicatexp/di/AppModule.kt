package id.riverflows.moviicatexp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.riverflows.core.domain.usecase.MovieInteractor
import id.riverflows.core.domain.usecase.MovieUseCase

@Module
@InstallIn(ViewModelComponent::class)
abstract class AppModule {
    @Binds
    @ViewModelScoped
    abstract fun provideMovieUseCase(movieInteractor: MovieInteractor): MovieUseCase
}