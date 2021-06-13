package id.riverflows.moviicatexp.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.riverflows.core.domain.usecase.MovieTvUseCase
import javax.inject.Inject

@HiltViewModel
class HomeSharedViewModel @Inject constructor(
    movieUseCase: MovieTvUseCase
): ViewModel() {
    val movies = movieUseCase.getMovies().asLiveData()
}