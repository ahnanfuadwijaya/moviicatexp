package id.riverflows.favorite

import androidx.lifecycle.ViewModel
import id.riverflows.core.domain.usecase.MovieTvUseCase

class FavoriteViewModel(
    movieTvUseCase: MovieTvUseCase
): ViewModel() {
    val favoriteMovies = movieTvUseCase.getFavoriteMovies()
}