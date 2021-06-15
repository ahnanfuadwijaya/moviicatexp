package id.riverflows.moviicatexp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import id.riverflows.core.domain.usecase.MovieTvUseCase

class HomeSharedViewModel(
    movieTvUseCase: MovieTvUseCase
): ViewModel() {
    val movies = movieTvUseCase.getMovies().asLiveData()
    val tvShows = movieTvUseCase.getTvShows().asLiveData()
}