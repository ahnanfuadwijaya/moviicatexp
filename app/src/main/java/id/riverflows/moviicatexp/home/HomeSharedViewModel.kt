package id.riverflows.moviicatexp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.riverflows.core.domain.usecase.MovieTvUseCase
import javax.inject.Inject

@HiltViewModel
class HomeSharedViewModel @Inject constructor(
    movieTvUseCase: MovieTvUseCase
): ViewModel() {
    val movies = movieTvUseCase.getMovies().asLiveData()
    val tvShows = movieTvUseCase.getTvShows().asLiveData()
}