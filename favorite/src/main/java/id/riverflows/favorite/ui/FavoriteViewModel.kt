package id.riverflows.favorite.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.riverflows.core.domain.model.MovieTv
import id.riverflows.core.domain.usecase.MovieTvUseCase
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(
    private val movieTvUseCase: MovieTvUseCase
): ViewModel() {
    private val _favoriteMovies = MutableLiveData<List<MovieTv>>()
    val favoriteMovies: LiveData<List<MovieTv>> = _favoriteMovies
    private val _favoriteTvShows = MutableLiveData<List<MovieTv>>()
    val favoriteTvShows: LiveData<List<MovieTv>> = _favoriteTvShows


    fun getFavoriteMovies() = viewModelScope.launch {
        movieTvUseCase.getFavoriteMovies().collect {
            _favoriteMovies.postValue(it)
        }
    }

    fun getFavoriteTvShows() = viewModelScope.launch {
        movieTvUseCase.getFavoriteTvShows().collect {
            _favoriteTvShows.postValue(it)
        }
    }
}