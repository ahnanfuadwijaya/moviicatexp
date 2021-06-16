package id.riverflows.favorite.ui

import androidx.lifecycle.ViewModel
import id.riverflows.core.domain.usecase.MovieTvUseCase

class FavoriteViewModel(
    movieTvUseCase: MovieTvUseCase
): ViewModel() {
    val favoriteMovies = movieTvUseCase.getFavoriteMovies()
    val favoriteTvShows = movieTvUseCase.getFavoriteTvShows()
}